package com.softserve.academy.repository;

import com.softserve.academy.model.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserRepository {

    public static List<User> getUsersFromCsv(String filePath) {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(UserRepository.class.getResourceAsStream(filePath))
                )
        );

        return reader.lines()
                .skip(1)
                .map(line -> line.split(","))
                .map(data -> new User(data[0], data[1], data[2]))
                .collect(Collectors.toList());
    }
}