package isse.agents;

import java.util.Map.Entry;

import isse.Valuator;
import isse.Shift;

public class TaskAgentValuator implements Valuator<Shift> {

	protected TaskAgent agent;
	
	public TaskAgentValuator(TaskAgent agent) {
		this.agent = agent;
	}
	
	@Override
	public double valuate(Shift instance) {
		double valuation = 0.0;
		// sum up the values of stated costs for this agent
		for(Entry<Integer, TaskAgent> entry : instance.getAssignment().entrySet()) {
			if(entry.getValue().equals(agent)) {
				valuation += (- agent.getAnnouncedTime(entry.getKey()));
			}
		}
		return valuation;
	}
	
}
