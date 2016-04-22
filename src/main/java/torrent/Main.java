package torrent;

import com.turn.ttorrent.bcodec.BDecoder;
import com.turn.ttorrent.common.Utils;
import experiment.Runner;
import experiment.Util;

/**
 * Created by spirals on 11/04/16.
 */
public class Main {

    public static void run() {
        System.out.println("Run Torrent...");
        Runner.setup(Utils.class, TorrentCallable.class, new TorrentManager(), String.class);
        Runner.runExplorers();
        ((TorrentManager)Runner.manager).stop();
        System.exit(0);//need to force exit because sometimes the torrent doesn't stop itself
    }

    public static void main(String[] args) {
        if (args.length >= 1)
            Util.parseArgs(args);
        run();
    }
}

