

package perturbation.perturbator;

import java.math.BigInteger;

public class PerturbatorDecorator implements Perturbator {
    protected Perturbator decoratedPerturbator;

    public PerturbatorDecorator(Perturbator decoratedPerturbator) {
        PerturbatorDecorator.this.decoratedPerturbator = decoratedPerturbator;
    }

    @Override
    public boolean pboolean(boolean value) {
        return PerturbatorDecorator.this.decoratedPerturbator.pboolean(value);
    }

    @Override
    public byte pbyte(byte value) {
        return PerturbatorDecorator.this.decoratedPerturbator.pbyte(value);
    }

    @Override
    public short pshort(short value) {
        return PerturbatorDecorator.this.decoratedPerturbator.pshort(value);
    }

    @Override
    public int pint(int value) {
        return PerturbatorDecorator.this.decoratedPerturbator.pint(value);
    }

    @Override
    public long plong(long value) {
        return PerturbatorDecorator.this.decoratedPerturbator.plong(value);
    }

    @Override
    public char pchar(char value) {
        return PerturbatorDecorator.this.decoratedPerturbator.pchar(value);
    }

    @Override
    public float pfloat(float value) {
        return PerturbatorDecorator.this.decoratedPerturbator.pfloat(value);
    }

    @Override
    public double pdouble(double value) {
        return PerturbatorDecorator.this.decoratedPerturbator.pdouble(value);
    }

    public BigInteger pBigInteger(BigInteger value) {
        return PerturbatorDecorator.this.decoratedPerturbator.pBigInteger(value);
    }

    @Override
    public String toString() {
        return PerturbatorDecorator.this.decoratedPerturbator.toString();
    }
}

