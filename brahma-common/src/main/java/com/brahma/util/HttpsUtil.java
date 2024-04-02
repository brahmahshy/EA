package com.brahma.util;

import lombok.experimental.UtilityClass;

import com.brahma.entity.BrahmaException;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.net.URIBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@UtilityClass
public class HttpsUtil {
    public String get(String url, List<NameValuePair> valuePairs) {
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            URI uri = new URIBuilder(new URI(url)).addParameters(valuePairs).build();
            HttpGet request = new HttpGet(uri);
            return client.execute(request, response -> {
                if (HttpStatus.SC_OK == response.getCode()) {
                    return EntityUtils.toString(response.getEntity());
                }
                return "";
            });
        } catch (IOException e) {
            throw new BrahmaException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public String post(String url, List<NameValuePair> valuePairs) {
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            HttpGet request = new HttpGet(url);
            request.setEntity(new UrlEncodedFormEntity(valuePairs));
            return client.execute(request, response -> {
                if (HttpStatus.SC_OK == response.getCode()) {
                    return EntityUtils.toString(response.getEntity());
                }
                return "";
            });
        } catch (IOException e) {
            throw new BrahmaException(e);
        }
    }
}
