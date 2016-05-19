package bitcoin;

import experiment.Oracle;
import experiment.OracleManagerImpl;
import experiment.Runner;
import experiment.Tuple;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.utils.BriefLogFormatter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by spirals on 25/04/16.
 *
 * Tuple : using Tuple3 to define tx between nodes as : T._1 = sender, T._2 = receiver and T._3 = amount.
 *
 * Runner.numberOfTask is the number of tx, and Runner.sizeOfEachTask is the number of bitcoin node used in scenarios.
 *
 */
public class BitcoinManager extends OracleManagerImpl<Tuple> {

    private static Map<Integer, WalletAppKit> kits = new HashMap<>();

    private static final String PATH_WALLET = "./resources/bitcoin/wallets/";

    static int[] amountOfWalletBeforeTask;

    static final NetworkParameters networkParameters = RegTestParams.get();

    static final int FEE_AMOUNT = 1;

    public BitcoinManager(int numberOfTask, int seed) {
        super(numberOfTask, seed);
        super.header = super.numberOfTask + " random tx between " + Runner.sizeOfEachTask + " bitcoin nodes\nRandom integer generated with " + super.seedForGenTask + " as seed\n";
        super.path = "bitcoin";
        initWallets();
    }

    public BitcoinManager(int seed) {
        this(Runner.numberOfTask, seed);
    }

    public BitcoinManager() {
        this(Runner.numberOfTask, 23);
    }

    public void initWallets() {

        System.out.println("Init...");

        BriefLogFormatter.initWithSilentBitcoinJ();

        BitcoinToolbox.clean();

        for (int i = 0 ; i < Runner.sizeOfEachTask ; i++) {
            kits.put(i, new WalletAppKit(networkParameters, new File(PATH_WALLET + "wallet_" + i), "wallet_" + i));
        }

        for (Integer key : kits.keySet()) {
            kits.get(key).connectToLocalHost();
        }

        for (Integer key : kits.keySet()) {
            kits.get(key).startAsync();
        }

        for (Integer key : kits.keySet()) {
            kits.get(key).awaitRunning();
        }

        BitcoinToolbox.initWallets();

        BitcoinToolbox.mine();

        amountOfWalletBeforeTask = new int[Runner.sizeOfEachTask];

        for (int i = 0; i < Runner.sizeOfEachTask; i++) {
            amountOfWalletBeforeTask[i] = btcStringToBtcInt(kits.get(i).wallet().getBalance().toFriendlyString());
        }

        print();

    }

    public void stop() {
        for (int key : kits.keySet())
            kits.get(key).stopAsync();
    }

    public static int btcStringToBtcInt(String amount) {
        return (int) Float.parseFloat(amount.substring(0, amount.length() - 4));
    }

    public static WalletAppKit getWalletAppKit(int index) {
        return kits.get(index);
    }

    @Override
    protected Tuple generateOneTask() {
        Tuple task = new Tuple(3);
        task.set(0, super.randomForGenTask.nextInt(Runner.sizeOfEachTask));
        int indexReceiver = super.randomForGenTask.nextInt(Runner.sizeOfEachTask);
        while (indexReceiver == task.get(0))
            indexReceiver = super.randomForGenTask.nextInt(Runner.sizeOfEachTask);
        task.set(1, indexReceiver);
        task.set(2, super.randomForGenTask.nextInt(4) + 1);
        return task;
    }

    @Override
    public Tuple get(int index) {
        Tuple retour = new Tuple(3);
        return retour.add(super.scenario.get(index));
    }

    @Override
    public Oracle<Tuple, ?> getOracle() {
        return new BitcoinOracle();
    }


    public void print() {
        for (int key : kits.keySet()) {
            System.out.println(key + " " + kits.get(key).wallet().toString(false, false, false, null));
        }
    }

}
