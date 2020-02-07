package pro.cryptoevil.proxy.integrations.getproxylist;

import java.util.Collections;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import pro.cryptoevil.proxy.core.ProxyRepository;
import pro.cryptoevil.proxy.impl.model.Proxy;
import pro.cryptoevil.proxy.web.WebClient;

@AllArgsConstructor
public class GetProxyListProxyRepository implements ProxyRepository<Proxy> {

    private String endpointUrl;
    private String apiKey;

    private ObjectMapper objectMapper;
    private WebClient webClient;

    @Override
    public Proxy get() {
        String url = endpointUrl.concat(apiKey);
        String response = this.webClient.getResponseBody(url, Collections.emptyMap());

        try {
            return this.objectMapper.readValue(response, Proxy.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
