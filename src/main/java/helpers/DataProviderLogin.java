package helpers;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class DataProviderLogin {

    public static Stream<Arguments> providerKeywordsAboutAuthorization(){
        return Stream.of(Arguments.of("http://localhost:8081", "nikolay.yalyshev1@gmail.com", "Odepoz88"),
                Arguments.of("http://localhost:8081", "nikolay.yalyshev1@gmail.com", "Odepoz"),
                Arguments.of("http://localhost:8081","nikolay.yalyshev@gmail.com", "плохо")
        );
    }

    public static Stream<Arguments> providerKeywordsAboutRegistration(){
        return Stream.of(Arguments.of("http://localhost:8081","Николас", "nikolas.cage@gmail.com", "armsdealer"),
                Arguments.of("http://localhost:8081", "Николас", "nikolay@gmail.com", "Odep"),
                Arguments.of("http://localhost:8081","ИИИИИИИИИИИИИИИИИИИИИИИИИИИИИИгорь" , "nikolaygmail.com", "плохо")
        );
    }

    public static Stream<Arguments> providerKeywordsAboutAuthorizationFail(){
        return Stream.of(Arguments.of("http://localhost:8081", "nikolay.yalyshev1@gmail.com", "111111"),
                Arguments.of("http://localhost:8081", "nikolay.yalyshev1@gmail.com", "NikoBelik"),
                Arguments.of("http://localhost:8081","nikolay.yalyshev@gmail.com", "плохо")
        );
    }
}
