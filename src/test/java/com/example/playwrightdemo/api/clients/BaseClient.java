package com.example.playwrightdemo.api.clients;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseClient {

    protected final APIRequestContext requestContext;

    protected final Logger log = LoggerFactory.getLogger(getClass());

    public BaseClient(APIRequestContext requestContext) {
        this.requestContext = requestContext;
    }

    protected APIResponse get(String url) {
        log.info("GET {}", url);
        APIResponse response = requestContext.get(url);
        log.info("STATUS {}", response.status());
        log.info("RESPONSE {}", response.text());
        return response;
    }

    protected APIResponse post(String url, Object body) {
        log.info("POST {}", url);
        log.info("POST {}", body);
        APIResponse response = requestContext.post(url, RequestOptions.create().setData(body));
        log.info("STATUS {}", response.status());
        log.info("RESPONSE {}", response.text());
        return response;
    }

    protected APIResponse put(String url, Object body) {
        log.info("PUT {}", url);
        log.info("PUT {}", body);
        APIResponse response = requestContext.put(url, RequestOptions.create().setData(body));
        log.info("STATUS {}", response.status());
        log.info("RESPONSE {}", response.text());
        return response;
    }

    protected APIResponse delete(String url) {
        log.info("DELETE {}", url);
        APIResponse response = requestContext.delete(url);
        log.info("STATUS {}", response.status());
        log.info("RESPONSE {}", response.text());
        return response;
    }

}
