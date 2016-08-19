package main;

import experiment.Manager;
import experiment.exploration.Exploration;
import experiment.exploration.IntegerExplorationPlusOne;
import experiment.explorer.CallExplorer;
import experiment.explorer.Explorer;
import perturbation.PerturbationEngine;
import perturbation.enactor.NCallEnactorImpl;
import perturbation.location.PerturbationLocation;
import perturbation.location.PerturbationLocationImpl;
import perturbation.log.LoggerImpl;
import perturbation.perturbator.AddNPerturbatorImpl;

public class ExploreExample {

	static {
		ExploreExample.initPerturbationLocation0();
	}

	static PerturbationLocation __L0;
	static PerturbationLocation __L1;

	static int function(int bound) {
		int acc = 0;
		int mask = 0x2;
		for (int i = bound; i > 0; i--) {
			int valueOfI = PerturbationEngine.pint(__L0, i);
//			int valueOfMask = PerturbationEngine.pint(__L1, mask);
			int valueOfMask = mask;
			acc |= PerturbationEngine.pint(__L1, valueOfI >> valueOfMask);
			trace += acc + "&" + valueOfI + "&" + valueOfMask + "\\\\\n";
		}
		return acc;
	}

	static String trace = "";

	static int cpt = 0;

	static int cptError = 0;

	static void explore(int bound) {
		PerturbationLocation location = __L1;
		PerturbationEngine.loggers.put("ExploreExample", new LoggerImpl());
		PerturbationEngine.loggers.get("ExploreExample").logOn(location);
		int out = function(bound);
		int nbCall = PerturbationEngine.loggers.get("ExploreExample").getCalls(location);
		location.setPerturbator(new AddNPerturbatorImpl(1));
		for (int n = 0; n < nbCall; n++) {
			cpt++;
			trace = "";
			location.setEnactor(new NCallEnactorImpl(n));
			int output = function(bound);
			if (out != output) {
				cptError++;
				System.err.println(bound + " : " + n + " : ERROR");
			}
//			System.out.println(trace);
		}
	}

	public static void main(String[] args) {
//		explore(8);
//		IntStream.range(0, 100).forEach(ExploreExample::explore);
//		System.out.println(cpt);
//		System.out.println(cptError);
		Manager manager = new ExampleManager(100);
		Exploration exploration = new IntegerExplorationPlusOne();
		Explorer explorer = new CallExplorer(manager, exploration);
		explorer.run();
	}

	private static void initPerturbationLocation0() {
		ExploreExample.__L0 = new PerturbationLocationImpl("MainInstr.java:4", 0, "Numerical");
		ExploreExample.__L1 = new PerturbationLocationImpl("MainInstr.java:4", 0, "Numerical");
	}

}


