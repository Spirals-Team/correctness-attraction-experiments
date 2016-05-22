package mersenne;

import experiment.Oracle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spirals on 18/04/16.
 */
public class MersenneOracle implements Oracle<Long,List<Long>> {

    @Override
    public boolean assertPerturbation(Long input, List<Long> output) {
        MersenneTwister mersenneTwister = new MersenneTwister(input);
        List<Long> inputList = new ArrayList<>();
        for (int i = 0 ; i < output.size() ; i++)
            inputList.add(mersenneTwister.genrand());
        return inputList.equals(output);
    }
}
