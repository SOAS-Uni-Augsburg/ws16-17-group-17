package isse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
	public /* NOT private */ int m;

	@Parameter(value = 1)
	public /* NOT private */ int n;

	@Parameter(value = 2)
	public /* NOT private */ double tMin;

	@Parameter(value = 3)
	public /* NOT private */ double tMax;
	
	
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
			TaskAgent agent = new LyingAgent(timesHelper);
			agents.add(i, agent);
			
			typeProfile.put(agent, new TaskAgentValuator(agent));
		}
		
		problem = new SchedulingProblem(agents.size(), agents);
	}

	@After
	public void tearDown() throws Exception {
		// nothing to do yet
	}
	
	@Test
	public void test() {
		System.out.println("Test started.");
		System.out.println(m);
		System.out.println(n);
		System.out.println(tMin);
		System.out.println(tMax);
		
		// privater Nutzen ist: payment für Agent i - tatsächlich gemessene Spanne für Agent i (TaskAgentValuator.getActualSpan)
		
		// LyingAgent hat globales Flag LyingAgent.nobodyCanBeTrusted und auch pro Agent kann man angeben, ob er lügt
		
		// TODO: t-test
		// https://haifengl.github.io/smile/api/java/smile/stat/hypothesis/TTest.html
		
		System.out.println(problem);
	}
}
