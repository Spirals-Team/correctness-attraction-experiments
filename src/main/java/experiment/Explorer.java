package experiment;

import perturbation.location.PerturbationLocation;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by spirals on 05/04/16.
 */
public interface Explorer {

    double TOLERANCE = 70.0f;

    static void addToFragilityList(Tuple result, int total, PerturbationLocation location, List<PerturbationLocation> locationExceptionFragile,
                        List<PerturbationLocation> locationAntiFragile , List<PerturbationLocation> locationOracleFragile) {
        if (result.get(0) == total)//Antifragile
            locationAntiFragile.add(location);
        else if (Runner.perc(result.get(1), total) >= TOLERANCE)//OracleFragile
            locationOracleFragile.add(location);
        else if (Runner.perc(result.get(2), total) >= TOLERANCE)//ExceptionFragile
            locationExceptionFragile.add(location);
    }

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

    void run(int indexOfTask, PerturbationLocation location);

    void log();

}
