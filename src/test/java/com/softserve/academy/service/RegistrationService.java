package com.softserve.academy.service;

import com.softserve.academy.model.User;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.UUID;

public class RegistrationService {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By SIGN_UP_BUTTON = By.cssSelector(".header_sign-up-btn > span");
    private final By EMAIL_FIELD = By.id("email");
    private final By FIRST_NAME_FIELD = By.id("firstName");
    private final By PASSWORD_FIELD = By.id("password");
    private final By REPEAT_PASSWORD_FIELD = By.id("repeatPassword");
    private final By SUBMIT_BUTTON = By.cssSelector(".greenStyle");
    private final By SUCCESS_MESSAGE = By.cssSelector(".mdc-snackbar__label");

    public RegistrationService(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openRegistrationModal() {
        wait.until(ExpectedConditions.elementToBeClickable(SIGN_UP_BUTTON)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(EMAIL_FIELD));
    }

    public User prepareUniqueUser(User user) {
        String uniqueEmail = "test" + UUID.randomUUID().toString().substring(0, 8) + user.getEmail();
        return new User(uniqueEmail, user.getName(), user.getPassword());
    }

    public void fillForm(User user) {
        enterText(EMAIL_FIELD, user.getEmail());
        enterText(FIRST_NAME_FIELD, user.getName());
        enterText(PASSWORD_FIELD, user.getPassword());
        enterText(REPEAT_PASSWORD_FIELD, user.getPassword());
    }

    public void submit() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(SUBMIT_BUTTON));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
    }

    public String getSuccessMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(SUCCESS_MESSAGE)).getText();
    }

    private void enterText(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }
}