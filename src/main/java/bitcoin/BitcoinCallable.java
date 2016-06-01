package bitcoin;

import experiment.CallableImpl;
import experiment.Tuple;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.InsufficientMoneyException;
import org.bitcoinj.core.Wallet;
import org.junit.runner.Runner;

/**
 * Created by spirals on 25/04/16.
 */
public class BitcoinCallable extends CallableImpl<Tuple, Integer> {

    private BitcoinManager manager;

    public BitcoinCallable(Tuple input, BitcoinManager manager) {
        super(input);
        this.manager = manager;
    }

    @Override
    public Integer call() throws Exception {
        Coin value = Coin.valueOf((int)input.get(2), 0);
        Wallet.SendRequest request = Wallet.SendRequest.to(this.manager.getWalletAppKit((int)input.get(1))
                .wallet().currentReceiveKey().toAddress(BitcoinManager.networkParameters), value);
        request.fee = Coin.valueOf(BitcoinManager.FEE_AMOUNT, 0);
        request.feePerKb = Coin.ZERO;
        try {
            this.manager.getWalletAppKit((int)input.get(0)).wallet().sendCoins(request);
            BitcoinToolbox.mine();
            Thread.sleep(500);
            return 1;
        } catch (InsufficientMoneyException e) {
            return -1;
        }
    }
}
