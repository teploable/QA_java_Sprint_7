package ru.yandex.praktikum.courier;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.praktikum.ScooterService;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierSteps {
    private final CourierClient courierClient = new CourierClient();

    @Step("Создать учетную запись курьера")
    public Response createCourier(Courier courier) {
        Response response = courierClient.createCourier(courier);
        printResponseBody("Создание курьера: ", response, ScooterService.BODY_TRUE);
        return response;
    }

    @Step("Авторизация под учетной записью курьера")
    public Response loginCourier(Courier courier) {
        Response response = courierClient.loginCourier(courier);
        printResponseBody("Авторизация курьера: ", response, ScooterService.BODY_TRUE);
        return response;
    }

    @Step("Удаление учетной записи курьера по id")
    public void deleteCourier(String courierId) {
        Response response = courierClient.deleteCourier(courierId);
        printResponseBody("Удаление курьера: ", response, ScooterService.BODY_TRUE);
    }

    @Step("Проверка результата == true")
    public void checkResultIsTrue(Response response, int statusCode) {
        response
                .then()
                .assertThat()
                .log().all()
                .statusCode(statusCode)
                .body("ok", is(true));
    }

    @Step("Проверка результата текста ответа")
    public void checkResultMessage(Response response, int statusCode, String text) {
        response
                .then()
                .log().all()
                .statusCode(statusCode)
                .and()
                .assertThat()
                .body("message", is(text));
    }

    @Step("Вывести тело ответа")
    public void printResponseBody(String headerText, Response response, boolean detailedLog) {
        if (detailedLog)
            System.out.println(headerText + response.body().asString());
    }

    @Step("Проверка id != null")
    public void checkIdIsNotNull(Response response) {
        response
                .then()
                .assertThat()
                .log().all()
                .statusCode(HTTP_OK)
                .body("id", notNullValue());
    }
}