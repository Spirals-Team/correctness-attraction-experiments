package bitcoin;


import java.io.InputStream;

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
        Process p = null;
        int exitedValue = -1;
        try {
            while (exitedValue != 0) {
//                System.err.println(command + args);
                p = Runtime.getRuntime().exec(command + args);
                p.waitFor();
                exitedValue = p.exitValue();
                InputStream in = p.getInputStream();
//                String out = "";
//                try {
//                    while (in.available() != 0)
//                        out += (char)in.read();
//                    System.err.println(out);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                Thread.sleep(100);
            }
//            BufferedReader reader =
//                    new BufferedReader(new InputStreamReader(p.getInputStream()));
//            String line = "";
//            while ((line = reader.readLine()) != null) {
//                output.append(line + "\n");
//            }

//            System.err.println(output);

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
