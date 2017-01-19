package isse;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import isse.agents.*;
import isse.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class LectureExample {

	private final int drill = 0, insert = 1, tighten = 2;
	
	private TaskAgent r3;
	private TaskAgent r2;
	private TaskAgent r1;
	
	private SchedulingProblem problem;
	
	private Map<TaskAgent, Valuator<Shift>> typeProfile;
	
	@Before
	public void setUp() throws Exception {
		HashMap<Integer, Double> timesHelper = new HashMap<Integer, Double>();
		timesHelper.put(drill, 20.0);
		timesHelper.put(insert, 5.0);
		timesHelper.put(tighten, 8.0);	
		r1 = new BasicHonestAgent(timesHelper);
		
		// we can reuse timesHelper, as BasicHonestAgent makes a copy of the map
		timesHelper.put(drill, 15.0);
		timesHelper.put(insert, 20.0);
		timesHelper.put(tighten, 15.0);
		r2 = new BasicHonestAgent(timesHelper);
				
		timesHelper.put(drill, 10.0);
		timesHelper.put(insert, 30.0);
		timesHelper.put(tighten, 7.0);
		r3 = new BasicHonestAgent(timesHelper);
		
		List<TaskAgent> agents = Arrays.asList(r1, r2,r3);

		problem = new SchedulingProblem(agents.size(), agents);
		
		typeProfile = new HashMap<TaskAgent, Valuator<Shift>>();
		typeProfile.put(r1, new TaskAgentValuator(r1));
		typeProfile.put(r2, new TaskAgentValuator(r2));
		typeProfile.put(r3, new TaskAgentValuator(r3));		
	}

	@After
	public void tearDown() throws Exception {
		// nothing to do yet
	}

	// TODO: in selection und payments aufteilen
	@Test
	public void testLectureExampleVCG() {
		QuasilinearMechanism<TaskAgent, Shift> qm = new VCGMechanism(problem);
		// make sure your selection and payment functions are implemented correctly
		Shift selectedShift = qm.selection(typeProfile);
		
		Map<Integer, TaskAgent> shiftAssignment = selectedShift.getAssignment();
		// Die Laufzeitkomplexität des Suchalgorithmus ist O(m * n) (für jedes Task den schnellsten Agenten finden)
		
		System.out.println("Selected shift: " + selectedShift.toString());
		// get the correct assignment
		Assert.assertEquals(r3, shiftAssignment.get(drill));
		Assert.assertEquals(r1, shiftAssignment.get(insert));
		Assert.assertEquals(r3, shiftAssignment.get(tighten));
		
		
		Map<TaskAgent, Double> payments = qm.getPayments(typeProfile);
		// System.out.println(payments); // TODO: ordered pretty print
		Assert.assertEquals(-20, payments.get(r1).doubleValue(), 0.001);
		Assert.assertEquals(0, payments.get(r2).doubleValue(), 0.001);
		Assert.assertEquals(-23, payments.get(r3).doubleValue(), 0.001);
	}

	// TODO: in selection und payments aufteilen
	@Test
	public void testLectureExampleBonus() {
		QuasilinearMechanism<TaskAgent, Shift> qm = new BonusCompensationMechanism(problem);
		// make sure your selection and payment functions are implemented correctly
		Shift selectedShift = qm.selection(typeProfile);
		
		Map<Integer, TaskAgent> shiftAssignment = selectedShift.getAssignment();
		// Die Laufzeitkomplexität des Suchalgorithmus ist O(n^m) (alle möglichen Zuweisungen der Tasks an Agenten durchprobieren)
		
		System.out.println("Selected shift: " + selectedShift.toString());
		// get the correct assignment
		Assert.assertEquals(r3, shiftAssignment.get(drill));
		Assert.assertEquals(r1, shiftAssignment.get(insert));
		Assert.assertEquals(r1, shiftAssignment.get(tighten));
		
		Map<TaskAgent, Double> payments = qm.getPayments(typeProfile);
		System.out.println(payments); // TODO: ordered pretty print
		Assert.assertEquals(-20, payments.get(r1).doubleValue(), 0.001);
		Assert.assertEquals(0, payments.get(r2).doubleValue(), 0.001);
		Assert.assertEquals(-12, payments.get(r3).doubleValue(), 0.001);
	}
	
	// for debugging without junit environment 
	public static void main(String[] args) throws Exception {
		LectureExample example = new LectureExample();
		example.setUp();
		QuasilinearMechanism<TaskAgent, Shift> qm = new BonusCompensationMechanism(example.problem);
		// make sure your selection and payment functions are implemented correctly

		Map<TaskAgent, Double> payments = qm.getPayments(example.typeProfile);
	}
}
