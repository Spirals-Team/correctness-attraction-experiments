package main;

import experiment.Main;
import experiment.Manager;
import experiment.exploration.Exploration;
import experiment.exploration.IntegerExplorationPlusOne;
import experiment.explorer.CallExplorer;
import experiment.explorer.Explorer;
import perturbation.PerturbationEngine;
import perturbation.enactor.NCallEnactorImpl;
import perturbation.location.PerturbationLocation;
import perturbation.log.LoggerImpl;
import perturbation.perturbator.AddNPerturbatorImpl;

import java.util.stream.IntStream;

public class ExploreExample {

	static String trace = "";

	static int cpt = 0;

	static int cptError = 0;

	static void explore(int bound) {
		PerturbationLocation location = ExampleInstr.__L6;
		PerturbationEngine.loggers.put("ExploreExample", new LoggerImpl());
		PerturbationEngine.loggers.get("ExploreExample").logOn(location);
		int out = ExampleInstr.function(bound);
		int nbCall = PerturbationEngine.loggers.get("ExploreExample").getCalls(location);
		location.setPerturbator(new AddNPerturbatorImpl(1));
		for (int n = 0; n < nbCall; n++) {
			cpt++;
			ExampleInstr.trace = "";
			location.setEnactor(new NCallEnactorImpl(n));
			int output = ExampleInstr.function(bound);
			if (out != output) {
				cptError++;
				System.err.println(bound + " : " + n + " : ERROR");
				System.out.println(ExampleInstr.trace);
			}
		}
	}

	private static void explorePPOnI() {
		cpt = 0;
		cptError = 0;
		IntStream.range(0, 100).forEach(ExploreExample::explore);
		System.out.println(cpt);
		System.out.println(cptError);
	}



	public static void main(String[] args) {
//		explore(3);
		explorePPOnI();
//		systExploration();
	}

	private static void systExploration() {
		Main.verbose = true;
		Manager manager = new ExampleManager(100);
		Exploration exploration = new IntegerExplorationPlusOne();
		Explorer explorer = new CallExplorer(manager, exploration);
		explorer.run();
	}

}


