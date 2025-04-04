package ru.yandex.praktikum.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.ScooterService;

import static java.net.HttpURLConnection.*;

public class CourierCreationTest {
    private final CourierSteps step = new CourierSteps();
    private Courier courier;

    @Before
    public void startUp() {
        RestAssured.baseURI = ScooterService.BASE_URL;
        courier = CourierRandom.randomCourier();
    }

    @After
    public void tearDown() {
        try {
            Response responseLogin = step.loginCourier(courier);
            String courierId = responseLogin.then().extract().path("id").toString();
            step.deleteCourier(courierId);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("Создание учетной записи курьера")
    @Description("Успешное создание учетной записи курьера при передаче всех обязательных параметров")
    public void createNewCourierTest() {
        Response response = step.createCourier(courier);
        step.checkResultIsTrue(response, HTTP_CREATED);
    }

    @Test
    @DisplayName("Создание учетной записи курьера без передачи обязательного параметра login")
    @Description("Ошибка при попытке создания учетной записи курьера с отсутствующим значением параметра login")
    public void createCourierWithoutLoginTest() {
        courier.setLogin("");
        Response response = step.createCourier(courier);
        step.checkResultMessage(response, HTTP_BAD_REQUEST, "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Создание учетной записи курьера без передачи обязательного параметра password")
    @Description("Ошибка при попытке создания учетной записи курьера с отсутствующим значением параметра password")
    public void createCourierWithoutPasswordTest() {
        courier.setPassword("");
        Response response = step.createCourier(courier);
        step.checkResultMessage(response, HTTP_BAD_REQUEST, "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Создание уже существующего курьера")
    @Description("Ошибка при попытке создания учетной записи курьера с данными уже существующей учетной записи")
    public void createExistedCourierTest() {
        step.createCourier(courier);
        Response response = step.createCourier(courier);
        step.checkResultMessage(response, HTTP_CONFLICT, "Этот логин уже используется");
    }
}
