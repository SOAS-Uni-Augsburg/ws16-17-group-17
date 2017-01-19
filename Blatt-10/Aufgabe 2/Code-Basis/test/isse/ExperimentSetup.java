package isse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.math3.stat.inference.TTest;
import org.junit.After;
import org.junit.Before;
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
	@Parameters(name = "{index}: scheduling problem with {0} agents, {1} tasks and minimal time in interval [{2}, {3}]")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { 3, 2, 1.0, 50.0 }, { 3, 3, 1.0, 50.0 }, { 3, 5, 1.0, 50.0 } });
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
	
	public static double [] honestPayments;
	public static double [] lyingPayments;
	
	
	// Problem description
		
	private SchedulingProblem problem;
	
	private Map<TaskAgent, Valuator<Shift>> typeProfile;

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
			}
			else{
				LyingAgent agent = new LyingAgent(timesHelper);
				agents.add(i, agent);			
				typeProfile.put(agent, new TaskAgentValuator(agent));
			}

		}
		
		problem = new SchedulingProblem(agents.size(), agents);
	}

	@After
	public void tearDown() throws Exception {
		// nothing to do yet
	}
	
	@Test
	public void test() {
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
						LyingAgent agent = new LyingAgent(timesHelper, false); //Agent 0 lügt
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
			
			problem = new SchedulingProblem(agents.size(), agents);
		
		//eigentlicher Test
			
		System.out.println("Test started.");
		System.out.println(m);
		System.out.println(n);
		System.out.println(tMin);
		System.out.println(tMax);
		QuasilinearMechanism<TaskAgent, Shift> qm = new BonusCompensationMechanism(problem);
		// make sure your selection and payment functions are implemented correctly
		Shift selectedShift = qm.selection(typeProfile);
		
		Map<Integer, TaskAgent> shiftAssignment = selectedShift.getAssignment();
		Map<TaskAgent, Double> payments = qm.getPayments(typeProfile);
		
			lyingPayments[x] = payments.get(agentZero);
			honestPayments[x] = payments.get(agentOne);
		
		
		
		// privater Nutzen ist: payment für Agent i - tatsächlich gemessene Spanne für Agent i (TaskAgentValuator.getActualSpan)
		
		// LyingAgent hat globales Flag LyingAgent.nobodyCanBeTrusted und auch pro Agent kann man angeben, ob er lügt
		
		// TODO: t-test
		// https://haifengl.github.io/smile/api/java/smile/stat/hypothesis/TTest.html
		
		System.out.println(problem);
	}
		System.out.println("Lügner: "+Arrays.toString(lyingPayments));
		System.out.println(mean(lyingPayments));
		System.out.println("Ehrlicher Agent: "+Arrays.toString(honestPayments));
		System.out.println(mean(honestPayments));
		
		TTest tTest = new TTest();
		System.out.println(tTest.pairedTTest(lyingPayments, honestPayments));
	}
	
	public static double mean(double [] d){
		double sum = 0;
		for (int i = 0; i < d.length; i++){
			sum += d[i];
		}
		return sum/d.length;
	}
	
}
