package torrent;

import com.turn.ttorrent.client.Client;
import experiment.AddOneExplorerImpl;
import experiment.Runner;
import experiment.Util;

/**
 * Created by spirals on 11/04/16.
 */
public class Main {

    public static void run() {
        System.out.println("Run Torrent...");
        Runner.setup(Client.class, TorrentCallable.class, new TorrentManager(), String.class);
        Runner.runExplorers();
    }

    public static void main(String[] args) {
        if (args.length >= 1)
            Util.parseArgs(args);
        run();
    }
}

