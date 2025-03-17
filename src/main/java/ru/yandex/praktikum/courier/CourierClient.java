package ru.yandex.praktikum.courier;

import io.restassured.response.Response;
import ru.yandex.praktikum.ScooterService;
import static ru.yandex.praktikum.PathConfig.*;

import static io.restassured.RestAssured.given;

public class CourierClient extends ScooterService {
    public Response loginCourier(Courier courier) {
        return given()
                .spec(getBaseSpecification())
                .and()
                .body(courier)
                .when()
                .post(LOGIN_COURIER_PATH);
    }

    public Response createCourier(Courier courier) {
        return given()
                .spec(getBaseSpecification())
                .and()
                .body(courier)
                .log().all()
                .when()
                .post(CREATE_COURIER_PATH);
    }

    public Response deleteCourier(String id) {
        return given()
                .spec(getBaseSpecification())
                .delete(DELETE_COURIER_PATH + id);
    }
}
