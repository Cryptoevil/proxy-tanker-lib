package pro.cryptoevil.proxy.impl;

import java.util.Collection;

import pro.cryptoevil.proxy.core.IProxyHolder;
import pro.cryptoevil.proxy.impl.model.ProxyNode;

public class ProxyHolder implements IProxyHolder<ProxyNode, String> {

    @Override
    public boolean holdProxy(ProxyNode proxy) {
        return false;
    }

    @Override
    public boolean removeProxy(String parameter) {
        return false;
    }

    @Override
    public ProxyNode getFreshProxy() {
        return null;
    }

    @Override
    public int getProxyCount() {
        return 0;
    }

    @Override
    public Collection<ProxyNode> getProxyList() {
        return null;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
