package helpers;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class DataProviderCatalog {

    public static Stream<Arguments> providerKeywordsAuthorization(){
        return Stream.of(Arguments.of("http://localhost:8081", "nikolay.yalyshev1@gmail.com", "Odepoz88"),
                Arguments.of("http://localhost:8081", "nikolas.cage@gmail.com", "armsdealer"),
                Arguments.of("http://localhost:8081","nikolay.yalyshev@gmail.com", "плохо")
        );
    }

    public static Stream<Arguments> providerKeywordsFilteringByCategory(){
        return Stream.of(Arguments.of("http://localhost:8081", "nikolay.yalyshev1@gmail.com", "Odepoz88", "Гаджеты"),
                Arguments.of("http://localhost:8081", "nikolas.cage@gmail.com", "armsdealer", "Смартфоны"),
                Arguments.of("http://localhost:8081", "nikolay.yalyshev1@gmail.com", "Odepoz88", "Ноутбуки")
        );
    }

    public static Stream<Arguments> providerKeywordsFilteringByBrand(){
        return Stream.of(Arguments.of("http://localhost:8081", "nikolay.yalyshev1@gmail.com", "Odepoz88", "Apple"),
                Arguments.of("http://localhost:8081", "nikolas.cage@gmail.com", "armsdealer", "AMD"),
                Arguments.of("http://localhost:8081", "nikolay.yalyshev1@gmail.com", "Odepoz88", "NVIDIA")
        );
    }

    public static Stream<Arguments> providerKeywordsFilteringByPrice(){
        return Stream.of(Arguments.of("http://localhost:8081", "nikolay.yalyshev1@gmail.com", "Odepoz88", "342350", "404300"),
                Arguments.of("http://localhost:8081", "nikolas.cage@gmail.com", "armsdealer", "94550", "245000"),
                Arguments.of("http://localhost:8081", "nikolay.yalyshev1@gmail.com", "Odepoz88", "23750", "174200")
        );
    }

    public static Stream<Arguments> providerKeywordsFilteringBySorted(){
        return Stream.of(Arguments.of("http://localhost:8081", "nikolay.yalyshev1@gmail.com", "Odepoz88", "price_asc"),
                Arguments.of("http://localhost:8081", "nikolas.cage@gmail.com", "armsdealer", "name_desc"),
                Arguments.of("http://localhost:8081", "nikolay.yalyshev1@gmail.com", "Odepoz88", "price_desc")
        );
    }

}
