package experiment;

import perturbation.PerturbationEngine;
import perturbation.enactor.NCallEnactorImpl;
import perturbation.location.PerturbationLocation;
import perturbation.perturbator.Perturbator;

import java.util.List;

/**
 * Created by beyni on 30/04/16.
 */
public class CallExplorer extends ExplorerImpl {

    private int[][] nbCallReferencePerLocationPerTask;

    public CallExplorer(List<Perturbator> perturbatorToBeRun) {
        super(perturbatorToBeRun);
        nbCallReferencePerLocationPerTask = new int[Runner.locations.size()][Runner.numberOfTask];
    }

    @Override
    public void run(int indexOfTask, PerturbationLocation location) {
        //reference run : no perturbation
        PerturbationEngine.logger.logOn(location);
        Runner.runPerturbation(indexOfTask);
        int currentNbCall = PerturbationEngine.logger.getCalls(location);
        nbCallReferencePerLocationPerTask[Runner.locations.indexOf(location)][indexOfTask] = currentNbCall;
        PerturbationEngine.logger.reset();

        super.run(indexOfTask, location);
    }

    @Override
    public void runOnePerturbator(int indexOfTask, PerturbationLocation location, Perturbator perturbator) {
        int currentNbCall = nbCallReferencePerLocationPerTask[Runner.locations.indexOf(location)][indexOfTask];
        location.setPerturbator(perturbator);
        for (int indexOfCall = 1 ; indexOfCall < currentNbCall + 1; indexOfCall++)
            runAtTheIndexOfCall(indexOfCall, indexOfTask, location);
    }

    private Tuple runAtTheIndexOfCall(int indexOfCall, int indexOfTask, PerturbationLocation location) {
        location.setEnactor(new NCallEnactorImpl(indexOfCall, location));
        return Runner.runPerturbation(indexOfTask);
    }

    @Override
    public void log() {

    }
}
