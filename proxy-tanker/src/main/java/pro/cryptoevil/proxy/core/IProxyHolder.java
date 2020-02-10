package pro.cryptoevil.proxy.core;

import java.util.Collection;

public interface IProxyHolder<T> extends Daemon {

    boolean holdProxy(T proxy);

    boolean removeProxy(String parameter);

    T getFreshProxy();

    int getProxyCount();

    Collection<T> getProxyList();

    long getCheckDelay();

    long updateCheckDelay(long newDelay);
}
