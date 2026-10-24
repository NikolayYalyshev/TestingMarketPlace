package ru.store;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestLogin extends BaseTest{

    @Feature("Тесты страницы Авторизации")
    @DisplayName("Авторизация на сайте")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderLogin#providerKeywordsAboutAuthorization")
    public void testAuthorization(String url,String email, String pass){
        driver.get(url);
        objectPageNow.getLoginPage().authorizationSite(email, pass);
        Assertions.assertTrue(objectPageNow.getCatalogPage().isCatalogPageLoaded(), "Главная страница каталога не загрузилась");
    }

    @Feature("Тесты страницы Авторизации")
    @DisplayName("Регистрация на сайте")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderLogin#providerKeywordsAboutRegistration")
    public void testRegistration(String url, String personName, String email, String pass){
        driver.get(url);
        objectPageNow.getLoginPage()
                .registrationSite(personName, email, pass);
        Assertions.assertTrue(objectPageNow.getCatalogPage()
                .isCatalogPageLoaded(), "Главная страница каталога не загрузилась");
    }

    @Feature("Тесты страницы Авторизации")
    @DisplayName("попытка авторизоваться с некорректными данными")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderLogin#providerKeywordsAboutAuthorizationFail")
    public void testAuthorizationFail(String url,String email, String pass) {
        driver.get(url);
        Assertions.assertTrue(objectPageNow.getLoginPage()
                        .authorizationSiteFail(email, pass),
                "Непредвиденная ошибка в данных");
    }


}
