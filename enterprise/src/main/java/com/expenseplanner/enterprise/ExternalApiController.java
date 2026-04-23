package com.expenseplanner.enterprise;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that exposes the external team's API data through our application.
 * Acts as a proxy/consumer for another team's JSON feed.
 */
@RestController
@RequestMapping("/api/external")
public class ExternalApiController {

    /** Service used to fetch data from the external team's API */
    private final ExternalApiService externalApiService;

    /**
     * Constructor injection of ExternalApiService.
     * @param externalApiService the service that calls the external API
     */
    public ExternalApiController(ExternalApiService externalApiService) {
        this.externalApiService = externalApiService;
    }

    /**
     * Fetches and returns data from the external team's REST API.
     * @return JSON string from the external team's endpoint
     */
    @GetMapping
    public String getExternalData() {
        return externalApiService.fetchExternalData();
    }
}
