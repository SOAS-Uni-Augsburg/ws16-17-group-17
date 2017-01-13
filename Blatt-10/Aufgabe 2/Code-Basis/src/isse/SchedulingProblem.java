package isse;

import java.util.Collection;

import isse.agents.*;

/**
 * This class wraps all information
 * needed about an instance of a 
 * task scheduling problem
 * @author alexander
 *
 */
public class SchedulingProblem {
	protected int numberTasks;
	protected Collection<TaskAgent> agents;

	public SchedulingProblem(int numberTasks, Collection<TaskAgent> agents) {
		super();
		this.numberTasks = numberTasks;
		this.agents = agents;
	}
	
	public int getNumberTasks() {
		return numberTasks;
	}

	public void setNumberTasks(int numberTasks) {
		this.numberTasks = numberTasks;
	}

	public Collection<TaskAgent> getAgents() {
		return agents;
	}

	public void setAgents(Collection<TaskAgent> agents) {
		this.agents = agents;
	}	
}
