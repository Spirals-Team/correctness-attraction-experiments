

package perturbation.perturbator;

import java.math.BigInteger;

public class AddNPerturbatorImpl implements Perturbator {
    private int n = 1;

    public AddNPerturbatorImpl(int n) {
        AddNPerturbatorImpl.this.n = n;
    }

    @Override
    public boolean pboolean(boolean value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte pbyte(byte value) {
        return ((byte) (value + (AddNPerturbatorImpl.this.n)));
    }

    @Override
    public short pshort(short value) {
        return ((short) (value + (AddNPerturbatorImpl.this.n)));
    }

    @Override
    public int pint(int value) {
        return value + (AddNPerturbatorImpl.this.n);
    }

    @Override
    public long plong(long value) {
        return value + (AddNPerturbatorImpl.this.n);
    }

    @Override
    public char pchar(char value) {
        return ((char) (value + (AddNPerturbatorImpl.this.n)));
    }

    @Override
    public float pfloat(float value) {
        return value + (AddNPerturbatorImpl.this.n);
    }

    @Override
    public double pdouble(double value) {
        return value + (AddNPerturbatorImpl.this.n);
    }

    public BigInteger pBigInteger(BigInteger value) {
        return value.add(BigInteger.valueOf(AddNPerturbatorImpl.this.n));
    }
}

