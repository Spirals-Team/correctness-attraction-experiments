package md5;

import experiment.CallableImpl;

/**
 * Created by spirals on 07/04/16.
 */
public class MD5CallableImpl extends CallableImpl<String,byte[]> {

    public MD5CallableImpl(String originalValue) {
        super(originalValue);
    }

    @Override
    public byte[] call() throws Exception {
        return MD5.computeMD5(super.originalValue.getBytes());
    }
}
