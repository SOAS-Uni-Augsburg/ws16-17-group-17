package isse;

import isse.agents.TaskAgent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class VCGMechanism extends QuasilinearMechanism<TaskAgent,Shift> {
	
	protected SchedulingProblem problem;
	// private int numberTasks;
		
	public VCGMechanism(SchedulingProblem problem) {
		super();
		this.problem = problem;
	}

	// TODO: save some values such as number tasks as member variables BUT: SchedulingProglem is mutable! this values can change
	@Override
	public Shift selection(Map<TaskAgent, Valuator<Shift>> typeProfile) {
		// implement selection
		
		// Wir wählen diejenige Option, die die Summe der genannten Nutzen maximiert
		// Also eine Option, die die gesamte summerte Arbeitszeit minimiert (Arbeitszeit geht negativ in den Nutzen ein)
		
		// Greedy Ansatz: für jeden Task gibt es einen Agenten, der diesen Task am schnellsten macht -> diesen Agenten wählen wir für diesen Task aus
		
		int numberTasks = problem.getNumberTasks();
		Collection<TaskAgent> agents = problem.getAgents();
		Shift outcome = new Shift();
		
		// über tasks iterieren, taskKeys fangen mit 0 an
		for (int taskKey = 0; taskKey < numberTasks; taskKey++) {
			TaskAgent fastestAgent = null;
			double fastestTime = Double.POSITIVE_INFINITY;
			for (TaskAgent agent: agents) {
				double announcedTime = agent.getAnnouncedTime(taskKey);
				if (announcedTime < fastestTime) {
					fastestTime = announcedTime;
					fastestAgent = agent;
				}
			}
			outcome.assign(taskKey, fastestAgent);
		}
		
		return outcome;
	}

	@Override
	public Map<TaskAgent, Double> getPayments(
			Map<TaskAgent, Valuator<Shift>> typeProfile) {
		// implement payments
		
		int numberTasks = problem.getNumberTasks();
		Collection<TaskAgent> agents = problem.getAgents();
		
		Shift selectedShiftWithI = selection(typeProfile);
		
		Map<TaskAgent, Double> payments = new HashMap<>();
		
		for (TaskAgent i : agents) {
			Collection<TaskAgent> agentsWithoutI = new ArrayList<>(agents);
			agentsWithoutI.remove(i);
			
			
			// Wie wäre die Auswahl ausgegangen, wenn Agent i nicht dabei ist
			SchedulingProblem problemWithoutI = new SchedulingProblem(numberTasks, agentsWithoutI);
			
			QuasilinearMechanism<TaskAgent, Shift> qm = new VCGMechanism(problemWithoutI);
			Shift selectedShiftWithoutI = qm.selection(typeProfile);

			// Gesamtnutzen, der ohne Agent i entstehen würde: Clarke-Steuer
			double clarkeTax = 0.0;
			for (TaskAgent j : agentsWithoutI) {
				clarkeTax += typeProfile.get(j).valuate(selectedShiftWithoutI);
			}
			
			
			// Gesamtnutzen, den die anderen Agenten bekommen, falls  Agent i teilnimmt
			double utilityForOtherAgents = 0.0;
			for (TaskAgent j : agentsWithoutI) {
				utilityForOtherAgents += typeProfile.get(j).valuate(selectedShiftWithI);
			}
			
			double payment = clarkeTax - utilityForOtherAgents;
			payments.put(i, payment);
		}
		
		return payments;
	}


}
