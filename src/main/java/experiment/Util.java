package experiment;

import perturbation.location.PerturbationLocation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by spirals on 12/04/16.
 */
public class Util {

    public static String getStringPerc(int nb, int total) {
        double perc = perc(nb,total);
        String ret = dash(perc);
        return ret + " " + String.format("%.2f", perc);
    }

    public static double perc(int nb, int total) {
        return (double)nb / (double) total * 100;
    }

    public static String dash(double perc) {
        String dash = "";
        for (int d = 0 ; d < perc / 5 ; d++) dash += "-";
        return dash;
    }

    public static void buildNewListOfPerturbationPoint() {
        if (Runner.locations.size() <= Runner.sizeOfTopLocations)
            return;
        try {
            List<PerturbationLocation> topLocations = new ArrayList<>();
            addAntiFragileLocation(topLocations);
            if (topLocations.size() < Runner.sizeOfTopLocations)
                addNotFragileLocation(topLocations);
            Runner.locations = topLocations;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addAntiFragileLocation(List<PerturbationLocation> topLocations) throws IOException {
        String[] pathTofileToGetIndices = new String[]{
                "results/"+Runner.oracle.getPath()+"/AddOneExplorer_super_anti_fragile.txt",
                "results/"+Runner.oracle.getPath()+"/AddOneExplorer_anti_fragile.txt",
        };
        for (String path : pathTofileToGetIndices) {
            BufferedReader br = new BufferedReader(new FileReader(path));
            br.readLine(); // thrash header line
            String line = br.readLine();
            if (line == null)
                return;
            String[] indices = line.split(" ");
            for (int i = 0; i < indices.length && topLocations.size() < Runner.sizeOfTopLocations ; i++) {
                final int index = i;
                topLocations.add(Runner.locations.stream().filter(location ->
                        location.getLocationIndex() == Integer.parseInt(indices[index]) && !topLocations.contains(location)
                ).collect(Collectors.toList()).get(0));
            }
            if (topLocations.size() == Runner.sizeOfTopLocations) {
                Runner.locations = topLocations;
                return;
            }
        }
    }

    public static void addNotFragileLocation(List<PerturbationLocation> topLocations) throws IOException {
        List<Integer> blackList = new ArrayList<>();
        String[] pathTofileToGetIndices = new String[]{
                "results/"+Runner.oracle.getPath()+"/AddOneExplorer_oracle_fragile.txt",
                "results/"+Runner.oracle.getPath()+"/AddOneExplorer_exception_fragile.txt",
        };
        //Init blackList, we don't want fragile location anymore
        for (String pathToFile : pathTofileToGetIndices) {
            BufferedReader br = new BufferedReader(new FileReader(pathToFile));
            br.readLine(); // thrash header line
            String line = br.readLine();
            if (line == null)
                continue;
            for (String index :  line.split(" "))
                blackList.add(Integer.parseInt(index));
        }
        //Getting others, neither anti fragile nor fragile location
        for (PerturbationLocation location : Runner.locations) {
            if (!blackList.contains(location.getLocationIndex()) && !topLocations.contains(location))
                topLocations.add(location);
            if (topLocations.size() == Runner.sizeOfTopLocations)
                break;
        }
    }

}
