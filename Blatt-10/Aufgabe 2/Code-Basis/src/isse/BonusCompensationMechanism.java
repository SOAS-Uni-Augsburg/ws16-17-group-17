package isse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import isse.agents.TaskAgent;

public class BonusCompensationMechanism extends QuasilinearMechanism<TaskAgent, Shift> {
	
	protected SchedulingProblem problem;
		
	public BonusCompensationMechanism(SchedulingProblem problem) {
		this.problem = problem;
	}
	

	@Override
	public Shift selection(Map<TaskAgent, Valuator<Shift>> typeProfile) {
		// TODO implement selection
		int numAgents = problem.getAgents().size();
		int numTasks = problem.getNumberTasks();
		Object [] agents = typeProfile.keySet().toArray();
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
				currentShift.assign(i, (TaskAgent) agents[Integer.parseInt(""+shiftStr.charAt(i))]);
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

	@Override
	public Map<TaskAgent, Double> getPayments(
			Map<TaskAgent, Valuator<Shift>> typeProfile) {
		// TODO implement payments
		return null;
	}
	
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
			temp.clear();
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
