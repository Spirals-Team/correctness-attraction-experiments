package experiment;

import perturbation.location.PerturbationLocation;

import java.util.List;

/**
 * Created by spirals on 05/04/16.
 */
public interface Explorer {

    double TOLERANCE = 70.0f;

    static void addToFragilityList(Tuple result, int total, PerturbationLocation location, List<PerturbationLocation> locationExceptionFragile,
                        List<PerturbationLocation> locationAntiFragile , List<PerturbationLocation> locationOracleFragile) {
        if (result.get(0) == total)
            locationAntiFragile.add(location);
        else if (Runner.perc(result.get(1), total) >= TOLERANCE)//OracleFragile
            locationOracleFragile.add(location);
        else if (Runner.perc(result.get(2), total) >= TOLERANCE)//ExceptionFragile
            locationExceptionFragile.add(location);
    }

    void run(int indexOfTask, PerturbationLocation location);

    void log();

}
