package mersenne;

import experiment.CallableImpl;
import experiment.Runner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spirals on 18/04/16.
 */
public class MersenneCallableImpl extends CallableImpl<Long, List<Long>> {

    public MersenneCallableImpl(Long input) {
        super(input);
    }

    @Override
    public List<Long> call() throws Exception {
        MersenneTwisterInstr mersenneTwister = new MersenneTwisterInstr(input);
        List<Long> rndList = new ArrayList<>();
        for(int i = 0; i < Runner.sizeOfEachTask ; i++)
            rndList.add(mersenneTwister.genrand());
        return rndList;
    }
}