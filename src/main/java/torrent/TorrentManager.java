package torrent;

import com.turn.ttorrent.bcodec.BDecoder;
import com.turn.ttorrent.common.Torrent;
import experiment.CallableImpl;
import experiment.ManagerImpl;
import experiment.Oracle;
import experiment.Util;
import perturbation.location.PerturbationLocationImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by spirals on 21/04/16.
 */
public class TorrentManager extends ManagerImpl<String, String> {

	private String currentTask;

	private int currentIndex;

	static final String PATH_TO_TORRENT_FILE = "resources/input_torrent/";

	static final String PATH_TO_SENT_FILE = "resources/output_torrent/";

	final String URL_ANNOUCE = "http://0.0.0.0:6969/announce";

	final String PREFIX_FILE = "test_file_";

	final String CREATOR = "spirals";


	public TorrentManager(int size, String perturbedType) {
		this(size, 23, perturbedType);
	}

	public TorrentManager(int size, int seed, String perturbedType) {
		super(seed);
		super.CUP = BDecoder.class;
		this.initialize(-1, size);
		super.locations = Util.getAllLocations("ttorrent/core/src/main/java/com/turn/ttorrent/", "com.turn.ttorrent", perturbedType);
	}

	@Override
	public void initialize(int numberOfTask, int sizeOfTask) {
		this.locations = PerturbationLocationImpl.getLocationFromClass(CUP);
		this.outputs = new ArrayList<>();
		this.indexTasks = new ArrayList<>();
		this.tasks = new ArrayList<>();
		this.sizeOfTask = sizeOfTask;
		this.createDirectories();
		this.currentIndex = 0;
		this.currentTask = this.generateOneTask();
	}

	@Override
	public void recover() {
		try {
			File dir = new File(PATH_TO_SENT_FILE);
			if (dir.exists() && dir.isDirectory())
				for (File f : dir.listFiles())
					Files.delete(Paths.get(f.toURI()));
			dir = new File(PATH_TO_TORRENT_FILE);
			if (dir.exists() && dir.isDirectory())
				for (File f : dir.listFiles()) {
					if (!f.getName().endsWith(this.currentIndex + ".txt") && !f.getName().endsWith(this.currentIndex + ".torrent"))
						Files.delete(Paths.get(f.toURI()));
				}
		} catch (IOException e) {
//			e.printStackTrace();
		}
	}

	@Override
	public String getName() {
		return "torrent";
	}

	@Override
	protected String generateOneTask() {
		//Deleting old task in order to not stack a lof of file.
		try {
			Files.delete(Paths.get(PATH_TO_TORRENT_FILE + "/" + PREFIX_FILE + (currentIndex - 1) + ".txt"));
			Files.delete(Paths.get(PATH_TO_TORRENT_FILE + "/" + PREFIX_FILE + (currentIndex - 1) + ".torrent"));
		} catch (IOException ignored) {

		}
		String pathOfTheNewTask = PREFIX_FILE + (currentIndex);
		/* Create the file */
		File task = createFile(PATH_TO_TORRENT_FILE + pathOfTheNewTask);
		/* Create the torrent of the file */
		createTorrent(pathOfTheNewTask, task);
        /* return the path to the couple file/torrent */
		return pathOfTheNewTask;
	}

	private void createDirectories() {
		File directory = new File(PATH_TO_SENT_FILE);
		if (directory.exists())
			directory.delete();
		directory.mkdir();
		directory = new File(PATH_TO_TORRENT_FILE);
		if (directory.exists())
			directory.delete();
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
		if (index == this.currentIndex) {
			return this.currentTask;
		} else {
			this.currentIndex = index;
			this.currentTask = this.generateOneTask();
			return this.currentTask;
		}
	}

	@Override
	public Oracle<String, String> getOracle() {
		return new TorrentOracle(this);
	}

	@Override
	public CallableImpl<String, String> getCallable(String input) {
		return new TorrentCallable(input);
	}

	@Override
	public String getHeader() {
		return this.currentIndex + " files of " + super.sizeOfTask + " chararacters\n" +
				"Random characters generated with " + super.seedForGenTask + " as seed\n" +
				super.locations.size() + " perturbations points\n";
	}

	@Override
	public void stop() {
		super.stop();
		Thread.getAllStackTraces().keySet().stream().forEach(Thread::interrupt);
	}

	public static void main(String[] args) throws Exception {
		TorrentManager manager = new TorrentManager(100, "Numerical");
		long time = System.currentTimeMillis();
		for (int i = 0; i < 20; i++) {
			manager.getCallable(manager.getTask(i)).call();
			manager.recover();
		}
		System.out.println(System.currentTimeMillis() - time);
		manager.stop();
	}
}
