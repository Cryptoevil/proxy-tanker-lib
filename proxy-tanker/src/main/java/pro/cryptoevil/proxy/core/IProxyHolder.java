package pro.cryptoevil.proxy.core;

import java.util.Collection;

import pro.cryptoevil.proxy.core.Daemon;

public interface IProxyHolder<T, I> extends Daemon {

    boolean holdProxy(T proxy);

    boolean removeProxy(I parameter);

    T getFreshProxy();

    int getProxyCount();

    Collection<T> getProxyList();
}
