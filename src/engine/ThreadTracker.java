package engine;

public interface ThreadTracker {

	public void decreaseThreadCount();

	public void increaseThreadCount();

	public int getThreadCount();

	public boolean canIncrease();
}
