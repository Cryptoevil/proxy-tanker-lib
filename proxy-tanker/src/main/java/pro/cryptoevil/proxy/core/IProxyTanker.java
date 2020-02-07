package pro.cryptoevil.proxy.core;

import pro.cryptoevil.proxy.core.Daemon;

public interface IProxyTanker extends Daemon {

    long getGrabDelay();

    long updateGrabDelay(long newGrabDelay);

    int getProxyLimit();

    int updateProxyLimit(int newProxyLimit);
}
