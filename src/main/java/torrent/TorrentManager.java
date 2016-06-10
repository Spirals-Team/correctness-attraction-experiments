package torrent;

import com.turn.ttorrent.bcodec.BDecoder;
import com.turn.ttorrent.common.Torrent;
import com.turn.ttorrent.tracker.TrackedTorrent;
import com.turn.ttorrent.tracker.Tracker;
import experiment.CallableImpl;
import experiment.ManagerImpl;
import experiment.Oracle;
import perturbation.location.PerturbationLocation;
import perturbation.location.PerturbationLocationImpl;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by spirals on 21/04/16.
 */
public class TorrentManager extends ManagerImpl<String, String> {

    public static final String PATH_TO_TORRENT_FILE = "resources/input_torrent/";

    public static final String PATH_TO_SENT_FILE = "resources/output_torrent/";

    private final String URL_ANNOUCE = "http://0.0.0.0:6969/announce";

    private final String PREFIX_FILE = "test_file_";

    private final String CREATOR = "spirals";

    private Tracker tracker;

    private static FilenameFilter filter = (dir, name) -> name.endsWith(".torrent");

    public TorrentManager(int numberOfTask, int size) {
        this(numberOfTask, size, 23);
    }

    public TorrentManager(int numberOfTask, int size, int seed) {
        super(seed);
        super.CUP = BDecoder.class;
        super.initialize(numberOfTask, size);
        super.locations = this.buildAllLocation();
        this.initTracker();
    }

    private List<PerturbationLocation> buildAllLocation() {
        try {
            Search.getAllClasses("ttorrent/core/src/main/java/com/turn/ttorrent/", "com.turn.ttorrent");
        } catch (ClassNotFoundException e) {
            System.exit(-1);
        }
        final List<PerturbationLocation> locations = new ArrayList<>();
        Search.classes.stream().forEach(clazz -> {
                    PerturbationLocationImpl.getLocationFromClass(clazz).forEach(location -> {
                        if (!locations.contains(location) && location.getType().equals("Numerical"))
                            locations.add(location);
                    });
                }
        );
        return locations;
    }

    public void initTracker() {
        try {
            this.tracker = new Tracker(new InetSocketAddress(Tracker.DEFAULT_TRACKER_PORT));
            File parent = new File(PATH_TO_TORRENT_FILE);
            for (File f : parent.listFiles(filter))
                this.tracker.announce(TrackedTorrent.load(f));
            this.tracker.start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void reinit() {
        this.stop();
        cleanDirectory();
        this.initTracker();
    }

    public static void cleanDirectory() {
        try {
            File dir = new File(PATH_TO_SENT_FILE);
            if (dir.exists() && dir.isDirectory()) {
                for (File f : dir.listFiles()) {
                    Files.delete(Paths.get(f.toURI()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "torrent";
    }

    @Override
    protected String generateOneTask() {
        if (super.tasks.isEmpty())
            createDirectories();
        String pathOfTheNewTask = PREFIX_FILE + (super.tasks.size());
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
            for (int i = 0; i < super.sizeOfTask; i++) {
                writer.write((char) randomForGenTask.nextInt(256));
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return task;
    }

    public void stop() {
        if (this.tracker != null)
            this.tracker.stop();
        this.tracker = null;
    }

    private void createTorrent(String pathOfTheNewTask, File task) {
        try {
            Torrent torrent = Torrent.create(task, new URI(URL_ANNOUCE), CREATOR);
            torrent.save(new FileOutputStream(PATH_TO_TORRENT_FILE + pathOfTheNewTask + ".torrent"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getTask(int index) {
        if (index >= super.tasks.size()) {
            this.tasks.add(this.generateOneTask());
            this.indexTasks.add(this.tasks.size() - 1);
            try {
                File file = new File(PATH_TO_TORRENT_FILE+"/"+this.tasks.get(index) + ".torrent");
                this.tracker.announce(TrackedTorrent.load(file));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String input = new String(super.tasks.get(index));
        return input;
    }

    @Override
    public Oracle<String, String> getOracle() {
        return new TorrentOracle(this);
    }

    @Override
    public CallableImpl<String, String> getCallable(String input) {
        return new TorrentCallable(input, this);
    }

    @Override
    public String getHeader() {
        return super.indexTasks.size() + " files of " + super.sizeOfTask + " chararacters\n" +
                "Random characters generated with " + super.seedForGenTask + " as seed\n" +
                super.locations.size() + " perturbations points\n";
    }

}
