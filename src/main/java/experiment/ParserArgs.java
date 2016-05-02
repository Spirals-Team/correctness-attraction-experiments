package experiment;

import java.util.InputMismatchException;

/**
 * Created by bdanglot on 02/05/16.
 */
public class ParserArgs {

    //@TODO
    public static void parseArgs(String [] args) {

        if (getIndexOfOption("-help", args) != -1)
            usage();

        if (getIndexOfOption("-v", args) != -1)
            Runner.verbose = true;

        int currentIndex = -1;
        if ( (currentIndex = getIndexOfOption("-time", args)) != -1) {
            try {
                Runner.numberOfSecondsToWait = Integer.parseInt(args[currentIndex + 1]);
            } catch (InputMismatchException e) {
                System.err.println("Time specified must be an integer.");
                usage();
            }
        }

        if ( (currentIndex = getIndexOfOption("-size", args)) != -1) {
            try {
                Runner.sizeOfEachTask = Integer.parseInt(args[currentIndex + 1]);
            } catch (InputMismatchException e) {
                System.err.println("Size specified must be an integer.");
                usage();
            }
        }

        if ( (currentIndex = getIndexOfOption("-nb", args)) != -1) {
            try {
                Runner.sizeOfEachTask = Integer.parseInt(args[currentIndex + 1]);
            } catch (InputMismatchException e) {
                System.err.println("Number of Task specified must be an integer.");
                usage();
            }
        }



    }

    private static int getIndexOfOption(String opt, String [] args) {
        for (int i = 0 ; i < args.length ; i++) {
            if (args[i].equals(opt))
                return i;
        }
        return -1;
    }

    private static int parseOptionExplorer(String[] args, int i) {

        int cpt = 0;

        String nameOfExp = args[i];
        i++;
        cpt++;
        String [] argsExplorer;
        if ((argsExplorer = args[i].split(":")) != null) {

        }

        return cpt;
    }

    public static void usage() {
        System.out.println("options available : ");
        System.out.println("\t-size <integer> specify the size of each task");
        System.out.println("\t-nb <integer> specify the number of task");
        System.out.println("\t-time <integer> specify the number of seconds to wait until timeout");
        System.out.println("\t-v or -verbose to active Runner verbose mode");
        System.out.println("\t-exp <exp> specify the exp. If no exp is specified, the program will run all of it.");
        System.out.println("\tvalues available for <exp> are : addOne addN, BoolCall, IntRnd, BoolRnd");
        System.out.println("\t-help display this help");
        System.exit(-1);
    }


}
