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
        //Unused for bitcoin
        super.CUP = this.getClass();
        super.locations = new ArrayList<>();
        if (typePerturbed == null || typePerturbed.equals("Numerical")) {
            //Integer locations
            locations.add(ECKey.__L84);
            locations.add(ECKey.__L85);
            locations.add(ECKey.__L149);
            locations.add(DeterministicKey.__L678);
            locations.add(DeterministicKey.__L679);
            locations.add(DeterministicKey.__L680);
            locations.add(DeterministicKey.__L682);
//            locations.add(Message.__L2286);
            locations.add(Message.__L474);
            locations.add(Message.__L475);
            locations.add(Message.__L477);
            locations.add(Message.__L488);
            locations.add(Message.__L489);
            locations.add(Message.__L490);
            locations.add(Message.__L491);
            locations.add(Message.__L494);
            locations.add(Sha256Hash.__L611);
            locations.add(Sha256Hash.__L612);
            locations.add(BasicKeyChain.__L930);
        } else {
            //Boolean locations
//            locations.add(DeterministicKey.__L1419);
//            locations.add(DeterministicKey.__L1604);
//            locations.add(Message.__L2287);
//            locations.add(Message.__L2288);
//            locations.add(Message.__L2337);
//            locations.add(Message.__L2343);
//            locations.add(Message.__L2371);
//            locations.add(Message.__L2374);
//            locations.add(Message.__L2379);
//            locations.add(Message.__L2382);
        }
        this.initialize(numberOfTask, size);
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

    private HashMap<Integer, WalletAppKit> initKits() {
        if (this.kits != null) {
            for (Integer key : this.kits.keySet()) {
                this.kits.get(key).stopAsync();
            }
        }
        return new HashMap<>();
    }

    public void initWallets() {

        this.kits = initKits();

        System.out.println("Init...");

        BriefLogFormatter.initWithSilentBitcoinJ();

        BitcoinToolbox.clean();

        for (int i = 0; i < super.sizeOfTask ; i++) {
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

//        for (Integer key : this.kits.keySet()) {
//            this.kits.get(key).wallet().addEventListener(
//                    new AbstractWalletEventListener() {
//                @Override
//                public void onCoinsReceived(Wallet w, Transaction tx, Coin prevBalance, Coin newBalance) {
//                    System.out.println("RECEIVE SOME BTC");
//                    print();
//                    BitcoinToolbox.mine();
//                }
//            });
//        }


        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Integer key : this.kits.keySet()) {
            BitcoinToolbox.launchShellCmd(BitcoinToolbox.CMD_INIT, " " + this.kits.get(key).wallet().currentReceiveAddress());
        }

        BitcoinToolbox.mine();

        amountOfWalletBeforeTask = new int[super.sizeOfTask];

        int cpt = 0;

        while (cpt < super.sizeOfTask) {
            for (int i = 0; i < super.sizeOfTask; i++) {
                int amount = btcStringToBtcInt(kits.get(i).wallet().getBalance().toFriendlyString());
                if (amount != amountOfWalletBeforeTask[i]) {
//                    System.out.println("BTC sent to " + i + ", " + (super.sizeOfTask - cpt) + " to go");
                    amountOfWalletBeforeTask[i] = amount;
                    cpt ++;
//                    print();
                }
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        BitcoinToolbox.mine();
//        print();
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
