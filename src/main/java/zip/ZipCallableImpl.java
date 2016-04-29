package zip;

import experiment.CallableImpl;

/**
 * Created by spirals on 05/04/16.
 */
public class ZipCallableImpl extends CallableImpl<String,String> {

    public ZipCallableImpl(String input) {
        super(input);
    }

    @Override
    public String call() throws Exception {
        return LZWInstr.decompress(LZWInstr.compress(super.input));
    }
}
