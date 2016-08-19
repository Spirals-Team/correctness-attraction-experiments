package main;

import experiment.CallableImpl;
import experiment.ManagerImpl;
import experiment.Oracle;

/**
 * Created by bdanglot on 12/08/16.
 */
public class ExampleManager extends ManagerImpl<Integer, Integer> {

	private int currentTask = 0;

	public ExampleManager(int numberOfTask) {
		super(23);
		this.CUP = ExampleInstr.class;
		super.initialize(numberOfTask, 1);
	}

	@Override
	protected Integer generateOneTask() {
		return super.tasks.size();
	}

	@Override
	public CallableImpl<Integer, Integer> getCallable(Integer input) {
		return new CallableImpl<Integer, Integer>(input) {
			@Override
			public Integer call() throws Exception {
				return ExampleInstr.function(this.input);
			}
		};
	}

	@Override
	public Integer getTask(int indexOfTask) {
		return new Integer(super.tasks.get(indexOfTask));
	}

	@Override
	public Oracle<Integer, Integer> getOracle() {
		return (input, output) -> output == Example.function(input);
	}

	@Override
	public String getName() {
		return "example";
	}

	@Override
	public String getHeader() {
		return "example header\n";
	}
}
