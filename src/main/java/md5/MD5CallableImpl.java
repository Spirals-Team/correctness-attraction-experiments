package md5;

import experiment.CallableImpl;

/**
 * Created by spirals on 07/04/16.
 */
public class MD5CallableImpl extends CallableImpl<String> {

    public MD5CallableImpl(String originalValue) {
        super(originalValue);
    }

    @Override
    public String call() throws Exception {
        return "0x" + MD5OracleImpl.toHexString(MD5.computeMD5(super.originalValue.getBytes()));
    }
}
