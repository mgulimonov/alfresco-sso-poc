package com.max.alfboot.alfresco;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.naming.AuthenticationException;
import java.util.Objects;

/**
 * Created by Maxim on 16-May-15.
 */
public class Alfresco {

    private String baseUrl;
    private volatile String token;
    private String userName;
    private String password;

    private RestTemplate restTemplate;

    public Alfresco(RestTemplate restTemplate, String baseUrl, String userName, String password) {
        this.restTemplate = Objects.requireNonNull(restTemplate);
        this.baseUrl = Objects.requireNonNull(baseUrl);
        this.userName = userName;
        this.password = password;
    }

    public Alfresco(RestTemplate restTemplate, String baseUrl, String token) {
        this.restTemplate = Objects.requireNonNull(restTemplate);
        this.baseUrl = Objects.requireNonNull(baseUrl);
        this.token = Objects.requireNonNull(token, "Token can't be null");
    }

    public <T> T get(String url, Class<T> clazz, Object... params) {
        return restTemplate.getForObject(urlWithToken(url), clazz, params);
    }

    private String urlWithToken(String url) {
        if (url.startsWith("/")) {
            url = baseUrl + url;
        }

        if (!url.contains("?")) {
            return url + "?alf_ticket=" + token();
        } else {
            return url + "&alf_ticket=" + token();
        }
    }

    public String token() {
        if (token == null) {
            token = obtainToken();
        }

        return token;
    }

    private synchronized String obtainToken() {
        try {
            if (token != null) {
                return token;
            }

            String authResult = restTemplate.getForObject(baseUrl + "/api/login?u={username}&pw={password}", String.class, userName, password);
            return authResult.substring(authResult.indexOf("<ticket>") + 8, authResult.indexOf("</ticket>"));
        } catch (HttpStatusCodeException e) {
            throw new BadCredentialsException("Login failed", e);
        }
    }
}
