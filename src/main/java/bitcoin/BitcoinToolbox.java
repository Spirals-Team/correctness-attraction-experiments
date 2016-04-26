package bitcoin;

import experiment.Runner;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by spirals on 25/04/16.
 */
public class BitcoinToolbox {

    private static final String CMD_MINING = "$HOME/bitcoin-0.12.1/bin/bitcoin-cli -regtest generate 1";

    private static final String CMD_INIT = "src/script/bitcoin/init_bitcoin.sh";

    private static final String CMD_CLEAN = "src/script/bitcoin/clean_bitcoin.sh";

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

//            System.out.println(output);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clean() {
        launchShellCmd(CMD_CLEAN);
    }

    public static void initWallets() {
        try {
            FileWriter writer = new FileWriter("resources/bitcoin/adr_bitcoin", false);
            for (int i = 0 ; i < Runner.sizeOfEachTask ; i++)
                writer.write(BitcoinManager.getWalletAppKit(i).wallet().currentReceiveAddress() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        launchShellCmd(CMD_INIT);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public static void mine() {
        launchShellCmd(CMD_MINING);
    }

}
