package isse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import isse.agents.TaskAgent;

public class BonusCompensationMechanism extends QuasilinearMechanism<TaskAgent, Shift> {
	
	protected SchedulingProblem problem;
		
	public BonusCompensationMechanism(SchedulingProblem problem) {
		this.problem = problem;
	}
	

	@Override
	public Shift selection(Map<TaskAgent, Valuator<Shift>> typeProfile) {
		// implement selection
		int numAgents = problem.getAgents().size();
		int numTasks = problem.getNumberTasks();
		
		TaskAgent[] agents = new TaskAgent[numAgents];
		problem.getAgents().toArray(agents);
		// if a problem has reduced number of agents, but typeProfile remains the same (as we do for payment calculation: we simply ignore typeProfiles of missing agents)
		// typeProfile.keySet().toArray(agents); returns wrong agent list!
		
		ArrayList<String> possibleShifts = caculatePossibleShifts();
		//TEST
		for(String s : possibleShifts){
			System.out.println(s);
		}
		Shift currentShift = new Shift();
		Shift bestShift = null;
		
		for (String shiftStr : possibleShifts){
			//weise Tasks zu
			currentShift = new Shift();
			for (int i = 0; i < numTasks; i++){
				currentShift.assign(i, agents[Integer.parseInt(""+shiftStr.charAt(i))]);
			}
			//TEST
			System.out.println(currentShift.getMakeSpan());
			//teste auf geringste MakeSpan
			if (bestShift == null){
				bestShift = currentShift;
			}
			else if (currentShift.getMakeSpan() < bestShift.getMakeSpan()){
				bestShift = currentShift;
			}
		}
		return bestShift;
	}

	// TODO: remove code duplication in both mechanisms with template Methods, e.g. for selectedShiftWithoutAgentI computation
	@Override
	public Map<TaskAgent, Double> getPayments(
			Map<TaskAgent, Valuator<Shift>> typeProfile) {
		// implement payments
		
		int numberTasks = problem.getNumberTasks();
		Collection<TaskAgent> agents = problem.getAgents();
		
		Shift selectedShiftWithAgentI = selection(typeProfile);
		System.out.println("selectedShiftWithAgentI: " + selectedShiftWithAgentI);
		
		Map<TaskAgent, Double> payments = new HashMap<>();
		
		for (TaskAgent i : agents) {
			Collection<TaskAgent> agentsWithoutAgentI = new ArrayList<>(agents);
			agentsWithoutAgentI.remove(i);
			
			
			// Wie wäre die Auswahl ausgegangen, wenn Agent i nicht dabei ist
			SchedulingProblem problemWithoutAgentI = new SchedulingProblem(numberTasks, agentsWithoutAgentI);
			
			QuasilinearMechanism<TaskAgent, Shift> mechanismWithoutAgentI = new BonusCompensationMechanism(problemWithoutAgentI);
			Shift selectedShiftWithoutAgentI = mechanismWithoutAgentI.selection(typeProfile);
			System.out.println("selectedShiftWithoutAgentI: " + selectedShiftWithoutAgentI);

			// Produktionsdauer (Produktionsspanne), die ohne Agent i entstehen würde: Clarke-Steuer
			double clarkeTax = - selectedShiftWithoutAgentI.getMakeSpan();
			
			
			// Kompensation/Bonus (Agent wird bezahlt für die Dauer, die ihm tatsächlich entsteht (tatsächliche Spanne))
			Valuator<Shift> valuatorForAgentI = typeProfile.get(i);
			double actualSpanForAgentI = valuatorForAgentI.getActualSpan(selectedShiftWithAgentI);
			double bonus = actualSpanForAgentI;
			
			// Strafe
			
			// Die genannte Spanne für den nächstlangsamsten Agenten (wann ist der nächstletzte vor Agent i fertig)
			double maxAnnouncedSpanForOtherAgents = 0.0;
			for (TaskAgent j : agentsWithoutAgentI) {
				Valuator<Shift> valuator = typeProfile.get(j);
				double announcedSpan = valuator.getAnnouncedSpan(selectedShiftWithAgentI);
				if (announcedSpan >  maxAnnouncedSpanForOtherAgents) {
					maxAnnouncedSpanForOtherAgents = announcedSpan;
				}
			}
			
			// Die Strafe ist max {actualSpanForAgentI, maxAnnouncedSpanForOtherAgents}
			double penalty = Math.max(actualSpanForAgentI, maxAnnouncedSpanForOtherAgents);
			
			
			double payment = clarkeTax - bonus + penalty;
			payments.put(i, payment);
		}
		
		return payments;
	}
	
	// TODO: Über permutationen schreiben
	// Micha: eigentlich Variation mit Wiederholung, afu English aber
	// k-permutation, siehe
	// https://de.wikipedia.org/wiki/Variation_(Kombinatorik)#Variation_mit_Wiederholung
	public ArrayList<String> caculatePossibleShifts(){
		int numAgents = problem.getAgents().size();
		int numTasks = problem.getNumberTasks();
		ArrayList<String> possibleShifts = new ArrayList<String>();
		//init List
		for (int i = 0; i < numAgents; i++){
			possibleShifts.add(""+i);
		}
		
		for (int i = 0; i < numTasks - 1; i++){
			ArrayList<String> temp = new ArrayList<String>();
			temp.clear(); // FIXME: ist nicht notwendig?
			for (String str : possibleShifts){
				for (int j = 0; j < numAgents; j++){
					temp.add(str+j);
				}
			}
			possibleShifts = temp;
		}
		return possibleShifts;
	}
}
