package pro.cryptoevil.proxy.tool;

import lombok.NonNull;
import pro.cryptoevil.proxy.impl.model.ProxyNode;

import java.net.Proxy;

public class ProxyUtil {

    public static ProxyNode.ProxyNodeBuilder wrapType(ProxyNode.ProxyNodeBuilder builder,
                                                      @NonNull String proxyType) {
        switch (proxyType) {
            case "http":
                builder.proxyType(Proxy.Type.HTTP);
                builder.ssl(false);
                break;
            case "https":
                builder.proxyType(Proxy.Type.HTTP);
                builder.ssl(true);
                break;
            case "socks":
            case "socks5":
                builder.proxyType(Proxy.Type.SOCKS);
                builder.socksVersion(5);
                builder.ssl(false);
                break;
            case "socks4":
                builder.proxyType(Proxy.Type.SOCKS);
                builder.socksVersion(4);
                builder.ssl(false);
                break;
        }
        return builder;
    }
}
