package zip;

import experiment.CallableImpl;

/**
 * Created by spirals on 05/04/16.
 */
public class ZipCallableImpl extends CallableImpl<String> {

    public ZipCallableImpl(String originalValue) {
        super(originalValue);
    }

    @Override
    public String call() throws Exception {
        return LZW.decompress(LZW.compress(super.originalValue));
    }
}
