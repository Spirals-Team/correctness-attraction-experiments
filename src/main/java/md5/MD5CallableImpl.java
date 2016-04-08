package md5;

import experiment.CallableImpl;
import experiment.Runner;

/**
 * Created by spirals on 07/04/16.
 */
public class MD5CallableImpl extends CallableImpl<String> {

    public MD5CallableImpl(String originalValue) {
        super(originalValue);
    }

    @Override
    public String call() throws Exception {
        return "0x" + MD5.toHexString(MD5.computeMD5(super.originalValue.getBytes()));
    }

    @Deprecated
    public static void main(String[] args) {
        System.out.println("Run md5...");
        Runner.setup(MD5.class, MD5CallableImpl.class, "runMd5", new Md5OracleImpl(), String.class);
        Runner.runAllCampaign();
    }

}
