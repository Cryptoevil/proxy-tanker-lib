package pro.cryptoevil.proxy.impl;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import pro.cryptoevil.proxy.core.IProxyHolder;
import pro.cryptoevil.proxy.core.IProxyValidator;
import pro.cryptoevil.proxy.impl.model.ProxyNode;

@Slf4j
public class ProxyHolder implements IProxyHolder<ProxyNode> {

    private long checkDelay;

    private Thread thread;
    private IProxyValidator<ProxyNode> proxyValidator;
    private Deque<ProxyNode> proxyList = new ArrayDeque<>();

    public ProxyHolder(IProxyValidator<ProxyNode> proxyValidator, long checkDelay, boolean autoStart) {
        this.checkDelay = checkDelay;
        this.proxyValidator = proxyValidator;
        if (autoStart) {
            this.start();
        }
    }

    @Override
    public void start() {
        log.info("start -> Starting proxy holder daemon.");
        this.thread = new Thread(() -> {
            while (true) {
                this.validateProxy();
                try {
                    Thread.sleep(this.checkDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        this.thread.start();
    }

    @Override
    public void stop() {
        log.info("stop -> Stopping proxy holder daemon.");
        thread.interrupt();
    }

    private void validateProxy() {
        ProxyNode proxyNode = this.proxyList.pollLast();
        if (proxyNode != null) {
            boolean result = this.proxyValidator.validate(proxyNode);
            if (result) {
                this.proxyList.offerFirst(proxyNode);
            }
            log.info("validateProxy -> proxy: {}, result: {}", proxyNode, result);
        }
    }

    @Override
    public boolean holdProxy(ProxyNode proxyNode) {
        return proxyNode != null && this.proxyList.offerLast(proxyNode);
    }

    @Override
    public boolean removeProxy(String id) {
        List<ProxyNode> list = this.proxyList
                .stream()
                .filter(node -> node.getId().equals(id))
                .collect(Collectors.toList());

        if (!list.isEmpty()) {
            log.info("removeProxy -> Removed proxy with id: {}", id);
            return this.proxyList.remove(list.get(0));
        }
        log.warn("removeProxy -> Cannot remove proxy id {}, list is empty", id);
        return false;
    }

    @Override
    public ProxyNode getFreshProxy() {
        ProxyNode proxyNode = this.proxyList.pollFirst();
        if (proxyNode != null) {
            log.info("getFreshProxy -> Bring fresh proxy, id: {}" , proxyNode.getId());
        } else {
            log.warn("getFreshProxy -> Proxy list is empty!");
        }
        return this.proxyList.pollFirst();
    }

    @Override
    public int getProxyCount() {
        return this.proxyList.size();
    }

    @Override
    public Collection<ProxyNode> getProxyList() {
        return this.proxyList;
    }

    @Override
    public long getCheckDelay() {
        return this.checkDelay;
    }

    @Override
    public long updateCheckDelay(long newDelay) {
        this.checkDelay = newDelay;
        return this.checkDelay;
    }
}
