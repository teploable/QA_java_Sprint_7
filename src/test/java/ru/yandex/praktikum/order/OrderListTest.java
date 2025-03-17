package ru.yandex.praktikum.order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.ScooterService;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderListTest {
    private final OrderSteps orderStep = new OrderSteps();
    private final OrderClient ordersClient = new OrderClient();

    @Before
    public void startUp() {
        RestAssured.baseURI = ScooterService.BASE_URL;
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("В теле ответа возвращается список заказов")
    public void getOrdersListTest() {
        Response response = ordersClient.orderList();
        orderStep.printResponseBody("Список заказов: ", response, ScooterService.BODY_TRUE);
        response
                .then()
                .statusCode(HTTP_OK).assertThat().body("orders", notNullValue());
    }
}
