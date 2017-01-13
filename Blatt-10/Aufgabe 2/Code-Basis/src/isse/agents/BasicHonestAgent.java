package isse.agents;

import java.util.HashMap;
import java.util.Map;

public class BasicHonestAgent implements TaskAgent {

	protected Map<Integer, Double> minimalTimes; 
	protected Map<Integer, Double> announcedTimes;
	protected Map<Integer, Double> actualTimes;
	
	public BasicHonestAgent(HashMap<Integer, Double> minimalTimes) {
		super();
		this.minimalTimes = new HashMap<Integer, Double>(minimalTimes);
		this.announcedTimes = new HashMap<Integer, Double>(minimalTimes);
		this.actualTimes = new HashMap<Integer, Double>(minimalTimes);
	}

	@Override
	public double getMinimalTime(int taskKey) {
		return minimalTimes.get(taskKey);
	}

	@Override
	public double getAnnouncedTime(int taskKey) {
		return announcedTimes.get(taskKey);
	}

	@Override
	public double getActualTime(int taskKey) {
		return actualTimes.get(taskKey);
	}

}
