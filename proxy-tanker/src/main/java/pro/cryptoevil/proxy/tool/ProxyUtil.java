package pro.cryptoevil.proxy.tool;

import lombok.NonNull;
import pro.cryptoevil.proxy.impl.model.ProxyNode;

import java.net.Proxy;

public class ProxyUtil {

    public static ProxyNode wrapType(ProxyNode proxyNode,
                                     @NonNull String proxyType) {
        switch (proxyType) {
            case "http":
                proxyNode.setProxyType(Proxy.Type.HTTP);
                proxyNode.setSsl(false);
                break;
            case "https":
                proxyNode.setProxyType(Proxy.Type.HTTP);
                proxyNode.setSsl(true);
                break;
            case "socks":
            case "socks5":
                proxyNode.setProxyType(Proxy.Type.SOCKS);
                proxyNode.setSocksVersion(5);
                proxyNode.setSsl(false);
                break;
            case "socks4":
                proxyNode.setProxyType(Proxy.Type.SOCKS);
                proxyNode.setSocksVersion(4);
                proxyNode.setSsl(false);
                break;
        }
        return proxyNode;
    }
}
