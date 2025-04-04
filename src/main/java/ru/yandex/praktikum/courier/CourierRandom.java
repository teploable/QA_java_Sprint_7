package ru.yandex.praktikum.courier;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierRandom {
    public static Courier randomCourier() {
        final String login = RandomStringUtils.randomAlphabetic(5);
        final String password = RandomStringUtils.randomNumeric(6);
        final String firstName = RandomStringUtils.randomAlphabetic(7);
        return new Courier(login, password, firstName);
    }
}
