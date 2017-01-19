package isse.agents;

import java.util.HashMap;

public class LyingAgent extends BasicHonestAgent {
	public static boolean nobodyCanByTrusted = false;
	
	private boolean isLying = false;
	
	
	public LyingAgent(HashMap<Integer, Double> minimalTimes) {
		super(minimalTimes);
	}

	@Override
	public double getMinimalTime(int taskKey) {
		return minimalTimes.get(taskKey);
	}

	@Override
	public double getAnnouncedTime(int taskKey) {
		if (isLying || nobodyCanByTrusted) {
			return getMinimalTime(taskKey) - 1; // man kann es auch randomisiert machen, dann aber in protected Map<Integer, Double> announcedTimes; speichern und dadurch immer das Gleiche ausgeben
		}
		return super.getAnnouncedTime(taskKey);
	}

	@Override
	public double getActualTime(int taskKey) {
		if (isLying || nobodyCanByTrusted) {
			return getMinimalTime(taskKey) + 5;  // man kann es auch randomisiert machen, dann aber in protected Map<Integer, Double> actualTimes; speichern und dadurch immer das Gleiche ausgeben
		}
		return super.getActualTime(taskKey);
	}

	public boolean isLying() {
		return isLying;
	}

	public void setLying(boolean isLying) {
		this.isLying = isLying;
	}
}
