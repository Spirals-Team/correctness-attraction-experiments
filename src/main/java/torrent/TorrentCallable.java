package torrent;

import com.turn.ttorrent.client.Client;
import com.turn.ttorrent.client.SharedTorrent;
import com.turn.ttorrent.common.Torrent;
import com.turn.ttorrent.tracker.TrackedTorrent;
import com.turn.ttorrent.tracker.Tracker;
import experiment.CallableImpl;
import experiment.Main;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeoutException;

/**
 * Created by spirals on 21/04/16.
 */
public class TorrentCallable extends CallableImpl<String, String> {

    static  Tracker tracker = null;
    static {
        /* Init Tracker */
        try {
            tracker = new Tracker(new InetSocketAddress(Tracker.DEFAULT_TRACKER_PORT));
        } catch (IOException e) {
            e.printStackTrace();
        }

        tracker.start();
    }
    /**
     * The Manager is need in case of error during the call, we have to be able to reinit the system
     */
    private TorrentManager manager;

    public TorrentCallable(String input, TorrentManager manager) {
        super(input);
        this.manager = manager;
    }

    @Override
    public String call() throws Exception {
        System.out.println("new call " + input);
        Client seeder = null;
        Client leecher = null;

        SharedTorrent sharedTorrentSeeder = null;
        SharedTorrent sharedTorrentLeecher = null;
        TrackedTorrent torrent = null;
        try {
            torrent = TrackedTorrent.load(new File(TorrentManager.PATH_TO_TORRENT_FILE + this.input + ".torrent"));
            tracker.announce(torrent);
            /* Init Seeder */
            sharedTorrentSeeder = new SharedTorrent(torrent,
                    new File(TorrentManager.PATH_TO_TORRENT_FILE), true);
            seeder = new Client(InetAddress.getLocalHost(), sharedTorrentSeeder);
            //Runtime.getRuntime().addShutdownHook(new Thread(new Client.ClientShutdown(seeder, null)));
            seeder.share(1);

            /* Init Leecher */
            sharedTorrentLeecher = SharedTorrent.fromFile(
                    new File(TorrentManager.PATH_TO_TORRENT_FILE + input + ".torrent"),
                    new File(TorrentManager.PATH_TO_SENT_FILE));
            leecher = new Client(InetAddress.getLocalHost(), sharedTorrentLeecher);
            ///Runtime.getRuntime().addShutdownHook(new Thread(new Client.ClientShutdown(leecher, null)));

            //leecher.download();
            leecher.addObserver(new Observer() {
                @Override
                public void update(Observable observable, Object data) {
                    Client client = (Client) observable;
                    float progress = client.getTorrent().getCompletion();
                    System.out.println(progress);
                    // Do something with progress.
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            this.manager.recover();
            throw new TimeoutException();
        } finally {
            leecher.waitForCompletion();
            leecher.getTorrent().finish();
            sharedTorrentSeeder.finish();
            sharedTorrentLeecher.finish();
            if (sharedTorrentSeeder != null) {
                sharedTorrentSeeder.stop();
                sharedTorrentSeeder.close();
            }
            if (sharedTorrentLeecher != null) {
                sharedTorrentLeecher.stop();
                sharedTorrentLeecher.close();
            }
            if (seeder != null)
                seeder.stop(true);
            if (leecher != null)
                leecher.stop(true);
            if (tracker != null) {
                tracker.remove(torrent);
                tracker.stop();
            }
        }
        return input;
    }

}
