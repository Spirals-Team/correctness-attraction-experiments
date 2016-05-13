package experiment.explorer;

import perturbation.location.PerturbationLocation;

/**
 * Created by spirals on 05/04/16.
 */
public interface Explorer {

    /**
     * Method to run one time the subject on a task without any perturbation.
     */
    void runReference(int indexOfTask, PerturbationLocation location);

    /**
     * Method to run the explorer on the given parameters
     * @param indexOfTask
     * @param location
     */
    void run(int indexOfTask, PerturbationLocation location);

    /**
     * Initialize the PerturbationEngine.loggers and experiment.Logger with the setup of the exp.
     */
    void initLogger();

    /**
     * Output the logging after that the Explorer rand
     */
    void log();

    /**
     * @return getType() of the given Exploration
     */
    String getTypeOfExploration();

}
