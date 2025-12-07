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

    public List<UserWithPet> getUsers(int count, String nat) {
        if (!EnvironmentUtil.isRender()) {
            return new ArrayList<>(); // Local mode, not used
        }

        try {
            ClassPathResource resource = new ClassPathResource("static/users-hardcoded.json");
            // return new ArrayList<UserWithPet>(objectMapper.readValue(resource.getInputStream(), Map.class).values());
            List<Map<String, Object>> results = (List<Map<String, Object>>) objectMapper.readValue(resource.getInputStream(), Map.class).get("results");
            return filterUsers(results, count, nat);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load hardcoded JSON", e);
        }
    }

    private List<UserWithPet> filterUsers(List<Map<String, Object>> results, int count, String nat) {
        ArrayList<UserWithPet> filtered = new ArrayList<>();

        for (int i = 0; i < Math.min(count, results.size()); i++) {
            Map<String, Object> user = results.get(i);
            if (nat == null || nat.isEmpty() || (nat != null && user.containsKey("country") && nat.equals(user.get("country")))) {
                UserWithPet u = new UserWithPet();

                u.id = (String) user.get("id");;
                u.gender = (String) user.get("gender");
                u.country = (String) user.get("country");
                u.name = (String)  user.get("name");
                u.email = (String) user.get("email");
                u.dob = user.get("dob");
                u.phone = (String) user.get("phone");
                u.petImage = (String) user.get("petImage");

                filtered.add(u);
            }
        }

        return filtered;
    }
}
