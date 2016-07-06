package quicksort;

import perturbation.enactor.NeverEnactorImpl;
import perturbation.enactor.RandomEnactorImpl;
import perturbation.enactor.RandomUniqueEpsilonEnactor;
import perturbation.location.PerturbationLocation;
import perturbation.perturbator.AddNPerturbatorImpl;
import perturbation.perturbator.NothingPerturbatorImpl;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * Created by bdanglot on 23/06/16.
 */
public class WCETBensh {

    private static final int NB_ARRAY = 100;
    private static final int SIZE_ARRAY = 4000;

    public static void main(String[] args) {
        QuickSortManager manager = new QuickSortManager(NB_ARRAY, SIZE_ARRAY);
        run(manager);
    }

    private static List<PerturbationLocation> getLocationPivot() {
        List<PerturbationLocation> locations = new ArrayList<>();
        locations.add(QuickSortInstr.__L8);
        return locations;
    }

    private static void run(QuickSortManager manager) {
        long accTime = 0L;

        for (int i = 0; i < NB_ARRAY; i++) {
            int[] task = manager.getTask(i);
            QuickSort.sort(task, 0, task.length - 1);
            QuickSort.count = 0;
            QuickSort.sort(task, 0, task.length - 1);
            accTime += QuickSort.count;
            QuickSort.count = 0;
        }
        System.out.println((double)accTime / (double)NB_ARRAY);

        accTime = 0L;
        for (int i = 0; i < NB_ARRAY; i++) {
            int[] task = manager.getTask(i);
            QuickSort.sort(task, 0, task.length - 1);
            QuickSort.count = 0;

            QuickSort.__L0.setEnactor(new RandomEnactorImpl());
            QuickSort.__L0.setPerturbator(new AddNPerturbatorImpl(1));

            QuickSort.r_sort(task, 0, task.length - 1);
            accTime +=  QuickSort.rcount ;
            QuickSort.rcount  = 0;

            QuickSort.__L0.setEnactor(new NeverEnactorImpl());
            QuickSort.__L0.setPerturbator(new NothingPerturbatorImpl());
        }
        System.out.println((double)accTime / (double)NB_ARRAY);

        QuickSort.__L0.setEnactor(new NeverEnactorImpl());
        QuickSort.__L0.setPerturbator(new NothingPerturbatorImpl());


    }


}
