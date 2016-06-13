

package perturbation.perturbator;

import java.math.BigInteger;

public interface Perturbator {
    boolean pboolean(boolean value);

    byte pbyte(byte value);

    short pshort(short value);

    int pint(int value);

    long plong(long value);

    char pchar(char value);

    float pfloat(float value);

    double pdouble(double value);

    BigInteger pBigInteger(BigInteger value);

    @Override
    String toString();
}

