package experiment.explorer;

import experiment.Logger;
import experiment.Runner;
import experiment.Tuple;
import experiment.Util;
import experiment.exploration.Exploration;
import perturbation.location.PerturbationLocation;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by bdanglot on 03/05/16.
 */
public class HeatMapExplorer extends RandomExplorer {

    public HeatMapExplorer(Exploration exploration, float... randomRates) {
        super(exploration, randomRates);
    }

    @Override
    public void log() {

        Tuple[][][][] results = Logger.getResults();

        int numberOfPerturbor = exploration.getPerturbators().size();
        String[] perturbatorsName = exploration.getPerturbatorsName();
        String campaignName = exploration.getName();

        String pathToOutPutFile = "results/" + Runner.manager.getPath() + "/" +
                campaignName + "_HeatMap.txt";

        try {
         /* Sum Arrays */
            FileWriter writer = new FileWriter(pathToOutPutFile, false);
            writer.write("contains the data for the random rates analysis graph with " + exploration.getColumnName() + " as perturbator.\n");
            writer.write(exploration.getHeader() + Runner.manager.getHeader());
            String format = "%-10s %-" + exploration.getColumnName().length() + "s %-10s %-10s %-10s %-10s %-10s %-14s %-27s";
            writer.write(String.format(format,
                    "RandomRate", exploration.getColumnName(), "IndexLoc",
                    "#Success", "#Failure", "#Exception",
                    "#Call", "#Perturbation",
                    "%Success") + "\n");

            for (PerturbationLocation location : Runner.locations) {
                Tuple resultForLocation = new Tuple(3);
                for (int indexRandomRates = 0; indexRandomRates < randomRates.length; indexRandomRates++) {
                    Tuple result = new Tuple(5);
                    for (int indexPerturbator = 0; indexPerturbator < numberOfPerturbor; indexPerturbator++) {
                        for (int indexTask = 0; indexTask < Runner.numberOfTask; indexTask++) {
                            result = result.add(results[Runner.locations.indexOf(location)][indexTask][indexPerturbator][indexRandomRates]);

                            writer.write(String.format(format,
                                    randomRates[indexRandomRates], perturbatorsName[indexPerturbator], location.getLocationIndex(),
                                    result.get(0), result.get(1), result.get(2),
                                    result.get(3), result.get(4),
                                    result.get(4) == 0 ? "NaN" : Util.getStringPerc(result.get(0), result.total(3))) + "\n");

                        }
                        resultForLocation = resultForLocation.add(result);
                    }
                }
            }
            writer.close();
        } catch (IOException e) {}
    }
}
