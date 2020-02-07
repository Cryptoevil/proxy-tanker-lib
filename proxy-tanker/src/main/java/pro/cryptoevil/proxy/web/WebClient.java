package pro.cryptoevil.proxy.web;

import java.io.IOException;
import java.util.Map;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebClient {

    private OkHttpClient okHttpClient;

    public WebClient() {
        this.okHttpClient = new OkHttpClient();
    }

    public String getResponseBody(String url, Map<String, String> headers) {
        Response response = this.getResponse(url, headers);
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
}

