package experiment;

import bitcoin.BitcoinCallable;
import bitcoin.BitcoinManager;
import classifier.BayesCallable;
import classifier.BayesManager;
import com.turn.ttorrent.bcodec.BDecoder;
import experiment.exploration.BooleanExplorationNegation;
import experiment.exploration.Exploration;
import experiment.exploration.IntegerExplorationPlusMagnitude;
import experiment.exploration.IntegerExplorationPlusOne;
import experiment.explorer.*;
import md5.MD5CallableImpl;
import md5.MD5Instr;
import md5.MD5Manager;
import mersenne.MersenneCallableImpl;
import mersenne.MersenneManager;
import mersenne.MersenneTwisterInstr;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Message;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.wallet.BasicKeyChain;
import simplex.SimplexCallableImpl;
import simplex.SimplexManager;
import org.apache.commons.math3.optim.OptimizationData;
import org.apache.commons.math3.optim.linear.SimplexSolverInstr;
import org.bitcoinj.core.Sha256Hash;
import perturbation.location.PerturbationLocation;
import quicksort.QuickSortCallableImpl;
import quicksort.QuickSortInstr;
import quicksort.QuickSortManager;
import rsa.RSACallable;
import rsa.RSAManager;
import sudoku.SudokuCallableImpl;
import sudoku.SudokuInstr;
import sudoku.SudokuManager;
import torrent.TorrentCallable;
import torrent.TorrentManager;
import weka.core.Instances;
import weka.experiment.CrossValidationResultProducer;
import weka.experiment.Experiment;
import zip.LZWInstr;
import zip.ZipCallableImpl;
import zip.ZipManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bdanglot on 02/05/16.
 */
public class ParserArgs {

    private static String typePerturbed;

    public static final List<PerturbationLocation> locations = new ArrayList<>();

    public static void parseArgs(String[] args) {

        if (getIndexOfOption("-help", args) != -1 || getIndexOfOption("-h", args) != -1)
            usage();

        if (getIndexOfOption("-v", args) != -1)
            Runner.verbose = true;

        int currentIndex = -1;
        if ((currentIndex = getIndexOfOption("-time", args)) != -1) {
            try {
                Runner.numberOfSecondsToWait = Integer.parseInt(args[currentIndex + 1]);
            } catch (NumberFormatException e) {
                System.err.println("Time specified must be an integer.");
                usage();
            }
        }

        if ((currentIndex = getIndexOfOption("-size", args)) != -1) {
            try {
                Runner.sizeOfEachTask = Integer.parseInt(args[currentIndex + 1]);
            } catch (NumberFormatException e) {
                System.err.println("Size specified must be an integer.");
                usage();
            }
        }

        if ((currentIndex = getIndexOfOption("-nb", args)) != -1) {
            try {
                Runner.numberOfTask = Integer.parseInt(args[currentIndex + 1]);
            } catch (NumberFormatException e) {
                System.err.println("Number of Task specified must be an integer.");
                usage();
            }
        }

        if ((currentIndex = getIndexOfOption("-type", args)) != -1) {
            if (args[currentIndex + 1].equals("Numerical") || args[currentIndex + 1].equals("Boolean"))
                typePerturbed = args[currentIndex + 1];
            else
                usage();
        }

        if ((currentIndex = getIndexOfOption("-s", args)) != -1) {
            buildSubject(currentIndex + 1, args);
        }

        if ((currentIndex = getIndexOfOption("-exp", args)) != -1) {
            buildExp(currentIndex + 1, args);
        } else {
            if ((currentIndex = getIndexOfOption("-run", args)) != -1) {
                run(currentIndex + 1, args);
            } else {
                Runner.explorers.add(new CallExplorer(new IntegerExplorationPlusOne()));
                Runner.explorers.add(new CallExplorer(new IntegerExplorationPlusMagnitude()));
                Runner.explorers.add(new RandomExplorer(new IntegerExplorationPlusOne()));
                Runner.explorers.add(new CallExplorer(new BooleanExplorationNegation()));
                Runner.explorers.add(new RandomExplorer(new BooleanExplorationNegation()));
            }
        }

    }

    private static void run(int index, String[] args) {
        int[] arrayInteger = null;
        int currentIndex = index;

        if (currentIndex + 1 < args.length) {
            String[] arrayStr;
            if ((arrayStr = args[currentIndex + 1].split(":")).length > 1) {
                arrayInteger = new int[arrayStr.length];
                for (int i = 0; i < arrayStr.length; i++)
                    arrayInteger[i] = Integer.parseInt(arrayStr[i]);
            }
        }

        while (currentIndex < args.length && !args[currentIndex].startsWith("-")) {
            switch (args[currentIndex]) {
                case "tasknumber":
                    if (arrayInteger != null)
                        TaskNumberExplorer.numberOfTask = arrayInteger;
                    runNumberTask();
                    break;
                case "tasksize":
                    if (arrayInteger != null)
                        TaskSizeExplorer.sizeOfTask = arrayInteger;
                    runSizeTask();
                    break;
                case "seed":
                    if (arrayInteger != null)
                        SeedExplorer.seeds = arrayInteger;
                    runSeed();
                    break;
                default:
                    usage();
            }
            currentIndex++;
        }
    }

    private static void buildExp(int index, String[] args) {

        int currentIndex = index;

        while (currentIndex < args.length && !args[currentIndex].startsWith("-")) {
            switch (args[currentIndex]) {
                case "rnd":
                    currentIndex = buildRnd(currentIndex + 1, args);
                    break;
                case "call":
                    currentIndex = buildCall(currentIndex + 1, args);
                    break;
                case "heatmap":
                case "hm":
                    buildHeatMap();
                    currentIndex++;
                    break;
                default:
                    usage();
            }
        }
    }

    private static void runNumberTask() {
        assert Runner.CUP != null : "CUP must be initialized with -s cmd";
        TaskNumberExplorer.run(Runner.CUP, Runner.classCallable, Runner.manager.getClass(), typePerturbed != null ? typePerturbed : "Numerical", Runner.inputType);
    }

    private static void runSizeTask() {
        assert Runner.CUP != null : "CUP must be initialized with -s cmd";
        TaskSizeExplorer.run(Runner.CUP, Runner.classCallable, Runner.manager.getClass(), typePerturbed != null ? typePerturbed : "Numerical", Runner.inputType);
    }

    private static void runSeed() {
        assert Runner.CUP != null : "CUP must be initialized with -s cmd";
        SeedExplorer.run(Runner.CUP, Runner.classCallable, Runner.manager.getClass(), typePerturbed != null ? typePerturbed : "Numerical", Runner.inputType);
    }

    private static void buildHeatMap() {
        Runner.explorers.add(new HeatMapExplorer(new IntegerExplorationPlusMagnitude()));
    }

    private static int buildCall(int i, String[] args) {
        Exploration exploration = getExploration(i, args);
        if (magnitude)
            i++;
        i++;
        Runner.explorers.add(new CallExplorer(exploration));
        return i++;
    }

    private static int buildRnd(int i, String[] args) {
        Exploration exploration = getExploration(i, args);
        if (magnitude)
            i++;
        i++;
        int repeat;
        if (i < args.length) {
            try {
                repeat = Integer.parseInt(args[i]);
                i++;
            } catch (NumberFormatException e) {
                repeat = 5;
            }
        } else
            repeat = 5;

        float[] randomRate = null;
        if (i < args.length) {
            try {
                String[] rndRateStr;
                if ((rndRateStr = args[i].split(":")).length > 1) {
                    randomRate = new float[rndRateStr.length];
                    for (int index = 0; index < rndRateStr.length; index++)
                        randomRate[index] = Float.parseFloat(rndRateStr[index]);
                    i++;
                }
            } catch (NumberFormatException e) {
                randomRate = new float[]{0.001f, 0.005f, 0.01f, 0.05f, 0.1f, 0.15f, 0.2f, 0.25f, 0.3f};
            }
        }

        if (randomRate == null)
            randomRate = new float[]{0.001f, 0.005f, 0.01f, 0.05f, 0.1f, 0.15f, 0.2f, 0.25f, 0.3f};

        Runner.explorers.add(new RandomExplorer(exploration, repeat, randomRate));
        return i;
    }

    //@TODO
    private static boolean magnitude = false;

    private static Exploration getExploration(int i, String[] args) {
        switch (args[i]) {
            case "magnitude":
                typePerturbed = "Numerical";
                String[] mag = null;
                if (i + 1 < args.length && (mag = args[i + 1].split(":")).length > 1) {
                    int[] magint = new int[mag.length];
                    for (int index = 0; index < mag.length; index++)
                        magint[index] = Integer.parseInt(mag[index]);
                    magnitude = true;
                    return new IntegerExplorationPlusMagnitude(magint);
                } else
                    return new IntegerExplorationPlusMagnitude();
            case "one":
                typePerturbed = "Numerical";
                return new IntegerExplorationPlusOne();
            case "boolean":
                typePerturbed = "Boolean";
                return new BooleanExplorationNegation();
            default:
                return new IntegerExplorationPlusOne();
        }
    }

    private static void buildSubject(int index, String[] args) {
        switch (args[index]) {
            case "zip":
                Runner.setup(LZWInstr.class, ZipCallableImpl.class, new ZipManager(), typePerturbed != null ? typePerturbed : "Numerical", String.class);
                break;
            case "torrent":
                Runner.setup(BDecoder.class, TorrentCallable.class, new TorrentManager(), typePerturbed != null ? typePerturbed : "Numerical", String.class);
                break;
            case "mersenne":
            case "mt":
                Runner.setup(MersenneTwisterInstr.class, MersenneCallableImpl.class, new MersenneManager(), typePerturbed != null ? typePerturbed : "Numerical", Long.class);
                break;
            case "md5":
                Runner.setup(MD5Instr.class, MD5CallableImpl.class, new MD5Manager(), typePerturbed != null ? typePerturbed : "Numerical", String.class);
                break;
            case "bitcoin":
            case "bc":

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

                Runner.setup(BasicKeyChain.class, BitcoinCallable.class, new BitcoinManager(), typePerturbed != null ? typePerturbed : "Numerical", Tuple.class);
                break;
            case "classifier":
            case "bayes":
                Runner.setup(CrossValidationResultProducer.class, BayesCallable.class, new BayesManager(), typePerturbed != null ? typePerturbed : "Numerical", Experiment.class);
                break;
            case "sudoku":
                Runner.setup(SudokuInstr.class, SudokuCallableImpl.class, new SudokuManager(), typePerturbed != null ? typePerturbed : "Numerical", int[][].class);
                break;
            case "cipher":
            case "rsa":
                Class rsaCoreEngine = null;
                try {
                    rsaCoreEngine = ParserArgs.class.getClassLoader().loadClass("org.bouncycastle.crypto.engines.RSACoreEngine");
                    Runner.setup(rsaCoreEngine, RSACallable.class, new RSAManager(), typePerturbed != null ? typePerturbed : "Numerical", String.class);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case "simplex":
                Runner.setup(SimplexSolverInstr.class, SimplexCallableImpl.class, new SimplexManager(), typePerturbed != null ? typePerturbed : "Numerical", OptimizationData[].class);
                break;
            default:
            case "qs":
            case "quicksort":
                Runner.setup(QuickSortInstr.class, QuickSortCallableImpl.class, new QuickSortManager(),
                        typePerturbed != null ? typePerturbed : "Numerical", List.class);
                break;
        }
    }

    private static int getIndexOfOption(String opt, String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(opt))
                return i;
        }
        return -1;
    }

    public static void usage() {
        System.out.println("options available : ");
        System.out.println("\t-size <integer> specify the size of each task");
        System.out.println("\t-nb <integer> specify the number of task");
        System.out.println("\t-time <integer> specify the number of seconds to wait until timeout");
        System.out.println("\t-v or -verbose to active Runner verbose mode");
        System.out.println("\t-s <subject> to specify the subject");
        System.out.println("\tvalue for <subject> : qs zip torrent rsa sudoku bayes simplex mt md5 bc");
        System.out.println("\tquicksort is used if you don't specify it");
        System.out.println("\t-exp <exp> specify the exp");
        System.out.println("\tvalue for <exp> call rnd heatmap");
        System.out.println("\tfor call and rnd exp you can specify which <exploration> just after it.");
        System.out.println("\tvalue for <exploration> : one magnitude boolean");
        System.out.println("\tyou can specify an array of magnitude to be used just after the key-word magnitude");
        System.out.println("\ta list of integer separated with \":\" (1:2:3 for example)");
        System.out.println("\tafter the exploration, you can specify the random rates list used by rnd explorer just as for the magnitude (but with float)");
        System.out.println("\tyou can run as many as you want exp call and rnd");
        System.out.println("\tif no exp is specified, <call one> <call magnitude> <rnd one> <call boolean> <rnd boolean> will be run");
        System.out.println("\t-run <tasksize> or <tasknumber> to run the exploration of the impact of the size or the number of task");
        System.out.println("\truns will be executed before everything else, you must specify a subject (with -s) before");
        System.out.println("\t-help display this help");
        System.exit(-1);
    }


}
