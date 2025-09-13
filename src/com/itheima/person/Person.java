package com.itheima.person;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Properties;

public class Person {

    private static final String FILE_PATH = "users.properties";
    public Person(){

    }


    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashed = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashed);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveUser(String username, String password) throws IOException {
        Properties props = new Properties();
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                props.load(reader);
            }
        }

        props.setProperty(username, hashPassword(password));

        try (FileWriter writer = new FileWriter(file)) {
            props.store(writer, "User Credentials");
        }
    }

    // 3. 验证用户
    public static boolean validateUser(String username, String password) throws IOException {
        Properties props = new Properties();
        try (FileReader reader = new FileReader(FILE_PATH)) {
            props.load(reader);
        }

        String storedHash = props.getProperty(username);
        if (storedHash == null) return false;

        return storedHash.equals(hashPassword(password));
    }

    public static boolean isContainUser(String username) throws IOException {
        Properties props = new Properties();
        try (FileReader reader = new FileReader(FILE_PATH)) {
            props.load(reader);
        }

        String storedHash = props.getProperty(username);
        if (storedHash == null) return false;
        return true;
    }
}
