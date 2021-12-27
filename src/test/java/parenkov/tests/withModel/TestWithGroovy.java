package parenkov.tests.withModel;

import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static parenkov.filters.CustomLogFilter.customLogFilter;
import static parenkov.tests.withModel.Specs.baseRequest;
import static parenkov.tests.withModel.Specs.baseSuccessfulResponse;

@Story("Reqres.in")
public class TestWithGroovy {

    @Test
    @DisplayName("Finding a user by name in the list of users")
    void findUserByName() {
        step("Find a user by name", () -> {
            given()
                    .filter(customLogFilter().withCustomTemplates())
                    .spec(baseRequest)
                    .when()
                    .get("/users?page=1")
                    .then()
                    .spec(baseSuccessfulResponse)
                    .log().status()
                    .log().body()

                    // выборка имен юзеров, начинающихся на букву "E" и проверка наличия конкретного имени
                    .body("data.findAll{it.first_name =~/^E/}.first_name.flatten()",
                            hasItem("Emma"));
        });
    }

    @Test
    @DisplayName("Finding a user by surname in the list of users")
    void findUserBySurname() {
        step("Find a user by surname", () -> {
            given()
                    .filter(customLogFilter().withCustomTemplates())
                    .spec(baseRequest)
                    .when()
                    .get("/users?page=1")
                    .then()
                    .spec(baseSuccessfulResponse)
                    .log().status()
                    .log().body()

                    // выборка всех фамилий юзеров и проверка наличия конкретной фамилии
                    .body("data.findAll{it.last_name =~/./}.last_name.flatten()",
                            hasItem("Morris"));
        });
    }
}
