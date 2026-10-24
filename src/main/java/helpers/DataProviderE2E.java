package helpers;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class DataProviderE2E {

    public static Stream<Arguments> providerKeywordsE2EDeliveryProduct() {
        return Stream.of(Arguments.of("http://localhost:8081", "nikolay.yalyshev1@gmail.com", "Odepoz88"),
                Arguments.of("http://localhost:8081", "nikolas.cage@gmail.com", "armsdealer"),
                Arguments.of("http://localhost:8081", "nikolay.yalyshev1@gmail.com", "Odepoz88")
        );
    }

    public static Stream<Arguments> providerKeywordsVariousFilters() {
        return Stream.of(Arguments.of("http://localhost:8081", "nikolay.yalyshev1@gmail.com", "Odepoz88", "Планшеты", "Apple", "Смартфоны", "Видеокарты", "AMD", "40035", "65030"),
                Arguments.of("http://localhost:8081", "nikolas.cage@gmail.com", "armsdealer", "Смартфоны", "Samsung", "Смартфоны", "Видеокарты", "AMD", "90035", "165030"),
                Arguments.of("http://localhost:8081", "nikolay.yalyshev1@gmail.com", "Odepoz88", "Планшеты", "Samsung", "Ноутбуки", "Видеокарты", "NVIDIA", "41450", "245000")
        );
    }

    public static Stream<Arguments> providerKeywordsGetProductsAtDiscount() {
        return Stream.of(Arguments.of("http://localhost:8081", "nikolay.yalyshev1@gmail.com", "Odepoz88", "Планшеты", "Apple", "SAVE10"),
                Arguments.of("http://localhost:8081", "nikolas.cage@gmail.com", "armsdealer", "Смартфоны", "Samsung", "FLAT500"),
                Arguments.of("http://localhost:8081", "nikolay.yalyshev1@gmail.com", "Odepoz88", "Планшеты", "Samsung", "WELCOME")
        );
    }
}
