package ru.store;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
public class TestCart extends BaseTest{

    @Feature("Тесты страницы Корзина")
    @DisplayName("Проверяем, что страница корзины доступна")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderCart#providerKeywordsAuthorization")
    public void testNavigatingCartTab(String url,String email, String pass){
        driver.get(url);
        objectPageNow.getLoginPage().authorizationSite(email, pass);
        objectPageNow.getCatalogPage().isCatalogPageLoaded();
        objectPageNow.getCatalogPage().goToCartPage();
        Assertions.assertTrue(objectPageNow.getCartPage().isBasketPageLoaded(), "Главная страница каталога не загрузилась");
    }

    @Feature("Тесты страницы Корзина")
    @DisplayName("Проверяем, что в корзине есть добавленный товар из каталога")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderCart#providerKeywordsAuthorization")
    public void productToCart(String url,String email, String pass){
        driver.get(url);
        objectPageNow.getLoginPage().authorizationSite(email, pass);
        objectPageNow.getCatalogPage().isCatalogPageLoaded();
      String nameProductCatalog = objectPageNow.getCatalogPage().getFirstProductCard().getName();
        objectPageNow.getCatalogPage().getFirstProductCard().clickBuyButton();
        objectPageNow.getCatalogPage().goToCartPage();
        objectPageNow.getCartPage().isBasketPageLoaded();
        List<String> actualProductNames = objectPageNow.getCartPage().getProductNamesInCart();
        assertThat(actualProductNames)
                .as("Добавленный товар '" + nameProductCatalog + "' должен отображаться в корзине")
                .contains(nameProductCatalog);
    }

    @Feature("Тесты страницы Корзина")
    @DisplayName("Проверяем, что из корзины удаляются товары")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderCart#providerKeywordsAuthorization")
    public void deleteProductInCart(String url,String email, String pass){
        driver.get(url);
        objectPageNow.getLoginPage().authorizationSite(email, pass);
        objectPageNow.getCatalogPage().isCatalogPageLoaded();
        objectPageNow.getCatalogPage().getFirstProductCard().clickBuyButton();
        objectPageNow.getCatalogPage().goToCartPage();
        objectPageNow.getCartPage().isBasketPageLoaded();
        objectPageNow.getCartPage().removeCart();
        Assertions.assertTrue(objectPageNow.getCartPage().productsInCart().isEmpty(),
                "Корзина не пуста после удаления!");
    }

    @Feature("Тесты страницы Корзина")
    @DisplayName("Проверяем, что корзина очищается")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderCart#providerKeywordsAuthorization")
    public void clearProductInCart(String url,String email, String pass){
        driver.get(url);
        objectPageNow.getLoginPage().authorizationSite(email, pass);
        objectPageNow.getCatalogPage().isCatalogPageLoaded();
        objectPageNow.getCatalogPage().getFirstProductCard().clickBuyButton();
        objectPageNow.getCatalogPage().goToCartPage();
        objectPageNow.getCartPage().isBasketPageLoaded();
        objectPageNow.getCartPage().clickButtonClear();
        Assertions.assertTrue(objectPageNow.getCartPage().waitForCartToBeEmpty(),
                "Корзина не пуста после очистки!");
    }

    @Feature("Тесты страницы Корзина")
    @DisplayName("Добавляем промокод к товарам в корзине")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderCart#providerKeywordsApplyPromocode")
    public void testApplyPromocode(String url,String email, String pass, String promo){
        driver.get(url);
        objectPageNow.getLoginPage().authorizationSite(email, pass);
        objectPageNow.getCatalogPage().isCatalogPageLoaded();
        objectPageNow.getCatalogPage().getFirstProductCard().clickBuyButton();
        objectPageNow.getCatalogPage().goToCartPage();
        objectPageNow.getCartPage().isBasketPageLoaded();
        objectPageNow.getCartPage().enterPromoCode(promo);
        objectPageNow.getCartPage().clickPromoApply();
        Assertions.assertEquals("SAVE10", objectPageNow.getCartPage().getAttributeAppliedPromo(),
                "Применен иной промокод");
    }

    @Feature("Тесты страницы Корзина")
    @DisplayName("Добавляем невалидный промокод к товарам в корзине")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderCart#providerKeywordsPromocodeNotApply")
    public void testNegativeInvalidPromocodeNotApply(String url,String email, String pass, String promo){
        driver.get(url);
        objectPageNow.getLoginPage().authorizationSite(email, pass);
        objectPageNow.getCatalogPage().isCatalogPageLoaded();
        objectPageNow.getCatalogPage().getFirstProductCard().clickBuyButton();
        objectPageNow.getCatalogPage().goToCartPage();
        objectPageNow.getCartPage().isBasketPageLoaded();
        objectPageNow.getCartPage().enterPromoCode(promo);
        Assertions.assertTrue(
                objectPageNow.getCartPage().promoMessageInvisible(),
                "Элемент успешного применения промокода отображается на странице, хотя промокод невалиден!"
        );
    }

    @Feature("Тесты страницы Корзина")
    @DisplayName("Проверяем доступность перехода на страницу доставки")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderCart#providerKeywordsAuthorization")
    public void testNavigatingDeliveryTab(String url,String email, String pass){
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
    @DisplayName("Проверяем невозможность перехода на страницу доставки, если корзина пуста")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderCart#providerKeywordsAuthorization")
    public void testNegativeCheckoutButtonIsDisabledWhenCartIsEmpty(String url,String email, String pass){
        driver.get(url);
        objectPageNow.getLoginPage().authorizationSite(email, pass);
        objectPageNow.getCatalogPage().goToCartPage();
        objectPageNow.getCartPage().isBasketPageLoaded();
        Assertions.assertTrue(objectPageNow.getCartPage().productsInCart().isEmpty(),
                "Тест невалиден: корзина должна быть пустой перед проверкой кнопки!");

        Assertions.assertFalse(
                objectPageNow.getCartPage().isCheckoutButtonEnabled(),
                "Ошибка: Кнопка 'К оформлению' активна, хотя в корзине нет товаров!"
        );
    }

}
