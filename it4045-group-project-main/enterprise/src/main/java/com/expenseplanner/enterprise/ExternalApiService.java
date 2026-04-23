package com.expenseplanner.enterprise;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service class that consumes an external team's REST API.
 * Integrates with another team's expense or finance JSON feed.
 */
@Service
public class ExternalApiService {

    /** RestTemplate used to make HTTP requests to external APIs */
    private final RestTemplate restTemplate;

    /**
     * Constructor that builds the RestTemplate.
     * @param builder Spring's RestTemplateBuilder for creating RestTemplate instances
     */
    public ExternalApiService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    /**
     * Fetches JSON data from another team's REST endpoint.
     * Replace the URL below with the other team's actual deployed endpoint.
     * @return raw JSON response as a String
     */
    public String fetchExternalData() {
        // TODO: Replace with the other team's actual REST endpoint URL
        String url = "https://jsonplaceholder.typicode.com/posts";
        return restTemplate.getForObject(url, String.class);
    }
}
