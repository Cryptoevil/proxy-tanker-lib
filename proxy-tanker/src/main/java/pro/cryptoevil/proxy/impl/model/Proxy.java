package pro.cryptoevil.proxy.impl.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Proxy {
    private String ip;
    private int port;
}
