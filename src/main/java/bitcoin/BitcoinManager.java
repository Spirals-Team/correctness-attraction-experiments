package bitcoin;


import experiment.CallableImpl;
import experiment.ManagerImpl;
import experiment.Oracle;
import experiment.Tuple;
import experiment.Util;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.utils.BriefLogFormatter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Created by spirals on 25/04/16.
 * <p>
 * Tuple : using Tuple3 to define tx between nodes as : T._1 = sender, T._2 = receiver and T._3 = amount.
 * <p>
 * Runner.numberOfTask is the number of tx, and super.sizeOfTask is the number of bitcoin node used in scenarios.
 */
public class BitcoinManager extends ManagerImpl<Tuple, Integer> {

    private Map<Integer, WalletAppKit> kits;

    private static final String PATH_WALLET = "./resources/bitcoin/wallets/";

    static int[] amountOfWalletBeforeTask;

    static final NetworkParameters networkParameters = RegTestParams.get();

    static final int FEE_AMOUNT = 1;

    public BitcoinManager(int numberOfTask, int size, String typePerturbed) {
        this(numberOfTask, size, 23, typePerturbed);
    }

    public BitcoinManager(int numberOfTask, int size, int seed, String typePerturbed) {
        super(seed);
        super.CUP = this.getClass();//CUP is unused for bitcoin
        super.locations = Util.getAllLocations("./bitcoinj/core/src/main/java/org/bitcoinj/", "org.bitcoinj", typePerturbed);
        this.initialize(numberOfTask, size);
        this.recover();
    }

    @Override
    public void initialize(int numberOfTask, int sizeOfTask) {
        this.outputs = new ArrayList<>();
        this.indexTasks = new ArrayList<>();
        this.tasks = new ArrayList<>();
        this.sizeOfTask = sizeOfTask;
        IntStream.range(0, numberOfTask).forEach(index -> {
            this.indexTasks.add(index);
            this.tasks.add(this.generateOneTask());
        });
    }

    @Override
    public void stop() {
        for (Integer key : this.kits.keySet()) {
            this.kits.get(key).stopAsync();
        }
    }

    private HashMap<Integer, WalletAppKit> initKits() {
        if (this.kits != null)
            stop();
        return new HashMap<>();
    }

    @Override
    public void recover() {

        long timeInit = System.currentTimeMillis();

        this.kits = initKits();

        System.out.println("Init...");

        BriefLogFormatter.initWithSilentBitcoinJ();

        BitcoinToolbox.clean();

        for (int i = 0; i < super.sizeOfTask; i++) {
            this.kits.put(i, new WalletAppKit(networkParameters, new File(PATH_WALLET + "wallet_" + i), "wallet_" + i));
        }

        for (Integer key : kits.keySet()) {
            this.kits.get(key).connectToLocalHost();
        }

        for (Integer key : this.kits.keySet()) {
            this.kits.get(key).startAsync();
        }

        for (Integer key : this.kits.keySet()) {
            this.kits.get(key).awaitRunning();
        }

        for (Integer key : this.kits.keySet()) {
            BitcoinToolbox.launchShellCmd(BitcoinToolbox.CMD_INIT, " " + this.kits.get(key).wallet().currentReceiveAddress());
        }

        BitcoinToolbox.mine();

        amountOfWalletBeforeTask = new int[super.sizeOfTask];

        int retry = 5;
        int cpt = 0;
        int attempt = 0;

        while (cpt < super.sizeOfTask) {
            if (attempt > 100) {
                System.err.println("Too many attempt... retry");
                for (Integer key : this.kits.keySet()) {
                    BitcoinToolbox.launchShellCmd(BitcoinToolbox.CMD_INIT, " " + this.kits.get(key).wallet().currentReceiveAddress());
                }
                BitcoinToolbox.mine();
                amountOfWalletBeforeTask = new int[super.sizeOfTask];
                attempt = 0;
                if (retry < 0) {
                    System.err.println("Recall the whole recovery procedure....");
                    recover();
                    return;
                } else
                    retry--;

            }
            for (int i = 0; i < super.sizeOfTask; i++) {
                int amount = btcStringToBtcInt(kits.get(i).wallet().getBalance().toFriendlyString());
                if (amount != amountOfWalletBeforeTask[i]) {
                    amountOfWalletBeforeTask[i] = amount;
                    cpt++;
                }
            }
            attempt++;
        }

        BitcoinToolbox.mine();

        System.out.println(System.currentTimeMillis() - timeInit);
    }

    public static int btcStringToBtcInt(String amount) {
        return (int) Float.parseFloat(amount.substring(0, amount.length() - 4));
    }

    public WalletAppKit getWalletAppKit(int index) {
        return kits.get(index);
    }

    @Override
    protected Tuple generateOneTask() {
        Tuple task = new Tuple(3);
        task.set(0, super.randomForGenTask.nextInt(super.sizeOfTask));
        int indexReceiver = super.randomForGenTask.nextInt(super.sizeOfTask);
        while (indexReceiver == task.get(0))
            indexReceiver = super.randomForGenTask.nextInt(super.sizeOfTask);
        task.set(1, indexReceiver);
        task.set(2, super.randomForGenTask.nextInt(4) + 1);
        return task;
    }

    @Override
    public Tuple getTask(int index) {
        if (index >= super.tasks.size())
            return super.getTask(index);
        Tuple clone = new Tuple(3);
        return clone.add(super.tasks.get(index));
    }

    @Override
    public CallableImpl<Tuple, Integer> getCallable(Tuple input) {
        return new BitcoinCallable(input, this);
    }

    @Override
    public Oracle<Tuple, Integer> getOracle() {
        return new BitcoinOracle(this);
    }

    @Override
    public String getName() {
        return "bitcoin";
    }

    @Override
    public String getHeader() {
        return super.indexTasks.size() + " random tx between " + super.sizeOfTask + " bitcoin nodes\n" +
                "Random integer generated with " + super.seedForGenTask + " as seed\n" +
                super.locations.size() + " perturbations points\n";
    }

    public void print() {
        for (int key : kits.keySet()) {
            System.out.println(key + " " + kits.get(key).wallet().toString(false, false, false, null));
        }
    }

}
