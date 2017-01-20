package isse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.assertj.core.api.Assertions.*;

import org.apache.commons.math3.stat.inference.TTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import isse.agents.LyingAgent;
import isse.agents.TaskAgent;
import isse.agents.TaskAgentValuator;

// Parameterized tests in JUnit 4 see https://github.com/junit-team/junit4/wiki/Parameterized-tests
@RunWith(Parameterized.class)
public class ExperimentSetup {
	private static final int NUM_RUNS = 100;

	@Parameters(name = "{index}: scheduling problem with {0} agents, {1} tasks and minimal time in interval [{2}, {3}]")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { 3, 2, 1.0, 50.0 }, { 3, 3, 1.0, 50.0 }, { 3, 5, 1.0, 50.0 }, { 5, 3, 1.0, 50.0 } });
	}
	
	
	// Parameters

	@Parameter // first data value (0) is default
	public /* NOT private */ int n;

	@Parameter(value = 1)
	public /* NOT private */ int m;

	@Parameter(value = 2)
	public /* NOT private */ double tMin;

	@Parameter(value = 3)
	public /* NOT private */ double tMax;
	
	
	// Problem description
		
	private SchedulingProblem problem;
	
	private Map<TaskAgent, Valuator<Shift>> typeProfile;
	
	
	// Measurements for t-Test
	public static double [] honestPayments;
	public static double [] lyingPayments;
	
	public static double [] honestPrivateUtility;
	public static double [] lyingPrivateUtility;

	public ExperimentSetup() {

	}
	

	protected static Random random = new Random(); // TODO: maybe set constant seed to get same test cases

	
	public static double randomInRange(double min, double max) {
		double range = max - min;
		double scaled = random.nextDouble() * range;
		double shifted = scaled + min;
		return shifted; // == (rand.nextDouble() * (max-min)) + min;
	}
	
	private double randomMimimalTime() {
		return randomInRange(tMin, tMax);
	}
	
	@Before
	public void setUp() throws Exception {

		List<TaskAgent> agents = new ArrayList<>();
		
		generateAgents(agents);

		generateProblem(agents);
	}

	private void generateAgents(List<TaskAgent> agents) {
		HashMap<Integer, Double> timesHelper = new HashMap<Integer, Double>();

		typeProfile = new HashMap<TaskAgent, Valuator<Shift>>();

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				// we can reuse timesHelper, as BasicHonestAgent makes a copy of the map
				timesHelper.put(j, randomMimimalTime());
			}
			TaskAgent agent = new LyingAgent(timesHelper); // default: lügt nicht
			agents.add(i, agent);

			typeProfile.put(agent, new TaskAgentValuator(agent));
		}
	}
	
	private void generateProblem(List<TaskAgent> agents) {
		problem = new SchedulingProblem(m, agents);
	}

	@After
	public void tearDown() throws Exception {
		// nothing to do yet
	}
	
	@Ignore
	@Test
	public void testBonusCompensationMechanismRunsWithoutExceptions() {
		System.out.printf("Test of scheduling problem with %d agents, %d tasks and minimal time in interval [%f, %f] started.%n", n, m, tMin, tMax);
		
		QuasilinearMechanism<TaskAgent, Shift> qm = new BonusCompensationMechanism(problem);
		// make sure your selection and payment functions are implemented correctly
		Shift selectedShift = qm.selection(typeProfile);
		
		System.out.println("Selected shift: " + selectedShift.toString());
		
		Map<TaskAgent, Double> payments = qm.getPayments(typeProfile);
		System.out.println(payments); // TODO: ordered pretty print
		
		System.out.println("Test ended.");
	}
	
	@Test
	public void testPrivateUtilitiesTTest() {
		int runs = NUM_RUNS;
		
		honestPayments = new double [runs];
		lyingPayments = new double [runs];
		
		honestPrivateUtility = new double [runs];
		lyingPrivateUtility = new double [runs];
		
		for (int x = 0; x < runs; x++) {
			ArrayList<TaskAgent> agents = new ArrayList<>();
			
			generateAgents(agents);
			generateProblem(agents);
			
			LyingAgent agentZero = (LyingAgent) agents.get(0); // Agent 0 lügt nicht per default
			
			
			QuasilinearMechanism<TaskAgent, Shift> qm = new BonusCompensationMechanism(problem);
			
			
			
			// Agent 0 lügt nicht per default
			Shift selectedShift = qm.selection(typeProfile);
			Map<TaskAgent, Double> payments = qm.getPayments(typeProfile);
			honestPayments[x] = payments.get(agentZero);
			
			// privater Nutzen ist: - payment vom Agent i - tatsächlich gemessene Spanne für Agent i (TaskAgentValuator.getActualSpan)
			Valuator<Shift> valuator = typeProfile.get(agentZero);
			honestPrivateUtility[x] = - honestPayments[x] - valuator.getActualSpan(selectedShift);
			
			
			// Agent 0 lügt
			agentZero.setLying(true); 
			selectedShift = qm.selection(typeProfile);
			payments = qm.getPayments(typeProfile);
			lyingPayments[x] = payments.get(agentZero);
			
			lyingPrivateUtility[x] = - lyingPayments[x] - valuator.getActualSpan(selectedShift);
		}
		
		System.out.println("Bezahlungen vom Lügner: " + Arrays.toString(lyingPayments));
		System.out.println("Mittelwert der Bezahlungen vom Lügner: " +  mean(lyingPayments));
		System.out.println("Bezahlungen vom ehrlichen Agent: "+Arrays.toString(honestPayments));
		System.out.println("Mittelwert der Bezahlungen vom ehrlichen Agent: " +  mean(honestPayments));
		
		System.out.println("Private Nutzen des Lügners: " + Arrays.toString(lyingPrivateUtility));
		System.out.println("Mittelwert des privaten Nutzens des Lügners: " + mean(lyingPrivateUtility));
		System.out.println("Private Nutzen des ehrlichen Agenten: " + Arrays.toString(honestPrivateUtility));
		System.out.println("Mittelwert des privaten Nutzens des ehrlichen Agenten: " + mean(honestPrivateUtility));
		
		
		// Calculate p-value with Apache Commons Math, see https://commons.apache.org/proper/commons-math/javadocs/api-3.6.1/org/apache/commons/math3/stat/inference/TTest.html
		// other great library is Smile, see https://haifengl.github.io/smile/api/java/smile/stat/hypothesis/TTest.html, which also has statistics and plotting capabilities
		
		// from https://haifengl.github.io/smile/api/java/smile/stat/hypothesis/TTest.html:
		// There are different versions of the t test depending on whether the two samples are
		// - unpaired, independent of each other (e.g., individuals randomly assigned into two groups, measured after an intervention and compared with the other group),
		// - or paired, so that each member of one sample has a unique relationship with a particular member of the other sample
		//   (e.g., the same people measured before and after an intervention).
		
		TTest tTest = new TTest();
		double pValue = tTest.pairedTTest(lyingPrivateUtility, honestPrivateUtility);
		System.out.println("Observed significance level (p-value) is " + pValue);
		
		// from https://haifengl.github.io/smile/api/java/smile/stat/hypothesis/TTest.html:
		// If the calculated p-value is below the threshold chosen for statistical significance (usually 0.05 or 0.01 level),
		// then the null hypothesis which usually states that the two groups do not differ is rejected in favor of an alternative hypothesis,
		// which typically states that the groups do differ.
		
		assertThat(pValue).isLessThan(0.05);		
	}
	
	@Ignore
	@Test
	public void testPaymentsTTest() {
		int runs = 100;
		honestPayments = new double [runs];
		lyingPayments = new double [runs];
		LyingAgent agentZero = null;
		LyingAgent agentOne = null;
		for (int x = 0; x < runs; x++){
			//setup
			
			HashMap<Integer, Double> timesHelper = new HashMap<Integer, Double>();
			
			List<TaskAgent> agents = new ArrayList<>();
			
			typeProfile = new HashMap<TaskAgent, Valuator<Shift>>();
			
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					// we can reuse timesHelper, as BasicHonestAgent makes a copy of the map
					timesHelper.put(j,  randomMimimalTime());
				}
				if (i == 0){
						LyingAgent agent = new LyingAgent(timesHelper, true); //Agent 0 lügt
						agents.add(i, agent);			
						typeProfile.put(agent, new TaskAgentValuator(agent));
						agentZero = agent;
				}
				else if (i == 1){
						LyingAgent agent = new LyingAgent(timesHelper, false); //Agent 1 lügt
						agents.add(i, agent);			
						typeProfile.put(agent, new TaskAgentValuator(agent));
						agentOne = agent;
				}
				else{
					LyingAgent agent = new LyingAgent(timesHelper);
					agents.add(i, agent);			
					typeProfile.put(agent, new TaskAgentValuator(agent));
				}

			}
			
			generateProblem(agents);
		
			//eigentlicher Test
				
			System.out.println("Test started.");
			System.out.println(n);
			System.out.println(m);
			System.out.println(tMin);
			System.out.println(tMax);
			QuasilinearMechanism<TaskAgent, Shift> qm = new BonusCompensationMechanism(problem);
			// make sure your selection and payment functions are implemented correctly
			Shift selectedShift = qm.selection(typeProfile);
			
			Map<Integer, TaskAgent> shiftAssignment = selectedShift.getAssignment();
			Map<TaskAgent, Double> payments = qm.getPayments(typeProfile);
			
			lyingPayments[x] = payments.get(agentZero);
			honestPayments[x] = payments.get(agentOne);
			
			System.out.println(problem);
		}
		
		System.out.println("Lügner: "+Arrays.toString(lyingPayments));
		System.out.println(mean(lyingPayments));
		System.out.println("Ehrlicher Agent: "+Arrays.toString(honestPayments));
		System.out.println(mean(honestPayments));
		
		TTest tTest = new TTest();
		double pValue = tTest.pairedTTest(lyingPayments, honestPayments);
		System.out.println("Observed significance level (p-value) is " + pValue);
		
		assertThat(pValue).isLessThan(0.05);
	}
	
	public static double mean(double [] d){
		double sum = 0;
		for (int i = 0; i < d.length; i++){
			sum += d[i];
		}
		return sum/d.length;
	}
	
}
