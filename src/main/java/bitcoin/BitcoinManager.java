package bitcoin;

import experiment.CallableImpl;
import experiment.ManagerImpl;
import experiment.Oracle;
import experiment.Tuple;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Message;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.utils.BriefLogFormatter;
import org.bitcoinj.wallet.BasicKeyChain;
import org.junit.runner.Runner;
import perturbation.location.PerturbationLocationImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Created by spirals on 25/04/16.
 *
 * Tuple : using Tuple3 to define tx between nodes as : T._1 = sender, T._2 = receiver and T._3 = amount.
 *
 * Runner.numberOfTask is the number of tx, and super.sizeOfTask is the number of bitcoin node used in scenarios.
 *
 */
public class BitcoinManager extends ManagerImpl<Tuple, Integer> {

    private static Map<Integer, WalletAppKit> kits = new HashMap<>();

    private static final String PATH_WALLET = "./resources/bitcoin/wallets/";

    static int[] amountOfWalletBeforeTask;

    static final NetworkParameters networkParameters = RegTestParams.get();

    static final int FEE_AMOUNT = 1;

    public BitcoinManager(int numberOfTask, int size, String typePerturbed) {
        this(numberOfTask, size, 23, typePerturbed);
    }

    public BitcoinManager(int numberOfTask, int size, int seed, String typePerturbed) {
        super(seed);
        //Unused for bitcoin
        super.CUP = null;
        if (typePerturbed == null || typePerturbed.equals("Numerical")) {
            //Integer locations
            locations.add(ECKey.__L1588);
            locations.add(ECKey.__L1589);
            locations.add(DeterministicKey.__L6751);
            locations.add(DeterministicKey.__L6752);
            locations.add(DeterministicKey.__L6753);
            locations.add(DeterministicKey.__L6755);
            locations.add(Message.__L2286);
            locations.add(Message.__L2295);
            locations.add(Message.__L2296);
            locations.add(Message.__L2298);
            locations.add(Message.__L2309);
            locations.add(Message.__L2310);
            locations.add(Message.__L2311);
            locations.add(Message.__L2312);
            locations.add(Message.__L2315);
            locations.add(ECKey.__L1648);
            locations.add(ECKey.__L1648);
            locations.add(Sha256Hash.__L3677);
            locations.add(Sha256Hash.__L3678);
            locations.add(BasicKeyChain.__L12816);
        } else {
            //Boolean locations
            locations.add(DeterministicKey.__L6715);
            locations.add(DeterministicKey.__L1604);
            locations.add(Message.__L2287);
            locations.add(Message.__L2288);
            locations.add(Message.__L2337);
            locations.add(Message.__L2343);
            locations.add(Message.__L2371);
            locations.add(Message.__L2374);
            locations.add(Message.__L2379);
            locations.add(Message.__L2382);
        }
        super.initialize(numberOfTask, size);
        initWallets();
    }

    @Override
    public void initialize(int numberOfTask, int sizeOfTask) {
//        this.locations = PerturbationLocationImpl.getLocationFromClass(CUP);
        this.outputs = new ArrayList<>();
        this.indexTasks = new ArrayList<>();
        this.tasks = new ArrayList<>();
        this.sizeOfTask = sizeOfTask;
        IntStream.range(0, numberOfTask).forEach(index -> {
            this.indexTasks.add(index);
            this.tasks.add(this.generateOneTask());
        });
    }

    public void initWallets() {

        System.out.println("Init...");

        BriefLogFormatter.initWithSilentBitcoinJ();

        BitcoinToolbox.clean();

        for (int i = 0 ; i < super.sizeOfTask ; i++) {
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

        amountOfWalletBeforeTask = new int[super.sizeOfTask];

        for (int i = 0; i < super.sizeOfTask; i++) {
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
        Tuple retour = new Tuple(3);
        return retour.add(super.tasks.get(index));
    }

    @Override
    public CallableImpl<Tuple, Integer> getCallable(Tuple input) {
        return new BitcoinCallable(input, this);
    }

    @Override
    public Oracle<Tuple, Integer> getOracle() {
        return new BitcoinOracle();
    }

    @Override
    public String getName() {
        return "bitcoin";
    }

    @Override
    public String getHeader() {
        return super.indexTasks.size() + " random tx between " + super.sizeOfTask + " bitcoin nodes\n"+
                "Random integer generated with " + super.seedForGenTask + " as seed\n" +
                super.locations.size() + " perturbations points\n";
            }

    public void print() {
        for (int key : kits.keySet()) {
            System.out.println(key + " " + kits.get(key).wallet().toString(false, false, false, null));
        }
    }

}
