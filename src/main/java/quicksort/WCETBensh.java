package quicksort;

import perturbation.enactor.NeverEnactorImpl;
import perturbation.enactor.RandomEnactorImpl;
import perturbation.location.PerturbationLocation;
import perturbation.perturbator.AddNPerturbatorImpl;
import perturbation.perturbator.NothingPerturbatorImpl;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by bdanglot on 23/06/16.
 */
public class WCETBensh {

    public static void main(String[] args) {
        QuickSortManager manager = new QuickSortManager(100, 100000);
        run(manager);
    }

    private static List<PerturbationLocation> getLocationPivot() {
        List<PerturbationLocation> locations = new ArrayList<>();
        locations.add(QuickSortInstr.__L2);
        locations.add(QuickSortInstr.__L3);
        locations.add(QuickSortInstr.__L4);
        locations.add(QuickSortInstr.__L5);
        locations.add(QuickSortInstr.__L6);
        locations.add(QuickSortInstr.__L7);
        locations.add(QuickSortInstr.__L8);
        return locations;
    }

    private static void run(QuickSortManager manager) {
        long accTime = 0L;

        for (int i = 0; i < 100; i++) {
            int[] task = manager.getTask(i);
            long time = System.currentTimeMillis();
            QuickSort.sort(task, 0, task.length - 1);
            long timeElapsed = System.currentTimeMillis() - time;
            accTime += timeElapsed;
        }
        System.out.println(accTime / 100);

        for (PerturbationLocation perturbationLocation : getLocationPivot()) {
            perturbationLocation.setEnactor(new RandomEnactorImpl());
            perturbationLocation.setPerturbator(new AddNPerturbatorImpl(1));

            accTime = 0L;
            for (int i = 0; i < 100; i++) {
                int[] task = manager.getTask(i);
                long time = System.currentTimeMillis();
                QuickSortInstr.sort(task, 0, task.length - 1);
                long timeElapsed = System.currentTimeMillis() - time;
                accTime += timeElapsed;

            }
            System.out.println(perturbationLocation.getLocationIndex() + " " + accTime / 100);

            perturbationLocation.setEnactor(new NeverEnactorImpl());
            perturbationLocation.setPerturbator(new NothingPerturbatorImpl());
        }


    }


}
