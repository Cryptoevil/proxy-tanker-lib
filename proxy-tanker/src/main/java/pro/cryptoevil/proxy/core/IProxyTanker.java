package pro.cryptoevil.proxy.core;

public interface IProxyTanker extends Daemon {

    long getGrabDelay();

    long updateGrabDelay(long newGrabDelay);

    int getProxyLimit();

    int updateProxyLimit(int newProxyLimit);
}
