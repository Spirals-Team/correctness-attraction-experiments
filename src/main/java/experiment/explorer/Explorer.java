package experiment.explorer;

import experiment.Tuple;
import experiment.Util;
import perturbation.location.PerturbationLocation;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by spirals on 05/04/16.
 */
public interface Explorer {

    @Deprecated
    double TOLERANCE = 70.0f;

    @Deprecated
    static void addToFragilityList(Tuple result, int total, PerturbationLocation location,
                                   List<PerturbationLocation> locationExceptionFragile, List<PerturbationLocation> locationSuperAntiFragile,
                                   List<PerturbationLocation> locationAntiFragile , List<PerturbationLocation> locationOracleFragile) {
        if (result.get(0) == total && result.get(0) != 0)//Super - Antifragile
            locationSuperAntiFragile.add(location);
        else if (Util.perc(result.get(0), total) >= TOLERANCE)//AntiFragile
            locationAntiFragile.add(location);
        else if (Util.perc(result.get(1), total) >= TOLERANCE)//OracleFragile
            locationOracleFragile.add(location);
        else if (Util.perc(result.get(2), total) >= TOLERANCE)//ExceptionFragile
            locationExceptionFragile.add(location);
    }

    @Deprecated
    static void writeListOnGivenFile(String pathToFile, String header, List<PerturbationLocation> locations) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(pathToFile , false);
            writer.write(header + "\n");
            for (PerturbationLocation location : locations)
                writer.write(location.getLocationIndex() + " ");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void init();

    void run(int indexOfTask, PerturbationLocation location);

    void log();

    String getTypeOfExploration();

}
