

package perturbation.perturbator;

import java.math.BigInteger;

public class MinusOnePerturbatorImpl extends PerturbatorDecorator {
    public MinusOnePerturbatorImpl(Perturbator innerPerturbator) {
        super(innerPerturbator);
    }

    @Override
    public byte pbyte(byte value) {
        return ((byte) (value - 1));
    }

    @Override
    public short pshort(short value) {
        return ((short) (value - 1));
    }

    @Override
    public int pint(int value) {
        return value - 1;
    }

    @Override
    public long plong(long value) {
        return value - 1;
    }

    @Override
    public char pchar(char value) {
        return ((char) (value - 1));
    }

    @Override
    public float pfloat(float value) {
        return value - 1;
    }

    @Override
    public double pdouble(double value) {
        return value - 1;
    }

    public BigInteger pBigInteger(BigInteger value) {
        return value.subtract(BigInteger.ONE);
    }

    @Override
    public String toString() {
        return "MONE";
    }
}

