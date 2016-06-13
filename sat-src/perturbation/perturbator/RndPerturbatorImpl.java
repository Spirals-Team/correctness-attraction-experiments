

package perturbation.perturbator;

import java.math.BigInteger;
import java.util.Random;

public class RndPerturbatorImpl implements Perturbator {
    @Override
    public boolean pboolean(boolean value) {
        return new Random().nextBoolean();
    }

    @Override
    public byte pbyte(byte value) {
        return ((byte) (new Random().nextInt()));
    }

    @Override
    public short pshort(short value) {
        return ((short) (new Random().nextInt()));
    }

    @Override
    public int pint(int value) {
        return new Random().nextInt();
    }

    @Override
    public long plong(long value) {
        return new Random().nextLong();
    }

    @Override
    public char pchar(char value) {
        return ((char) (new Random().nextInt()));
    }

    @Override
    public float pfloat(float value) {
        return new Random().nextFloat();
    }

    @Override
    public double pdouble(double value) {
        return ((double) (new Random().nextFloat()));
    }

    @Override
    public BigInteger pBigInteger(BigInteger value) {
        return BigInteger.valueOf(new Random().nextLong());
    }

    @Override
    public String toString() {
        return "RAND";
    }
}

