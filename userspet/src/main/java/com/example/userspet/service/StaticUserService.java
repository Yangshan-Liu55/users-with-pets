package com.example.userspet.service;

import com.example.userspet.model.UserWithPet;
import com.example.userspet.utils.EnvironmentUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StaticUserService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<UserWithPet> getUsers() {
        if (!EnvironmentUtil.isRender()) {
            return null; // Local mode, not used
        }

        try {
            ClassPathResource resource = new ClassPathResource("static/users-hardcoded.json");
            return new ArrayList<UserWithPet>(objectMapper.readValue(resource.getInputStream(), Map.class).values());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load hardcoded JSON", e);
        }
    }
}
