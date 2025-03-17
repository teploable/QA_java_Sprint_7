package ru.yandex.praktikum.order;

import io.restassured.response.Response;
import ru.yandex.praktikum.ScooterService;

import static io.restassured.RestAssured.given;
import static ru.yandex.praktikum.PathConfig.*;

public class OrderClient extends ScooterService {
    public Response createOrder(Order order) {
        return given()
                .spec(getBaseSpecification())
                .and()
                .body(order)
                .when()
                .post(CREATE_ORDER_PATH);
    }

    public Response orderList() {
        return given()
                .spec(getBaseSpecification())
                .when()
                .get(CREATE_ORDER_PATH);
    }

    public void cancelOrder(String orderId) {
        String orderData = "{ \"track\": " + orderId + "}";
        given()
                .spec(getBaseSpecification())
                .and()
                .body(orderData)
                .when()
                .put(CANCEL_ORDER_PATH);
    }
}
