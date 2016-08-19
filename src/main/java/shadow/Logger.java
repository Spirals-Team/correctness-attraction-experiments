package shadow;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by bdanglot on 12/08/16.
 */
public class Logger {

	private int[] successPerLocation;
	private int[] failurePerLocation;
	private int[] executionPerLocation;

	public Logger(int numberOfLocations) {
		this.successPerLocation = new int[numberOfLocations];
		this.failurePerLocation = new int[numberOfLocations];
		this.executionPerLocation = new int[numberOfLocations];
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
			FileWriter writer = new FileWriter("output", false);
			String format = "%-10s %-10s %-10s %-10s %-25s";
			writer.write(String.format(format, "IndexLoc", "#Exec", "#Success", "#Failure", "CorrectnessRatio") + "\n");
			for (int i = 0; i < this.executionPerLocation.length; i++) {
				if (this.executionPerLocation[i] != 0)
					writer.write(String.format(format, i, this.executionPerLocation[i],
							this.successPerLocation[i], this.failurePerLocation[i],
							getStringPerc(this.successPerLocation[i], this.executionPerLocation[i])) + "\n");
			}
			writer.close();
		} catch (IOException ignored) {
		}
		System.out.println("End log");
	}

	public static String getStringPerc(long nb, long total) {
		double perc = perc(nb, total);
		String ret = dash(perc);
		return ret + " " + String.format("%.2f", perc);
	}

	public static double perc(long nb, long total) {
		return (double) nb / (double) total * 100;
	}

	public static String dash(double perc) {
		String dash = "";
		for (int d = 0; d < perc / 5; d++) dash += "-";
		return dash;
	}

}
