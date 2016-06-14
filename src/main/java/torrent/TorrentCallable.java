package torrent;

import com.turn.ttorrent.client.Client;
import com.turn.ttorrent.client.SharedTorrent;
import com.turn.ttorrent.common.Torrent;
import com.turn.ttorrent.tracker.TrackedTorrent;
import com.turn.ttorrent.tracker.Tracker;
import experiment.CallableImpl;

import java.io.File;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeoutException;

/**
 * Created by spirals on 21/04/16.
 */
public class TorrentCallable extends CallableImpl<String,String> {

    private TorrentManager manager;

    public TorrentCallable(String input, TorrentManager manager) {
        super(input);
        this.manager = manager;
    }

    @Override
    public String call() throws Exception {
        Client seeder = null;
        Client leecher = null;
        Tracker tracker = null;
        try {
            /* Init Tracker */
            tracker = new Tracker(new InetSocketAddress(Tracker.DEFAULT_TRACKER_PORT));
            tracker.announce(TrackedTorrent.load(new File(TorrentManager.PATH_TO_TORRENT_FILE + this.input+".torrent")));
            tracker.start();

            /* Init Seeder */
            seeder = new Client(
                    InetAddress.getLocalHost(),
                    new SharedTorrent(
                            Torrent.load(new File(TorrentManager.PATH_TO_TORRENT_FILE+ input +".torrent")),
                            new File(TorrentManager.PATH_TO_TORRENT_FILE), true));
            seeder.share(-1);

            /* Init Leecher */
            leecher = new Client(
                    InetAddress.getLocalHost(),
                    SharedTorrent.fromFile(
                            new File(TorrentManager.PATH_TO_TORRENT_FILE+ input +".torrent"),
                            new File(TorrentManager.PATH_TO_SENT_FILE)));
            Runtime.getRuntime().addShutdownHook(
                    new Thread(new Client.ClientShutdown(leecher, null)));

            leecher.download();

            leecher.waitForCompletion();

            leecher.stop();
            seeder.stop();
            tracker.stop();

        } catch (Exception e) {
            e.printStackTrace();
            if (seeder != null)
                seeder.stop();
            if (leecher != null)
                leecher.stop();
            tracker.stop();
            this.manager.reinit();
            throw new TimeoutException();
        }

//        leecher.stop();
//        seeder.stop();
//        tracker.stop();

        return input;
    }

}
