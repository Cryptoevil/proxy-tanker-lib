package pro.cryptoevil.proxy.impl.model;

import java.net.Proxy;

import lombok.*;

@Data
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ProxyNode {
    private String id;
    private String host;
    private int port;
    private Proxy.Type proxyType;
}