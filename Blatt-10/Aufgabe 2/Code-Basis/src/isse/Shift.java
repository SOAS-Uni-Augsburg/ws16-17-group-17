package isse;

import isse.agents.TaskAgent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Beschreibt eine mögliche Taskzuweisung an Agenten/(Arbeits)Plan/ein Element x aus der Auswahlmenge X; eine Option, die der Mechanismus wählen muss
 *
 */
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
	
	/**
	 * @return die Produktionsspanne: die maximale genannte (angekündigte) Spanne unter allen Agenten
	 */
	public double getMakeSpan() {
		if(makeSpan == Double.NEGATIVE_INFINITY) {
			// Spanne für jeden Agenten berechnen: Summe der Zeiten für die an einen Agenten zugewiesenen Tasks
			Map<TaskAgent, Double> workPerAgent = new HashMap<TaskAgent,Double>();
			for(Entry<Integer, TaskAgent> entry : assignment.entrySet()) {
				TaskAgent agent = entry.getValue();
				Integer taskKey = entry.getKey();
				double work = agent.getAnnouncedTime(taskKey);
				if(workPerAgent.containsKey(agent)){
					work += workPerAgent.get(agent);
				}
				workPerAgent.put(agent, work);
			}
			
			// now take the maximal value of all these
			makeSpan = Collections.max(workPerAgent.values());
		}
		return makeSpan;
	}
	
	/**
	 * @return die gesamte summierte genannte (angekündigte) Arbeitszeit von allen Agenten
	 */
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
		return "Shift duration: "+ getOverallWorkDuration() + " - makespan " + getMakeSpan();
	}
}
