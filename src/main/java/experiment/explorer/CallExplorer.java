package experiment.explorer;

import experiment.Logger;
import experiment.Runner;
import experiment.Tuple;
import experiment.campaign.Campaign;
import perturbation.PerturbationEngine;
import perturbation.enactor.NCallEnactorImpl;
import perturbation.location.PerturbationLocation;
import perturbation.log.LoggerImpl;
import perturbation.perturbator.Perturbator;

/**
 * Created by beyni on 30/04/16.
 */
public class CallExplorer extends ExplorerImpl {

    private int[][] nbCallReferencePerLocationPerTask;

    public CallExplorer(Campaign campaign) {
        super(campaign, "CallExplorer");
        nbCallReferencePerLocationPerTask = new int[Runner.locations.size()][Runner.numberOfTask];
        //Logger contains : Success Failure Exception Call Perturbation NumberOfExecution
        Logger.init(Runner.locations.size(), Runner.numberOfTask, super.perturbators.size(), 6);
        PerturbationEngine.loggers.put(super.name, new LoggerImpl());
    }

    @Override
    public void run(int indexOfTask, PerturbationLocation location) {
        //reference run : no perturbation
        PerturbationEngine.loggers.get(super.name).logOn(location);
        Runner.runPerturbation(indexOfTask);
        int currentNbCall = PerturbationEngine.loggers.get(super.name).getCalls(location);
        nbCallReferencePerLocationPerTask[Runner.locations.indexOf(location)][indexOfTask] = currentNbCall;
        PerturbationEngine.loggers.get(super.name).reset();

        super.run(indexOfTask, location);
    }

    @Override
    public void runOnePerturbator(int indexOfTask, PerturbationLocation location, Perturbator perturbator) {
        int currentNbCall = nbCallReferencePerLocationPerTask[Runner.locations.indexOf(location)][indexOfTask];
        location.setPerturbator(perturbator);
        for (int indexOfCall = 1 ; indexOfCall < currentNbCall + 1; indexOfCall++) {
            PerturbationEngine.loggers.get(super.name).logOn(location);
            Tuple result = runAtTheIndexOfCall(indexOfCall, indexOfTask, location);
            Logger.log(Runner.locations.indexOf(location), indexOfTask, super.perturbators.indexOf(perturbator), result, super.name);
            PerturbationEngine.loggers.get(super.name).reset();
        }
    }

    private Tuple runAtTheIndexOfCall(int indexOfCall, int indexOfTask, PerturbationLocation location) {
        location.setEnactor(new NCallEnactorImpl(indexOfCall, location));
        return Runner.runPerturbation(indexOfTask);
    }

    @Override
    public void log() {
        Logger.writeOutput(this, campaign);
    }
}
