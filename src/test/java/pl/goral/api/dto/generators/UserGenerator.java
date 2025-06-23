package pl.goral.api.dto.generators;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import pl.goral.api.dto.UserDto;


@Slf4j
@UtilityClass
public class UserGenerator {

    private static final Faker faker = new Faker();

    private static final int MIN_LENGTH = 6;
    private static final int MAX_RETRIES = 12;


    public static UserDto generate() {
        return UserDto.builder()
                .username(faker.name().username())
                .password(faker.internet().password(MIN_LENGTH, MAX_RETRIES))
                .email(faker.internet().emailAddress())
                .build();
    }
}