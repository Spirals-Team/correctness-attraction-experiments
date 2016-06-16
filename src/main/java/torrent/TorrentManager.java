package torrent;

import com.turn.ttorrent.bcodec.BDecoder;
import com.turn.ttorrent.common.Torrent;
import com.turn.ttorrent.tracker.TrackedTorrent;
import com.turn.ttorrent.tracker.Tracker;
import experiment.*;
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
import java.util.concurrent.*;

/**
 * Created by spirals on 21/04/16.
 */
public class TorrentManager extends ManagerImpl<String, String> {

    static final String PATH_TO_TORRENT_FILE = "resources/input_torrent/";

    static final String PATH_TO_SENT_FILE = "resources/output_torrent/";

    final String URL_ANNOUCE = "http://0.0.0.0:6969/announce";

    final String PREFIX_FILE = "test_file_";

    final String CREATOR = "spirals";


    public TorrentManager(int numberOfTask, int size) {
        this(numberOfTask, size, 23);
    }

    public TorrentManager(int numberOfTask, int size, int seed) {
        super(seed);
        super.CUP = BDecoder.class;
        super.initialize(numberOfTask, size);
        super.locations = Util.getAllLocations("ttorrent/core/src/main/java/com/turn/ttorrent/", "com.turn.ttorrent", "Numerical");//TODO Change type in function of the exploration
    }

    @Override
    public void recover() {
        try {
            File dir = new File(PATH_TO_SENT_FILE);
            if (dir.exists() && dir.isDirectory()) {
                for (File f : dir.listFiles((dire, name) -> name.endsWith(".part"))) {
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

    private void createTorrent(String pathOfTheNewTask, File task) {
        try {
            FileOutputStream stream = null;
            stream = new FileOutputStream(PATH_TO_TORRENT_FILE + pathOfTheNewTask + ".torrent");
            Torrent torrent = Torrent.create(task, new URI(URL_ANNOUCE), CREATOR);
            torrent.save(stream);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getTask(int index) {
        if (index >= super.tasks.size()) {
            this.tasks.add(this.generateOneTask());
            this.indexTasks.add(this.tasks.size() - 1);
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

    public static void main(String[] args) {
        TorrentManager manager = new TorrentManager(1, 10);
        for (int i = 0; i < 5; i++) {
            try {
                ExecutorService executor = Executors.newSingleThreadExecutor();
                Callable instanceRunner = manager.getCallable(manager.getTask(i));
                Future future = executor.submit(instanceRunner);
                Object output = (future.get(1000, TimeUnit.SECONDS));
                manager.getOracle().assertPerturbation(manager.getTask(i), (String) output);
                executor.shutdownNow();
                manager.getCallable(manager.getTask(i)).call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
