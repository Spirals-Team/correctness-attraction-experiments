package torrent;

import com.turn.ttorrent.common.Torrent;
import com.turn.ttorrent.tracker.TrackedTorrent;
import com.turn.ttorrent.tracker.Tracker;
import experiment.Oracle;
import experiment.OracleManager;
import experiment.Runner;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

/**
 * Created by spirals on 21/04/16.
 */
public class TorrentManager extends OracleManager<String> {

    public static final String PATH_TO_TORRENT_FILE = "resources/input_torrent/";

    public static final String PATH_TO_SENT_FILE = "resources/output_torrent/";

    private final String URL_ANNOUCE = "http://0.0.0.0:6969/announce";

    private final String PREFIX_FILE = "test_file_";

    private final String CREATOR = "spirals";

    private Tracker tracker;

    private static FilenameFilter filter = (dir, name) -> name.endsWith(".torrent");

    public TorrentManager(int seed) {
        super(seed);
        super.header = Runner.numberOfTask + " files of " + Runner.sizeOfEachTask + " chararacters\n";
        super.header += "Random characters generated with " + seedForGenTask + " as seed\n";

        super.path = "torrent";

        initTracker();
    }

    public TorrentManager() {
        super();
        super.header = Runner.numberOfTask + " files of " + Runner.sizeOfEachTask + " chararacters\n";
        super.header += "Random characters generated with " + seedForGenTask + " as seed\n";

        super.path = "torrent";

        initTracker();
    }

    public void initTracker() {
        try {
            tracker = new Tracker(new InetSocketAddress(Tracker.DEFAULT_TRACKER_PORT));
            File parent = new File(PATH_TO_TORRENT_FILE);
            for (File f : parent.listFiles(filter))
                tracker.announce(TrackedTorrent.load(f));
            tracker.start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void reinit() {
        cleanDirectory();
        stop();
        initTracker();
    }

    public static void cleanDirectory() {
        try {
            File dire = new File(PATH_TO_SENT_FILE);
            for (File f : dire.listFiles()) {
                Files.delete(Paths.get(f.toURI()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String generateOneTask() {
        if (super.scenario.isEmpty())
            createDirectories();
        String pathOfTheNewTask = PREFIX_FILE + (super.scenario.size());
        /* Create the file */
        File task = createFile(PATH_TO_TORRENT_FILE + pathOfTheNewTask);
        /* Create the torrent of the file */
        createTorrent(pathOfTheNewTask, task);
        /* return the path to the couple file/torrent */
        return pathOfTheNewTask;
    }

    private void createDirectories() {
        File directory = new File(PATH_TO_SENT_FILE);
        directory.mkdir();
        directory = new File(PATH_TO_TORRENT_FILE);
        directory.mkdir();
    }

    private File createFile(String pathOfTheNewTask) {
        File task = new File(pathOfTheNewTask + ".txt");
        try {
            task.createNewFile();
            FileWriter writer = new FileWriter(pathOfTheNewTask + ".txt");
            for (int i = 0; i < Runner.sizeOfEachTask; i++) {
                writer.write((char) randomForGenTask.nextInt(256));
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return task;
    }

    public void stop() {
        this.tracker.stop();
    }

    private void createTorrent(String pathOfTheNewTask, File task) {
        try {
            Torrent torrent = Torrent.create(task, new URI(URL_ANNOUCE), CREATOR);
            torrent.save(new FileOutputStream(PATH_TO_TORRENT_FILE + pathOfTheNewTask + ".torrent"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String get(int index) {
        String input = new String(scenario.get(index));
        return input;
    }

    @Override
    public Oracle<String, ?> getOracle() {
        return new TorrentOracle();
    }

}
