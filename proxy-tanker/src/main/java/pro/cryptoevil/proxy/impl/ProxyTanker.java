package pro.cryptoevil.proxy.impl;

import lombok.extern.slf4j.Slf4j;
import pro.cryptoevil.proxy.core.IProxyHolder;
import pro.cryptoevil.proxy.core.IProxyTanker;
import pro.cryptoevil.proxy.core.ProxyRepository;
import pro.cryptoevil.proxy.impl.model.Proxy;
import pro.cryptoevil.proxy.impl.model.ProxyNode;
import pro.cryptoevil.proxy.tool.IdProvider;

@Slf4j
public class ProxyTanker implements IProxyTanker {

    private int proxyLimit;
    private long grabDelay;

    private Thread thread;
    private ProxyRepository<Proxy> proxyRepository;
    private IProxyHolder<ProxyNode> proxyHolder;

    public ProxyTanker(ProxyRepository<Proxy> proxyRepository, IProxyHolder<ProxyNode> proxyHolder,
                       int proxyLimit, long grabDelay, boolean autoStart) {
        this.proxyLimit = proxyLimit;
        this.grabDelay = grabDelay;
        this.proxyHolder = proxyHolder;
        this.proxyRepository = proxyRepository;
        if (autoStart) {
            this.start();
        }
    }

    @Override
    public void start() {
        log.info("start -> Starting proxy tanker daemon.");
        this.thread = new Thread(() -> {
            while (true) {
                this.grabAttempt();
                try {
                    Thread.sleep(this.grabDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    @Override
    public void stop() {
        log.info("stop -> Stopping proxy tanker daemon.");
        this.thread.interrupt();
    }

    private boolean grabAttempt() {
        if (this.proxyHolder.getProxyCount() >= this.proxyLimit) {
            return false;
        }

        Proxy proxy = this.proxyRepository.get();
        if (proxy == null) {
            log.warn("grabAttempt -> Error, proxy is null. Check again in {} milliseconds", this.grabDelay);
            return false;
        }
        if (proxy.getIp() == null || proxy.getPort() == 0) {
            log.warn("grabAttempt -> Error, proxy host or port is null!!");
            return false;
        }

        ProxyNode proxyNode = ProxyNode.builder()
                .proxyType(java.net.Proxy.Type.HTTP)
                .id(IdProvider.getStringId())
                .host(proxy.getIp())
                .port(proxy.getPort())
                .build();
        log.info("grabAttempt -> Got proxy, ip: {}, port: {}", proxy.getIp(), proxy.getPort());
        this.proxyHolder.holdProxy(proxyNode);
        return true;
    }

    @Override
    public long getGrabDelay() {
        return this.grabDelay;
    }

    @Override
    public long updateGrabDelay(long newGrabDelay) {
        this.grabDelay = newGrabDelay;
        return this.grabDelay;
    }

    @Override
    public int getProxyLimit() {
        return this.proxyLimit;
    }

    @Override
    public int updateProxyLimit(int newProxyLimit) {
        this.proxyLimit = newProxyLimit;
        return this.proxyLimit;
    }
}
