package ch.bzz.applicationmanager.data;

import ch.bzz.applicationmanager.module.User;
import ch.bzz.applicationmanager.service.Config;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UserData {
    public static final String[] GUEST = {"guest", "user", "admin"};
    public static final String[] USER = {"user", "admin"};
    public static final String[] ADMIN = {"admin"};

    public static User findUser(String username, String password) {
        User user = new User();
        List<User> userList = readUserJson();

        for (User entry : userList) {
            if (entry.getUserName().equals(username) &&
                    entry.getPassword().equals(password))
                user = entry;
        }
        return user;
    }

    public static boolean userAllowed(String complete, String[] allowedRoles) {
        if (complete == null || complete.isEmpty() || complete.equals("guest")) return false;
        String[] array = complete.split("\n");
        try {
            return userAllowed(array[0], array[1], array[2], array[3], allowedRoles);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean userAllowed(String userUUID, String userName, String password, String userRole, String[] allowedRoles) {
        User user = findUser(userName, password);
        if (user == null ||
                user.getUserUUID() == null ||
                !user.getUserUUID().equals(userUUID) ||
                user.getUserRole() == null ||
                !user.getUserRole().equals(userRole)
        ) return false;
        List<String> allowedList = new ArrayList<>();
        Collections.addAll(allowedList, allowedRoles);

        return allowedList.contains(userRole);
    }

    private static List<User> readUserJson() {
        List<User> userList = new ArrayList<>();
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(Config.getProperty("userJSON")));
            ObjectMapper objectMapper = new ObjectMapper();
            User[] users = objectMapper.readValue(jsonData, User[].class);
            userList.addAll(Arrays.asList(users));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return userList;
    }
}
