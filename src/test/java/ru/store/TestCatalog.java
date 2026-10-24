package ru.store;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.catalog.ProductCardComponent;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class TestCatalog extends BaseTest{

    @Feature("Тесты страницы Каталог")
    @DisplayName("Добавление продукта в корзину без перехода")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderCatalog#providerKeywordsAuthorization")
    public void addProductToCart(String url,String email, String pass){
        driver.get(url);
        objectPageNow.getLoginPage().authorizationSite(email, pass);
        objectPageNow.getCatalogPage().isCatalogPageLoaded();
        objectPageNow.getCatalogPage().getFirstProductCard().clickBuyButton();
     Assertions.assertEquals("1", objectPageNow.getCatalogPage().getCartCounterText(),
             "Количество товаров в корзине не обновилось!");
    }

    @Feature("Тесты страницы Каталог")
    @DisplayName("Фильтрация товаров по категории")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderCatalog#providerKeywordsFilteringByCategory")
    public void filteringByCategory(String url,String email, String pass, String category){
        driver.get(url);
        objectPageNow.getLoginPage().authorizationSite(email, pass);
        objectPageNow.getCatalogPage().isCatalogPageLoaded();
        objectPageNow.getCatalogPage().clickCategory(category);
        objectPageNow.getCatalogPage().getFirstProductCard().clickBuyButton();
        Assertions.assertEquals("1", objectPageNow.getCatalogPage().getCartCounterText(),
                "Количество товаров в корзине не обновилось!");
    }

    @Feature("Тесты страницы Каталог")
    @DisplayName("Фильтрация товаров по бренду")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderCatalog#providerKeywordsFilteringByBrand")
    public void filteringByBrand(String url,String email, String pass, String brand){
        driver.get(url);
        objectPageNow.getLoginPage().authorizationSite(email, pass);
        objectPageNow.getCatalogPage().isCatalogPageLoaded();
        objectPageNow.getCatalogPage().clickBrand(brand);
        objectPageNow.getCatalogPage().getFirstProductCard().clickBuyButton();
        Assertions.assertEquals("1", objectPageNow.getCatalogPage().getCartCounterText(),
                "Количество товаров в корзине не обновилось!");
    }

    @Feature("Тесты страницы Каталог")
    @DisplayName("Фильтрация товаров по цене")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderCatalog#providerKeywordsFilteringByPrice")
    public void filterByPrice(String url,String email, String pass, String minPrice, String maxPrice){
        driver.get(url);
        objectPageNow.getLoginPage().authorizationSite(email, pass);
        objectPageNow.getCatalogPage().isCatalogPageLoaded();
        objectPageNow.getCatalogPage().setPriceRange(minPrice, maxPrice);
        objectPageNow.getCatalogPage().getFirstProductCard().clickBuyButton();
        Assertions.assertEquals("1", objectPageNow.getCatalogPage().getCartCounterText(),
                "Количество товаров в корзине не обновилось!");
    }

    @Feature("Тесты страницы Каталог")
    @DisplayName("Фильтрация товаров только в наличии")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderCatalog#providerKeywordsAuthorization")
    public void filterByStokeCheckbox(String url,String email, String pass){
        driver.get(url);
        objectPageNow.getLoginPage().authorizationSite(email, pass);
        objectPageNow.getCatalogPage().isCatalogPageLoaded();
        objectPageNow.getCatalogPage().clickCheckBoxStock();
        List<ProductCardComponent> productCards = objectPageNow.getCatalogPage().productsAfterFiltering();
            for (ProductCardComponent card : productCards) {
                Assertions.assertTrue(card.getStockAmount() > 0,
                        "Товар в карточке должен быть в наличии (остаток > 0), но был: " + card.getStockAmount());
            }
        }

    @Feature("Тесты страницы Каталог")
    @DisplayName("Фильтрация товаров через сортировку")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderCatalog#providerKeywordsFilteringBySorted")
    public void filterBySorted(String url,String email, String pass, String sortValue){
        driver.get(url);
        objectPageNow.getLoginPage().authorizationSite(email, pass);
        objectPageNow.getCatalogPage().isCatalogPageLoaded();
        objectPageNow.getCatalogPage().selectSortingByValue(sortValue);
        switch (sortValue) {
            case "price_asc":
                List<Double> ascPrices = objectPageNow.getCatalogPage().productsAfterFiltering().stream().map(ProductCardComponent::getPrice).collect(Collectors.toList());
                List<Double> expectedAsc = ascPrices.stream().sorted().collect(Collectors.toList());
                assertThat(ascPrices).as("Сортировка по возрастанию цены неверна").containsExactlyElementsOf(expectedAsc);
                break;

            case "price_desc":
                List<Double> descPrices = objectPageNow.getCatalogPage().productsAfterFiltering().stream().map(ProductCardComponent::getPrice).collect(Collectors.toList());
                List<Double> expectedDesc = descPrices.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
                assertThat(descPrices).as("Сортировка по убыванию цены неверна").containsExactlyElementsOf(expectedDesc);
                break;

            case "name_asc":
                List<String> ascNames = objectPageNow.getCatalogPage().productsAfterFiltering().stream().map(ProductCardComponent::getName).collect(Collectors.toList());
                List<String> expectedAscNames = ascNames.stream().sorted().collect(Collectors.toList());
                assertThat(ascNames).as("Сортировка по имени A-Z неверна").containsExactlyElementsOf(expectedAscNames);
                break;

            case "name_desc":
                List<String> descNames = objectPageNow.getCatalogPage().productsAfterFiltering().stream().map(ProductCardComponent::getName).collect(Collectors.toList());
                List<String> expectedDescNames = descNames.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
                assertThat(descNames).as("Сортировка по имени Z-A неверна").containsExactlyElementsOf(expectedDescNames);
                break;

            default:
                throw new IllegalArgumentException("Неизвестный тип сортировки: " + sortValue);
        }
    }

    @Feature("Тесты страницы Каталог")
    @DisplayName("Проверяем наличие картинки у товара")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @MethodSource("helpers.DataProviderCatalog#providerKeywordsAuthorization")
    public void testProductsHaveValidImages(String url,String email, String pass){
        driver.get(url);
        objectPageNow.getLoginPage().authorizationSite(email, pass);
        objectPageNow.getCatalogPage().isCatalogPageLoaded();
        objectPageNow.getCatalogPage().scrollPageToBottom();
        List<ProductCardComponent> cards = objectPageNow.getCatalogPage().productsAfterFiltering();
        assertThat(cards)
                .as("Список товаров на странице не должен быть пустым")
                .isNotEmpty();

        for (ProductCardComponent card : cards) {
            assertThat(card.isImageValid())
                    .as("Картинка товара повреждена, отсутствует или не загрузилась")
                    .isTrue();
        }
    }

}
