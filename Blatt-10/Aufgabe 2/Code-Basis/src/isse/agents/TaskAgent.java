package isse.agents;

public interface TaskAgent {

	/**
	 * This ought to be private information
	 * @param taskKey identifier of a task
	 * @return the minimal time to perform task taskKey
	 */
	double getMinimalTime(int taskKey);
	
	double getAnnouncedTime(int taskKey);
	
	double getActualTime(int taskKey);
}
