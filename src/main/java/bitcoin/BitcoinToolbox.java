package bitcoin;

import experiment.Main;

import javax.print.DocFlavor;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by spirals on 25/04/16.
 */
public class BitcoinToolbox {

    static final String CMD_INIT = "src/script/bitcoin/init_bitcoin.sh";

    static final String CMD_CLEAN = "src/script/bitcoin/clean_bitcoin.sh";

    private static final String CMD = "./bitcoin-0.12.1/bin/bitcoin-cli -regtest";

    static final String CMD_MINING = CMD + " generate 1";

    static void launchShellCmd(String command, String args) {
        StringBuffer output = new StringBuffer();
        Process p;
        try {
            System.out.println(command+args);
            p = Runtime.getRuntime().exec(command+args);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

//            System.out.println(output);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    synchronized static void clean() {
        launchShellCmd(CMD_CLEAN, "");
    }

    synchronized static void mine() {
        launchShellCmd(CMD_MINING, "");
    }

}
