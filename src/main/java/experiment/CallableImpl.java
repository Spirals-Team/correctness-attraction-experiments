package experiment;

import java.util.concurrent.Callable;

/**
 * Created by spirals on 08/04/16.
 */
public abstract class CallableImpl<T,P> implements Callable<P> {

    protected T originalValue;

    public CallableImpl(T originalValue) {
        this.originalValue = originalValue;
    }

    @Override
    public abstract P call() throws Exception;
}
