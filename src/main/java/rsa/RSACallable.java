package rsa;

import experiment.CallableImpl;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import org.bouncycastle.util.encoders.Hex;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by bdanglot on 29/04/16.
 */
public class RSACallable extends CallableImpl<String, String> {

    public RSACallable(String input) {
        super(input);
    }

    @Override
    public String call() throws Exception {
        RSAKeyPairGenerator kpg = new RSAKeyPairGenerator();
        kpg.init(new RSAKeyGenerationParameters(new BigInteger("11", 16), new SecureRandom(new byte[]{23,32,42}), 1024, 25));
//      kpg.init(new RSAKeyGenerationParameters(new BigInteger("11", 16), new SecureRandom(), 1024, 5));
//      kpg.init(new RSAKeyGenerationParameters(new BigInteger("11", 16), new SecureRandom(), 768, 25));
        AsymmetricCipherKeyPair pair = kpg.generateKeyPair();
        RSAEngine cipher = new RSAEngine();
        cipher.init(true, pair.getPrivate());
        RSAEngine decipher = new RSAEngine();
        decipher.init(false, pair.getPublic());
        byte [] data = Hex.decode(input);
        data = cipher.processBlock(data, 0, data.length);
        data = decipher.processBlock(data, 0 ,data.length);
        return new String(Hex.encode(data));
    }
}
