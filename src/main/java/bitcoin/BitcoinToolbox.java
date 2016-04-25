package bitcoin;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by spirals on 25/04/16.
 */
public class BitcoinToolbox {

    private static final String CMD_MINING = "/home/spirals/Téléchargements/bitcoin-0.12.0/bin/bitcoin-cli -regtest generate 1";

    private static final String CMD_INIT = "src/script/bitcoin/init_bitcoin.sh";

    private static void launchShellCmd(String command) {
        StringBuffer output = new StringBuffer();
        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initWallets() {
        launchShellCmd(CMD_INIT);
    }


    public static void mine() {
        launchShellCmd(CMD_MINING);
    }

}
