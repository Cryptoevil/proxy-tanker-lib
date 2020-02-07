package pro.cryptoevil.proxy.impl;

import java.util.Collections;

import com.squareup.okhttp.Response;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import pro.cryptoevil.proxy.core.IProxyValidator;
import pro.cryptoevil.proxy.impl.model.ProxyNode;
import pro.cryptoevil.proxy.web.WebClient;

@AllArgsConstructor
public class ProxyValidator implements IProxyValidator<ProxyNode> {

    private String checkUrl;

    @Override
    @SneakyThrows
    public boolean validate(ProxyNode proxyNode) {
        WebClient webClient = new WebClient(proxyNode);
        Response responseEntity = webClient.getResponse(this.checkUrl, Collections.emptyMap());
        boolean isValid = responseEntity != null && responseEntity.isSuccessful();
        if (responseEntity != null) {
            responseEntity.body().close();
        }
        return isValid;
    }
}
