package torrent;

import com.turn.ttorrent.client.Client;
import com.turn.ttorrent.client.SharedTorrent;
import com.turn.ttorrent.common.Torrent;
import experiment.CallableImpl;

import java.io.File;
import java.net.InetAddress;
import java.util.concurrent.TimeoutException;

/**
 * Created by spirals on 21/04/16.
 */
public class TorrentCallable extends CallableImpl<String,String> {

    public TorrentCallable(String input) {
        super(input);
    }

    @Override
    public String call() throws Exception {
        Client seeder = null;
        Client leecher = null;
        try {
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

        } catch (Exception e) {
            assert seeder != null;
            seeder.stop();
            assert leecher != null;
            leecher.stop();
            ((TorrentManager) Runner.manager).reinit();
            throw new TimeoutException();
        }

        leecher.stop();
        seeder.stop();

        return input;
    }

}
