package torrent;

import experiment.Oracle;
import experiment.Runner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static torrent.TorrentManager.PATH_TO_SENT_FILE;
import static torrent.TorrentManager.PATH_TO_TORRENT_FILE;

/**
 * Created by spirals on 21/04/16.
 */
public class TorrentOracle implements Oracle<String, String> {

    @Override
    public boolean assertPerturbation(String input, String output) {
        try {
            byte[] f1 = Files.readAllBytes(Paths.get(PATH_TO_TORRENT_FILE+"/"+input+".txt"));
            byte[] f2 = Files.readAllBytes(Paths.get(PATH_TO_SENT_FILE+"/"+output+".txt"));
            return Arrays.equals(f1, f2);
        } catch (IOException e) {
            e.printStackTrace();
            ((TorrentManager) Runner.manager).reinit();
            return false;
        }
    }

}
