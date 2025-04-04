package ru.yandex.praktikum;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class ScooterService {
    public static final String BASE_URL = "http://qa-scooter.praktikum-services.ru";
    public final static boolean BODY_TRUE = true;

    protected RequestSpecification getBaseSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .build();
    }
}