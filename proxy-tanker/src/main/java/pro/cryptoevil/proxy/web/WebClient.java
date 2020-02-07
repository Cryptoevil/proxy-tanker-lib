package pro.cryptoevil.proxy.web;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import lombok.extern.slf4j.Slf4j;
import pro.cryptoevil.proxy.impl.model.ProxyNode;

@Slf4j
public class WebClient {

    private ProxyNode proxyNode;
    private OkHttpClient okHttpClient;

    public WebClient() {
        this.okHttpClient = new OkHttpClient();
    }

    public WebClient(ProxyNode proxyNode) {
        this.proxyNode = proxyNode;
        this.okHttpClient = new OkHttpClient()
                .setProxy(getProxy(proxyNode));
        this.okHttpClient.setConnectTimeout(20000, TimeUnit.MILLISECONDS);
    }

    public String getResponseBody(String url, Map<String, String> headers) {
        Response response = this.getResponse(url, headers);
        return parseResponse(response);
    }

    public Response getResponse(String url, Map<String, String> headers) {
        Request request = getHeaders(headers, new Request.Builder())
                .url(url)
                .build();
        return this.doRequest(request);
    }

    public Response postResponseBody(String url, Map<String, String> headers, Map<String, String> formData) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), formData.toString());
        Request request = getHeaders(headers, new Request.Builder())
                .post(requestBody)
                .url(url)
                .build();
        return this.doRequest(request);
    }

    public String postResponse(String url, Map<String, String> headers, Map<String, String> formData) {
        Response response = this.postResponseBody(url, headers, formData);
        return parseResponse(response);
    }

    private String parseResponse(Response response) {
        if (response != null) {
            try {
                ResponseBody responseBody = response.body();
                String resp = responseBody.string();
                responseBody.close();
                return resp;
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    private Response doRequest(Request request) {
        try {
            return this.okHttpClient
                    .newCall(request)
                    .execute();
        } catch (IOException e) {
            log.warn("Request error: ", e);
            return null;
        }
    }

    private Request.Builder getHeaders(Map<String, String> headersList, Request.Builder builder) {
        headersList.forEach(builder::addHeader);
        return builder;
    }

    private Proxy getProxy(ProxyNode proxyNode) {
        return new Proxy(proxyNode.getProxyType(), new InetSocketAddress(proxyNode.getHost(), proxyNode.getPort()));
    }

    public ProxyNode getCurrentProxy() {
        return this.proxyNode;
    }
}

