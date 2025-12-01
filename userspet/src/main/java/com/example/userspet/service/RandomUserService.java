package com.example.userspet.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class RandomUserService {

    private final RestTemplate restT = new RestTemplate();
    private final String randomUsersUrl = "https://randomuser.me/api/?results=";

    public Map<String, Object> fetchUsers(int count, String nat) {
        try {
            String url = randomUsersUrl + count;

            if (nat != null && !nat.isEmpty()) {
                url += "&nat=" + nat;
            }

            Map<String, Object> response = restT.getForObject(url, Map.class);

            if (response == null || !response.containsKey("results")) {
                throw new IllegalStateException("RandomUser API returned empty response");
            }

            return response;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to fetch users from RandomUser API");
        }
    }
}
