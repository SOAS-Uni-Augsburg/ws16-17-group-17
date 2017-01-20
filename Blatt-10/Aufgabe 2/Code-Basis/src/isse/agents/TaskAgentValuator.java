package isse.agents;

import java.util.Map.Entry;

import isse.Valuator;
import isse.Shift;

public class TaskAgentValuator implements Valuator<Shift> {

	protected TaskAgent agent;
	
	public TaskAgentValuator(TaskAgent agent) {
		this.agent = agent;
	}
	
//	@Override
//	public double valuate(Shift instance) {
//		double valuation = 0.0;
//		// sum up the values of stated costs for this agent
//		for(Entry<Integer, TaskAgent> entry : instance.getAssignment().entrySet()) {
//			if(entry.getValue().equals(agent)) {
//				valuation += (- agent.getAnnouncedTime(entry.getKey())); // Nutzen ist negativ
//			}
//		}
//		return valuation;
//	}
//	
//	/**
//	 * @return die genannte Spanne: die Summe der genannten Zeiten für die an den Agenten zugewiesene Tasks
//	 */
//	@Override
//	public double getAnnouncedSpan(Shift instance) {
//		return -valuate(instance);
//	}
	
	
	/**
	 * @return die genannte Spanne: die Summe der genannten Zeiten für die an den Agenten zugewiesene Tasks
	 */
	@Override
	public double getAnnouncedSpan(Shift instance) {
		double announcedSpan = 0.0;
		// sum up the values of stated costs for this agent
		for(Entry<Integer, TaskAgent> entry : instance.getAssignment().entrySet()) {
			if(entry.getValue().equals(agent)) {
				announcedSpan += agent.getAnnouncedTime(entry.getKey());
			}
		}
		return announcedSpan;
	}
	
	@Override
	public double valuate(Shift instance) {
		return -getAnnouncedSpan(instance); // Nutzen ist negativ
	}
	
	/**
	 * @return die tatsächliche Spanne: die Summe der tatsächlich gemessenen Zeiten für die an den Agenten zugewiesene Tasks
	 */
	@Override
	public double getActualSpan(Shift instance) {
		double actualSpan = 0.0;
		// sum up the values of measured costs for this agent
		for(Entry<Integer, TaskAgent> entry : instance.getAssignment().entrySet()) {
			if(entry.getValue().equals(agent)) {
				actualSpan += agent.getActualTime(entry.getKey());
			}
		}
		return actualSpan;
	}
	
}
