

package mersenne;

import java.util.Random;

public class MersenneTwister extends Random {
    public static final int N = 624;

    public static final int M = 397;

    public static final int MATRIX_A = -1727483681;

    public static final int UPPER_MASK = -2147483648;

    public static final int LOWER_MASK = 2147483647;

    private long[] mt;

    private int mti = (MersenneTwister.N) + 1;

    public MersenneTwister() {
        mt = new long[MersenneTwister.N];
    }

    public MersenneTwister(long s) {
        this();
        mt[0] = s;
        for (mti = 1; (mti) < (MersenneTwister.N); (mti)++) {
            mt[mti] = (1812433253 * ((mt[((mti) - 1)]) ^ ((mt[((mti) - 1)]) >> 30))) + (mti);
            mt[mti] &= -1;
        }
    }

    public void initGenRand(long s) {
        mt[0] = s;
        for (mti = 1; (mti) < (MersenneTwister.N); (mti)++) {
            mt[mti] = (1812433253 * ((mt[((mti) - 1)]) ^ ((mt[((mti) - 1)]) >> 30))) + (mti);
            mt[mti] &= -1;
        }
    }

    public long genrand() {
        int y;
        long[] mag01 = new long[]{ 0 , MersenneTwister.MATRIX_A };
        if ((mti) >= (MersenneTwister.N)) {
            int kk;
            if ((mti) == ((MersenneTwister.N) + 1))
                initGenRand(5489);
            
            for (kk = 0; kk < ((MersenneTwister.N) - (MersenneTwister.M)); kk++) {
                y = ((int) (((mt[kk]) & (MersenneTwister.UPPER_MASK)) | ((mt[(kk + 1)]) & (MersenneTwister.LOWER_MASK))));
                mt[kk] = ((mt[(kk + (MersenneTwister.M))]) ^ (y >> 1)) ^ (mag01[(y & 1)]);
            }
            for (; kk < ((MersenneTwister.N) - 1); kk++) {
                y = ((int) (((mt[kk]) & (MersenneTwister.UPPER_MASK)) | ((mt[(kk + 1)]) & (MersenneTwister.LOWER_MASK))));
                mt[kk] = ((mt[(kk + ((MersenneTwister.M) - (MersenneTwister.N)))]) ^ (y >> 1)) ^ (mag01[(y & 1)]);
            }
            y = ((int) (((mt[((MersenneTwister.N) - 1)]) & (MersenneTwister.UPPER_MASK)) | ((mt[0]) & (MersenneTwister.LOWER_MASK))));
            mt[((MersenneTwister.N) - 1)] = ((mt[((MersenneTwister.M) - 1)]) ^ (y >> 1)) ^ (mag01[(y & 1)]);
            mti = 0;
        } 
        y = ((int) (mt[((mti)++)]));
        y ^= y >> 11;
        y ^= (y << 7) & (-1658038656);
        y ^= (y << 15) & (-272236544);
        y ^= y >> 18;
        return y;
    }

    protected synchronized int next(int bits) {
        return ((int) (genrand()));
    }
}

