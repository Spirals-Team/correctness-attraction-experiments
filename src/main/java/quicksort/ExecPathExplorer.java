package quicksort;

import experiment.Util;
import perturbation.PerturbationEngine;
import perturbation.enactor.NCallEnactorImpl;
import perturbation.enactor.NeverEnactorImpl;
import perturbation.location.PerturbationLocation;
import perturbation.location.PerturbationLocationImpl;
import perturbation.log.LoggerImpl;
import perturbation.perturbator.AddNPerturbatorImpl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by spirals on 12/04/16.
 */
public class ExecPathExplorer {

	static final int SIZE_ARRAY = 500;

	static final int NUMBER_TASK = 1;

	static Set<List<Integer>> pathSuccess = new HashSet<>();

	static Set<List<Integer>>  pathFailure = new HashSet();

	static List<Integer> unperturbed = new ArrayList<>();

	static int[] runQuicksort(int[] task) {
		QuickSortForExecPath.sort(task, 0, task.length - 1);
		return task;
	}

	public static void main(String[] args) throws Exception {
		List<PerturbationLocation> locations = PerturbationLocationImpl.getLocationFromClass(QuickSortForExecPath.class);
		locations.forEach(location -> location.setPerturbator(new AddNPerturbatorImpl(1)));
		QuickSortManager manager = new QuickSortManager(NUMBER_TASK, SIZE_ARRAY);
		PerturbationEngine.loggers.put("QuicksortExecPath", new LoggerImpl());
		int numberOfExecutions = 0;
		for (int i = 0; i < NUMBER_TASK; i++) {
			System.out.println(i + " task");
			locations.forEach(location -> PerturbationEngine.loggers.get("QuicksortExecPath").logOn(location));
			runQuicksort(manager.getTask(i));
			int[] nbCallRef = new int[locations.size()];
			locations.forEach(location -> nbCallRef[locations.indexOf(location)] = PerturbationEngine.loggers.get("QuicksortExecPath").getCalls(location));
			PerturbationEngine.loggers.get("QuicksortExecPath").reset();
			List<Integer> lst = new ArrayList<>();
			lst.addAll(LoggerExecPath.execPath);
			unperturbed.addAll(lst);
			LoggerExecPath.reset();
			for (PerturbationLocation location : locations) {
				System.out.println(Util.getStringPerc(locations.indexOf(location), locations.size()));
				for (int call = 0; call < nbCallRef[locations.indexOf(location)]; call++) {
					location.setEnactor(new NCallEnactorImpl(call));
					int[] task = manager.getTask(i);
					try {
						runQuicksort(task);
						if (!manager.getOracle().assertPerturbation(manager.getTask(i), task)) {
							lst = new ArrayList<>();
							lst.addAll(LoggerExecPath.execPath);
							pathSuccess.add(lst);
						} else {
							lst = new ArrayList<>();
							lst.addAll(LoggerExecPath.execPath);
							pathFailure.add(lst);
						}
					} catch (Exception e) {
						lst = new ArrayList<>();
						lst.addAll(LoggerExecPath.execPath);
						pathFailure.add(lst);
					}
					LoggerExecPath.reset();
					numberOfExecutions++;
				}
				location.setEnactor(new NeverEnactorImpl());
			}
		}
		System.out.println(numberOfExecutions);
		log();
	}

	static void writeNbPair(FileWriter writer, List<Integer> lst) {
		try {
			for (Integer nbPair : lst)
				writer.write(nbPair + " ");
			writer.write("\n");
		} catch (IOException ignored) {}
	}

	static void log() {
		try {
			FileWriter writer = new FileWriter("results/quicksort/outputExec.txt");
			writer.write(pathSuccess.size() + " " + pathFailure.size() + "\n");
			writeNbPair(writer, unperturbed);
			pathSuccess.stream().forEach(path -> writeNbPair(writer, path));
			pathFailure.stream().forEach(path -> writeNbPair(writer, path));
			writer.close();
		} catch (Exception ignored) {
		}
	}

}
