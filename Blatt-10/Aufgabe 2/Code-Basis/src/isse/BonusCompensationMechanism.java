package isse;
import isse.agents.TaskAgent;

import java.util.Map;

public class BonusCompensationMechanism extends QuasilinearMechanism<TaskAgent, Shift> {
	
	protected SchedulingProblem problem;
		
	public BonusCompensationMechanism(SchedulingProblem problem) {
		this.problem = problem;
	}
	

	@Override
	public Shift selection(Map<TaskAgent, Valuator<Shift>> typeProfile) {
		// TODO implement selection
		return null;
	}

	@Override
	public Map<TaskAgent, Double> getPayments(
			Map<TaskAgent, Valuator<Shift>> typeProfile) {
		// TODO implement payments
		return null;
	}

}
