package ru.store;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestE2E extends BaseTest{

    @Feature("Тесты страницы Корзина")
    @DisplayName("Проверяем возможность добавления товара в корзину и его заказа")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderE2E#providerKeywordsE2EDeliveryProduct")
    public void testLoginSelectItemGoCartChooseDelivery(String url,String email, String pass){
        driver.get(url);
        objectPageNow.getLoginPage().authorizationSite(email, pass);
        objectPageNow.getCatalogPage().isCatalogPageLoaded();
        objectPageNow.getCatalogPage().getFirstProductCard().clickBuyButton();
        objectPageNow.getCatalogPage().goToCartPage();
        objectPageNow.getCartPage().isBasketPageLoaded();
        objectPageNow.getCartPage().clickCheckoutButton();
        Assertions.assertTrue(objectPageNow.getDeliveryPage().isDeliveryPageLoaded(),
                "Страница доставки не загрузилась");
    }

    @Feature("Тесты страницы Корзина")
    @DisplayName("Проверяем возможность добавления товара в корзину и его заказа")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderE2E#providerKeywordsVariousFilters")
    public void selectProductsVariousFilters(String url,String email, String pass, String categoryFirstProd,
                                             String brandFirstProd, String categorySecondProd, String categoryThirdProd,
                                             String brandThirdProd, String minPrice, String maxPrice){
        driver.get(url);
        objectPageNow.getLoginPage().authorizationSite(email, pass);
        objectPageNow.getCatalogPage().isCatalogPageLoaded();
        objectPageNow.getCatalogPage().clickCategory(categoryFirstProd);
        objectPageNow.getCatalogPage().clickBrand(brandFirstProd);
        objectPageNow.getCatalogPage().getFirstProductCard().clickBuyButton();
        objectPageNow.getCatalogPage().clickAllCategory();
        objectPageNow.getCatalogPage().clickAllBrand();
        objectPageNow.getCatalogPage().clickCategory(categorySecondProd);
        objectPageNow.getCatalogPage().getFirstProductCard().clickBuyButton();
        objectPageNow.getCatalogPage().clickAllCategory();
        objectPageNow.getCatalogPage().clickCategory(categoryThirdProd);
        objectPageNow.getCatalogPage().scrollPageToBottom();
        objectPageNow.getCatalogPage().clickBrand(brandThirdProd);
        objectPageNow.getCatalogPage().setPriceRange(minPrice, maxPrice);
        objectPageNow.getCatalogPage().scrollPageToBottom();
        objectPageNow.getCatalogPage().getFirstProductCard().clickBuyButton();
        int actualProductCount = Integer.parseInt(objectPageNow.getCatalogPage().getCartCounterText().trim());
        Assertions.assertTrue(actualProductCount>1,
                "В корзине должно быть больше одного товара!");
    }

    @Feature("Тесты страницы Корзина")
    @DisplayName("Проверяем возможность заказать товар со скидкой")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderE2E#providerKeywordsGetProductsAtDiscount")
    public void personCanGetProductsAtDiscount(String url,String email, String pass, String categoryProd,
                                               String brandFProd, String promo){
        driver.get(url);
        objectPageNow.getLoginPage().authorizationSite(email, pass);
        objectPageNow.getCatalogPage().isCatalogPageLoaded();
        objectPageNow.getCatalogPage().clickCategory(categoryProd);
        objectPageNow.getCatalogPage().clickBrand(brandFProd);
        objectPageNow.getCatalogPage().getFirstProductCard().clickBuyButton();
        objectPageNow.getCatalogPage().goToCartPage();
        objectPageNow.getCartPage().isBasketPageLoaded();
        double priceBeforeDiscount = objectPageNow.getCartPage().getTotalPriceInCart();
        objectPageNow.getCartPage().enterPromoCode(promo);
        objectPageNow.getCartPage().clickPromoApply();
        objectPageNow.getCartPage().getAttributeAppliedPromo();
        double priceWithDiscount = objectPageNow.getCartPage().getTotalPriceInCart();
        Assertions.assertTrue(priceWithDiscount < priceBeforeDiscount,
                "Ошибка: Промокод не применился, цена в корзине не уменьшилась!");
        objectPageNow.getCartPage().clickCheckoutButton();
        objectPageNow.getDeliveryPage().isDeliveryPageLoaded();
        double priceInDelivery = objectPageNow.getDeliveryPage().getTotalPriceInDelivery();
        Assertions.assertEquals(priceWithDiscount, priceInDelivery,
                "Стоимость со скидкой на странице доставки не совпадает с ценой из корзины!");
    }
}

//TODO все переписать на Selenide
