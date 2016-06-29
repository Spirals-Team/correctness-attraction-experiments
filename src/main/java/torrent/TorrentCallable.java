package torrent;

import com.turn.ttorrent.client.Client;
import com.turn.ttorrent.client.SharedTorrent;
import com.turn.ttorrent.common.Torrent;
import com.turn.ttorrent.tracker.TrackedTorrent;
import com.turn.ttorrent.tracker.Tracker;
import experiment.CallableImpl;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.TimeoutException;

/**
 * Created by spirals on 21/04/16.
 */
public class TorrentCallable extends CallableImpl<String, Process> {

    private static final String CMD_TO_RUN;

    static {
        String classpath = "";
        for (URL url : ((URLClassLoader) (Thread.currentThread().getContextClassLoader())).getURLs())
            classpath += url + ":";
        CMD_TO_RUN = "java -cp " + classpath + " torrent.TorrentCallable ";
    }

    public TorrentCallable(String input) {
        super(input);
    }

    @Override
    public Process call() throws Exception {
        return Runtime.getRuntime().exec(CMD_TO_RUN + this.input);
    }

    public static void main(String[] args) {
        String input = args[0];
        Client seeder = null;
        Client leecher = null;
        Tracker tracker = null;
        SharedTorrent seederTorrent = null;
        SharedTorrent leecherTorrent = null;
        TrackedTorrent trackedTorrent;
        try {
            /* Init Tracker */
            trackedTorrent = TrackedTorrent.load(new File(TorrentManager.PATH_TO_TORRENT_FILE + input+".torrent"));
            tracker = new Tracker(new InetSocketAddress(Tracker.DEFAULT_TRACKER_PORT));
            tracker.announce(trackedTorrent);
            tracker.start();

            /* Init Seeder */
            seederTorrent = new SharedTorrent(
                    Torrent.load(new File(TorrentManager.PATH_TO_TORRENT_FILE+ input +".torrent")),
                    new File(TorrentManager.PATH_TO_TORRENT_FILE), true);
            seeder = new Client(
                    InetAddress.getLocalHost(), seederTorrent);
            seeder.share(-1);


            /* Init Leecher */
            leecherTorrent = SharedTorrent.fromFile(
                    new File(TorrentManager.PATH_TO_TORRENT_FILE+ input +".torrent"),
                    new File(TorrentManager.PATH_TO_SENT_FILE));
            leecher = new Client(InetAddress.getLocalHost(), leecherTorrent);
            Runtime.getRuntime().addShutdownHook(
                    new Thread(new Client.ClientShutdown(leecher, null)));

            leecher.download();
            leecher.waitForCompletion();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            if (seederTorrent != null) {
                seederTorrent.close();
                seederTorrent.stop();
            }
            if (leecherTorrent != null) {
                leecherTorrent.close();
                leecherTorrent.stop();
            }
            if (seeder != null)
                seeder.stop();
            if (leecher != null)
                leecher.stop();
            if (tracker != null)
                tracker.stop();
        }
        System.out.println(input);
        System.exit(0);
    }

}
