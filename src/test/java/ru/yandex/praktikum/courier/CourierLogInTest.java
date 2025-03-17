package ru.yandex.praktikum.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.ScooterService;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;

public class CourierLogInTest {
    private final CourierSteps step = new CourierSteps();
    private Courier courierRandom;

    @Before
    public void startUp() {
        RestAssured.baseURI = ScooterService.BASE_URL;
        courierRandom = CourierRandom.randomCourier();
    }

    @After
    public void tearDown() {
        try {
            Response responseLogin = step.loginCourier(courierRandom);
            String courierId = responseLogin.then().extract().path("id").toString();
            step.deleteCourier(courierId);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("Авторизация существующего курьера")
    @Description("Успешная авторизация при передаче в запросе всех обязательных параметров")
    public void loginCourierPositiveTest() {
        step.createCourier(courierRandom);
        Response response = step.loginCourier(courierRandom);
        step.checkIdIsNotNull(response);
    }

    @Test
    @DisplayName("Авторизация курьера без значения логина")
    @Description("Ошибка при попытке авторизоваться без значения параметра login")
    public void loginCourierWithoutLoginNameTest() {
        courierRandom.setLogin("");
        Response response = step.loginCourier(courierRandom);
        step.checkResultMessage(response, HTTP_BAD_REQUEST, "Недостаточно данных для входа");
    }

    @Test
    @DisplayName("Авторизация курьера без значения пароля")
    @Description("Ошибка при попытке авторизоваться без значения параметра password")
    public void loginCourierWithoutPasswordTest() {
        courierRandom.setPassword("");
        Response response = step.loginCourier(courierRandom);
        step.checkResultMessage(response, HTTP_BAD_REQUEST, "Недостаточно данных для входа");
    }

    @Test
    @DisplayName("Авторизация с некорректным логином")
    @Description("Ошибка при попытке авторизации курьера с передачей неверного логина")
    public void loginCourierIncorrectLogin() {
        courierRandom.setLogin("JackSparrow");
        Response response = step.loginCourier(courierRandom);
        step.checkResultMessage(response, HTTP_NOT_FOUND, "Учетная запись не найдена");
    }

    @Test
    @DisplayName("Авторизация с некорректным паролем")
    @Description("Ошибка при попытке авторизации курьера с передачей неверного пароля")
    public void loginCourierIncorrectPassword() {
        courierRandom.setPassword("22222");
        Response response = step.loginCourier(courierRandom);
        step.checkResultMessage(response, HTTP_NOT_FOUND, "Учетная запись не найдена");
    }

    @Test
    @DisplayName("Авторизация несуществующего курьера")
    @Description("Ошибка при попытке авторизации по несуществующей учетной записи курьера")
    public void loginCourierNotExistedTest() {
        Response response = step.loginCourier(courierRandom);
        step.checkResultMessage(response, HTTP_NOT_FOUND, "Учетная запись не найдена");
    }
}
