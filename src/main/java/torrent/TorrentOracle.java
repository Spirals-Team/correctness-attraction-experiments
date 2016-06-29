package torrent;

import experiment.Oracle;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static torrent.TorrentManager.PATH_TO_SENT_FILE;
import static torrent.TorrentManager.PATH_TO_TORRENT_FILE;

/**
 * Created by spirals on 21/04/16.
 */
public class TorrentOracle implements Oracle<String, Process> {

    private TorrentManager manager;

    public TorrentOracle(TorrentManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean assertPerturbation(String input, Process output) {
        try {
            output.waitFor();
            if (output.exitValue() == 1) {
                this.manager.recover();
                return false;
            }

            InputStream out = output.getInputStream();
            String outputPath = "";
            while (0 != out.available())
                outputPath += (char)out.read();
            outputPath = outputPath.substring(0, outputPath.length() - 1);

            byte[] f1 = Files.readAllBytes(Paths.get(PATH_TO_TORRENT_FILE+"/"+input+".txt"));
            byte[] f2 = Files.readAllBytes(Paths.get(PATH_TO_SENT_FILE+"/"+outputPath+".txt"));
            boolean assertion = Arrays.equals(f1, f2);
            Files.delete(Paths.get(PATH_TO_SENT_FILE+"/"+outputPath+".txt"));//After assertion, we remove the downloaded file in order to redo it.
            return assertion;
        } catch (Exception e) {
            e.printStackTrace();
            this.manager.recover();
            return false;
        }
    }

}
