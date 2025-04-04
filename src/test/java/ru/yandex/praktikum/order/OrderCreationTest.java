package ru.yandex.praktikum.order;


import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.ScooterService;

import java.util.List;

@RunWith(Parameterized.class)
public class OrderCreationTest {
    private final OrderSteps orderStep = new OrderSteps();

    private final List<String> colour;
    private final OrderClient ordersClient = new OrderClient();
    private Order order;
    private Response response;

    public OrderCreationTest(List<String> colour) {
        this.colour = colour;
    }

    @Parameterized.Parameters(name = "{index}: Выбран цвет: {0}")
    public static Object[][] createOrderWithVariousColourParam() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
                {List.of()},
        };
    }

    @Before
    public void startUp() {
        RestAssured.baseURI = ScooterService.BASE_URL;
        order = new Order("Вася", "Пупкин", "ул. Московская, д.5",
                "4", "+7 999 888 77 66", 4, "2025-03-16",
                "Привезите как можно быстрее", colour);
    }

    @After
    public void tearDown() {
        try {
            String orderId = response.then().extract().path("track").toString();
            ordersClient.cancelOrder(orderId);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("Создание заказа с выбором разных цветов самоката")
    @Description("Заказ успешно создаётся при отправке разных параметров цвета самоката")
    public void createOrderTest() {
        response = orderStep.createOrder(order);
        orderStep.checkTrackIsNotNull(response);
    }
}