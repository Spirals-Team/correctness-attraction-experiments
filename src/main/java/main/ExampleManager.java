package main;

import experiment.CallableImpl;
import experiment.ManagerImpl;
import experiment.Oracle;

/**
 * Created by bdanglot on 12/08/16.
 */
public class ExampleManager extends ManagerImpl<Integer, Integer> {

	private int currentTask = 0;

	@Override
	protected Integer generateOneTask() {
		return currentTask++;
	}

	@Override
	public CallableImpl<Integer, Integer> getCallable(Integer input) {
		return new CallableImpl<Integer, Integer>(input) {
			@Override
			public Integer call() throws Exception {
				return Main.function(this.input);
			}
		}
	}

	@Override
	public Oracle<Integer, Integer> getOracle() {

		return null;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public String getHeader() {
		return null;
	}
}
