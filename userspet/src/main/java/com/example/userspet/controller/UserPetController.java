package com.example.userspet.controller;

import com.example.userspet.model.UserWithPet;
import com.example.userspet.service.DogService;
import com.example.userspet.service.RandomUserService;
import com.example.userspet.service.StaticUserService;
import com.example.userspet.utils.EnvironmentUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserPetController {

    private final RandomUserService randomUserService;
    private final DogService dogService;
    private final StaticUserService staticUserService;

    public UserPetController(RandomUserService randomUserService,
                             DogService dogService,
                             StaticUserService staticUserService) {
        this.randomUserService = randomUserService;
        this.dogService = dogService;
        this.staticUserService = staticUserService;
    }

    @RequestMapping("/users-with-pet")
    public List<UserWithPet> getUsersPet(
            @RequestParam(defaultValue = "12") int count,
            @RequestParam(required = false) String nat
    ) {
        try {
            if (count < 1 || count > 5000) {
                throw new IllegalStateException("Count must be between 1 and 5000");
            }

            // If on Render → return fast hardcoded data
            if (EnvironmentUtil.isRender()) {
                return staticUserService.getUsers(count, nat);
            }

            // Local environment → do real API requests
            Map<String, Object> randomUsers = randomUserService.fetchUsers(count, nat);
            List<Map<String, Object>> results = (List<Map<String, Object>>) randomUsers.get("results");

            List<String> dogImages = dogService.fetchDogImages(count);

            List<UserWithPet> output = this.mapUsersWithPets(results, dogImages, nat);

            return output;
        } catch (Exception e) {
            e.printStackTrace(); // prints the real exception in Render logs
            // throw e; // optionally, rethrow the original exception
            throw new IllegalStateException("Failed to fetch users and their pets");
        }
    }

    private List<UserWithPet> mapUsersWithPets(List<Map<String, Object>> randomUsers, List<String> dogImages, String nat) {
        List<UserWithPet> usersWPetImg = new ArrayList<>();

        for (int i = 0; i < randomUsers.size(); i++) {
            Map<String, Object> randomU = randomUsers.get(i);

            if (nat == null || nat.isEmpty() || (nat != null && randomU.containsKey("nat") && nat.equals(randomU.get("nat")))) {
                UserWithPet user = new UserWithPet();
                user.id = ((Map<String, String>) randomU.get("id")).get("value");
                user.gender = (String) randomU.get("gender");
                user.country = (String) randomU.get("nat");

                Map<String, Object> name = (Map<String, Object>) randomU.get("name");
                user.name = name.get("first") + " " + name.get("last");

                user.email = (String) randomU.get("email");
                user.dob = randomU.get("dob");
                user.phone = (String) randomU.get("phone");
                user.petImage = dogImages.get(i);

                usersWPetImg.add(user);
            }
        }

        return usersWPetImg;
    }
}
