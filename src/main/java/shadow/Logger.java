package shadow;

import experiment.Util;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by bdanglot on 12/08/16.
 */
public class Logger {

	private int[] successPerLocation;
	private int[] failurePerLocation;
	private int[] executionPerLocation;

	private int nbRequest;

	public Logger(int numberOfLocations) {
		this.successPerLocation = new int[numberOfLocations];
		this.failurePerLocation = new int[numberOfLocations];
		this.executionPerLocation = new int[numberOfLocations];
		this.nbRequest = 0;
	}

	/**
	 * @return nbRequest, sum Exec, sum Success, sum Failures
	 */
	public int[] getState() {
		int nbLocation = this.successPerLocation.length;
		int[] states = new int[4];
		states[0] = this.nbRequest;
		for (int i = 0; i < nbLocation; i++) {
			states[1] += this.executionPerLocation[i];
			states[2] += this.successPerLocation[i];
			states[3] += this.failurePerLocation[i];
		}
		return states;
	}

	public void logRequest() {
		this.nbRequest++;
	}

	public synchronized void count(boolean assertion, int index) {
		if (assertion)
			this.successPerLocation[index]++;
		else
			this.failurePerLocation[index]++;
		this.executionPerLocation[index]++;
	}

	public synchronized void log() {
		try {
			FileWriter writer = new FileWriter("results/shadow/results.txt", false);
			String format = "%-10s %-10s %-10s %-10s %-25s";
			int accSuccess = 0, accExecution = 0, numberOfPPExplored = 0;
			writer.write(String.format(format, "IndexLoc", "#Exec", "#Success", "#Failure", "CorrectnessRatio") + "\n");
			for (int i = 0; i < this.executionPerLocation.length; i++) {
				if (this.executionPerLocation[i] != 0) {
					writer.write(String.format(format, i, this.executionPerLocation[i],
							this.successPerLocation[i], this.failurePerLocation[i],
							Util.getStringPerc(this.successPerLocation[i], this.executionPerLocation[i])) + "\n");
					accSuccess += this.successPerLocation[i];
					accExecution += this.executionPerLocation[i];
					numberOfPPExplored++;
				}
			}
			format = "%-25s %-25s";
			writer.write(String.format("%-25s %-10s %-10s %-25s", "PP explored : ", numberOfPPExplored, this.executionPerLocation.length, Util.getStringPerc(numberOfPPExplored, this.executionPerLocation.length)) + "\n");
			writer.write(String.format(format, "# Executions : ", accExecution) + "\n");
			writer.write(String.format(format, "# Success : ", accSuccess) + "\n");
			writer.write(String.format(format, "Correctness ratio : ", Util.getStringPerc(accSuccess, accExecution)) + "\n");
			writer.write(String.format(format, "# Request : ", this.nbRequest) + "\n");
			writer.write(String.format(format, "Execution ratio : ", Util.getStringPerc(accExecution, this.nbRequest)) + "\n");
			writer.close();
		} catch (IOException ignored) {
		}
		System.out.println("End log");
	}
}
