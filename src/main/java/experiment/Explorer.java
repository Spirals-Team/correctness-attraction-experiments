package experiment;

import perturbation.location.PerturbationLocation;

/**
 * Created by spirals on 05/04/16.
 */
public interface Explorer {

    void run(int indexOfTask, PerturbationLocation location);

    void log();

}
