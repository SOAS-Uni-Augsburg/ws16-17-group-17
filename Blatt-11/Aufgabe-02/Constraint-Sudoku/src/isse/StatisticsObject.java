package isse;

import java.util.concurrent.TimeUnit;

/**
 * Instrumentation of code to evaluate 
 * different algorithms
 * @author alexander
 *
 */
public class StatisticsObject {
	protected int recursiveCalls;
	protected long start;
	protected long end;
	protected long runtime;
	
	public void tickRuntime() {
		start = System.nanoTime();
	}
	
	public void tockRuntime() {
		end = System.nanoTime();
		runtime = end - start;
		runtime = TimeUnit.SECONDS.convert(runtime, TimeUnit.NANOSECONDS);
	}

	public void markRecursiveCall() {
		++recursiveCalls;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("+-+-+-+-+-+ STATISTICS +-+-+-+-+-+-+\n");
		sb.append(" + Elapsed time in secs: " + runtime + "\n");
		sb.append(" + recursive calls : " + recursiveCalls + "\n");
		sb.append("------------------------------------\n");
		return sb.toString();
	}
}
