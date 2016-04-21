package torrent;

import experiment.Oracle;
import experiment.Runner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Created by spirals on 21/04/16.
 */
public class TorrentOracle implements Oracle<String, String> {

    @Override
    public boolean assertPerturbation(String input, String output) {
        try {
            byte[] f1 = Files.readAllBytes(Paths.get(input));
            byte[] f2 = Files.readAllBytes(Paths.get(output));
            return Arrays.equals(f1, f2);
        } catch (IOException e) {
            ((TorrentManager) Runner.manager).reinit();
            return false;
        }
    }

}
