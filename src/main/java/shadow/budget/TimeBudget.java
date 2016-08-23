package shadow.budget;

import experiment.Util;

/**
 * Created by bdanglot on 19/08/16.
 */
public class TimeBudget implements Budget {

	private long budget;
	private long startTime;

	public TimeBudget(long budget) {
		this.startTime = System.currentTimeMillis();
		this.budget = budget;
	}

	@Override
	public boolean hasToRun() {
		return System.currentTimeMillis() - this.startTime < this.budget;
	}

	@Override
	public void update() {
		//empty
	}

	@Override
	public String toString() {
		return Util.getStringPerc((System.currentTimeMillis() - this.startTime), this.budget);
	}
}
