package com.softserve.academy.tests;

import com.softserve.academy.base.BaseTest;
import com.softserve.academy.model.User;
import com.softserve.academy.repository.UserRepository;
import com.softserve.academy.service.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GreenCityValidRegistrationTest extends BaseTest {

    private RegistrationService service;

    @BeforeEach
    void openSite() {
        driver.get("https://www.greencity.cx.ua/#/greenCity");
        service = new RegistrationService(driver);
    }

    @TestFactory
    @DisplayName("Registration tests from CSV")
    List<DynamicTest> registrationTests() {

        List<User> users = UserRepository.getUsersFromCsv("/registration_data.csv");

        return users.stream()
                .map(user -> DynamicTest.dynamicTest(
                        "Register: " + user.getEmail(),
                        () -> {
                            User uniqueUser = service.prepareUniqueUser(user);

                            service.openRegistrationModal();
                            service.fillForm(uniqueUser);
                            service.submit();

                            String message = service.getSuccessMessage();
                            assertTrue(message.contains("successfully registered"));
                        }
                ))
                .toList();
    }
}