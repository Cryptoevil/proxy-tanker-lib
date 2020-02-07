package pro.cryptoevil.proxy.impl.model;

import java.net.Proxy;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString
@EqualsAndHashCode
public class ProxyNode {
    private String id;
    private String host;
    private int port;
    private Proxy.Type proxyType;
}