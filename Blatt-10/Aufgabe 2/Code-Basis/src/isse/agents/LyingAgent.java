package isse.agents;

import java.util.HashMap;

public class LyingAgent extends BasicHonestAgent {
	private static boolean nobodyCanByTrusted = false;
	
	private boolean isLying = false;
	
	public LyingAgent(HashMap<Integer, Double> minimalTimes) {
		super(minimalTimes);
	}

	public LyingAgent(HashMap<Integer, Double> minimalTimes, boolean lying) {
		super(minimalTimes);
		setLying(lying);
	}
	
	@Override
	public double getMinimalTime(int taskKey) {
		return minimalTimes.get(taskKey);
	}

	@Override
	public double getAnnouncedTime(int taskKey) {
		if (isLying || nobodyCanByTrusted) {
			return getMinimalTime(taskKey) - 1;
			// man kann es auch randomisiert machen, dann aber in protected Map<Integer, Double> announcedTimes; im Konstruktor speichern
			// und dadurch mit super.getAnnouncedTime(taskKey); immer das Gleiche ausgeben
		}
		return super.getMinimalTime(taskKey);
	}

	@Override
	public double getActualTime(int taskKey) {
		if (isLying || nobodyCanByTrusted) {
			return getMinimalTime(taskKey) + 5; 
			// man kann es auch randomisiert machen, dann aber in protected Map<Integer, Double> actualTimes; im Konstruktor speichern
			// und dadurch mit super.getActualTime(taskKey); immer das Gleiche ausgeben
		}
		return super.getMinimalTime(taskKey);
	}

	public boolean isLying() {
		return isLying;
	}

	public void setLying(boolean isLying) {
		this.isLying = isLying;
	}

	public static boolean isNobodyCanByTrusted() {
		return nobodyCanByTrusted;
	}

	public static void setNobodyCanByTrusted(boolean nobodyCanByTrusted) {
		LyingAgent.nobodyCanByTrusted = nobodyCanByTrusted;
	}
	
}
