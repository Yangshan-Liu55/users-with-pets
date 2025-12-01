package com.example.userspet.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DogService {
    private final RestTemplate restT = new RestTemplate();
    private static final int MAX_DOG_API_LIMIT = 50;
    private final String dogUrl = "https://dog.ceo/api/breeds/image/random/";

    public List<String> fetchDogImages(int count) {
        try {
            List<String> allImages = new ArrayList<>();
            int remaining = count;

            while (remaining > 0) {
                int batchSize = Math.min(remaining, MAX_DOG_API_LIMIT);

                String url = dogUrl + batchSize;
                Map<String, Object> response = restT.getForObject(url, Map.class);

                if (response == null || !response.containsKey("message")) {
                    throw new IllegalStateException("Random Dog Image API returned an empty response");
                }

                List<String> batchImages = (List<String>) response.get("message");
                allImages.addAll(batchImages);

                remaining -= batchSize;
            }

            return allImages;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to fetch images from Random Dog Image API");
        }
    }
}
