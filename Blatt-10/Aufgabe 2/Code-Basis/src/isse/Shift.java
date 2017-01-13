package isse;

import isse.agents.TaskAgent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Shift {
	protected Map<Integer, TaskAgent> assignment;
	protected double makeSpan = Double.NEGATIVE_INFINITY;
	protected double overallWorkDuration = Double.NEGATIVE_INFINITY;
	
	public Shift() {
		this.assignment = new HashMap<Integer, TaskAgent>();
	} 
	
	public Shift(Shift shift) {
		this.assignment = new HashMap<Integer, TaskAgent>(shift.getAssignment());
	}

	public Map<Integer, TaskAgent> getAssignment() {
		return assignment;
	}
	
	public void assign(int index, TaskAgent agent) {
		assignment.put(index, agent);
	}
	
	public double getMakeSpan() {
		if(makeSpan == Double.NEGATIVE_INFINITY) {
			Map<TaskAgent, Double> workPerAgent = new HashMap<TaskAgent,Double>();
			for(Entry<Integer, TaskAgent> entry : assignment.entrySet()) {
				double work = entry.getValue().getAnnouncedTime(entry.getKey());
				if(workPerAgent.containsKey(entry.getValue())){
					work += workPerAgent.get(entry.getValue());
				}
				workPerAgent.put(entry.getValue(), work);
			}
			
			// now take the maximal value of all these
			makeSpan = Collections.max(workPerAgent.values());
		}
		return makeSpan;
	}
	
	public double getOverallWorkDuration() {
		if(overallWorkDuration == Double.NEGATIVE_INFINITY) {
			overallWorkDuration = 0.0;
			for(Entry<Integer, TaskAgent> entry : assignment.entrySet()) {
				overallWorkDuration += entry.getValue().getAnnouncedTime(entry.getKey());
			}
		}
		return overallWorkDuration;
	}
	
	@Override
	public String toString() {
		return "Shift duration: "+getOverallWorkDuration() + " - makespan " + getMakeSpan();
	}
}
