package mersenne;

import experiment.OracleImpl;
import experiment.Runner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spirals on 18/04/16.
 */
public class MersenneOracleImpl extends OracleImpl<Long,List<Long>> {

    private List<List<Long>> listOfRandomNumbers;

    public MersenneOracleImpl() {
        super();
        super.path = "mersenne";
        listOfRandomNumbers = new ArrayList<>();
        for (Long seed : scenario) {
            List<Long> randomNumbers = new ArrayList<>();
            MersenneTwister mersenneTwister = new MersenneTwister(seed);
            for (int i = 0 ; i < Runner.sizeOfEachTask ; i++)
                randomNumbers.add(mersenneTwister.genrand());
            this.listOfRandomNumbers.add(randomNumbers);
        }
    }

    @Override
    protected Long generateOneTask() {
        return super.randomForGenTask.nextLong();
    }

    @Override
    public String header() {
        String header = Runner.numberOfTask + " list of " + Runner.sizeOfEachTask + " number\n";
        header += "Random numbers generated with " + seedForGenTask + " as seed\n";
        return header;
    }

    @Override
    public boolean check(List<Long> perturbedValue, int index) {
        for (int i = 0 ; i < perturbedValue.size() ; i++)
            if (!listOfRandomNumbers.get(index).get(i).equals(perturbedValue.get(i)))
                return false;
        return true;
    }

    @Override
    public Long get(int index) {
        return new Long(super.scenario.get(index));
    }

}
