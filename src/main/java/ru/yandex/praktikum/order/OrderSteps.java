package ru.yandex.praktikum.order;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.praktikum.ScooterService;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderSteps {
    private final OrderClient ordersClient = new OrderClient();

    @Step("Создать заказ")
    public Response createOrder(Order order) {
        Response response = ordersClient.createOrder(order);
        printResponseBody("Успешное создание заказа: ", response, ScooterService.BODY_TRUE);
        return response;
    }

    @Step("Проверка track != null")
    public void checkTrackIsNotNull(Response response){
        response
                .then()
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("track", notNullValue());
    }

    @Step("Вывести тело ответа")
    public void printResponseBody(String headerText, Response response, boolean detailedLog){
        if (detailedLog)
            System.out.println(headerText + response.body().asString());
    }
}
