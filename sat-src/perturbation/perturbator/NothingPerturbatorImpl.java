

package perturbation.perturbator;

import java.math.BigInteger;

public class NothingPerturbatorImpl implements Perturbator {
    @Override
    public boolean pboolean(boolean value) {
        return value;
    }

    @Override
    public byte pbyte(byte value) {
        return value;
    }

    @Override
    public short pshort(short value) {
        return value;
    }

    @Override
    public int pint(int value) {
        return value;
    }

    @Override
    public long plong(long value) {
        return value;
    }

    @Override
    public char pchar(char value) {
        return value;
    }

    @Override
    public float pfloat(float value) {
        return value;
    }

    @Override
    public double pdouble(double value) {
        return value;
    }

    public BigInteger pBigInteger(BigInteger value) {
        return value;
    }

    @Override
    public String toString() {
        return "NTHN";
    }
}

