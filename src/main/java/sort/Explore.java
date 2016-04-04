package sort;

import perturbation.location.PerturbationLocation;

/**
 * Created by spirals on 04/04/16.
 */
public interface Explore {

    void run(int indexOfArray, PerturbationLocation location);

    void log();

}
