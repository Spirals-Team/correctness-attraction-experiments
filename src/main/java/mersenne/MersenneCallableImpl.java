package mersenne;

import experiment.CallableImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spirals on 18/04/16.
 */
public class MersenneCallableImpl extends CallableImpl<Long, List<Long>> {

    private int size;

    public MersenneCallableImpl(Long input, int size) {
        super(input);
        this.size = size;
    }

    @Override
    public List<Long> call() throws Exception {
        MersenneTwisterInstr mersenneTwister = new MersenneTwisterInstr(input);
        List<Long> rndList = new ArrayList<>();
        for(int i = 0; i < this.size ; i++)
            rndList.add(mersenneTwister.genrand());
        return rndList;
    }
}