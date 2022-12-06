package tests;

import models.lombok.RegisterBodyLombokModel;
import models.lombok.UserBodyLombokModel;
import models.pojo.RegisterBodyPojoModel;
import models.pojo.UserBodyPojoModel;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static specs.BaseSpecs.baseRequestSpec;
import static specs.BaseSpecs.baseResponseSpec;
import static specs.CreateUserSpecs.createUserRequestSpec;
import static specs.CreateUserSpecs.createUserResponseSpec;
import static specs.RegisterSpecs.registerRequestSpec;
import static specs.RegisterSpecs.registerResponseSpec;

public class RegresInTests {

    @Test
    void listUserTotalTest(){
        given()
                .spec(baseRequestSpec)
                .when()
                .get("/api/users?page=2")
                .then()
                .spec(baseResponseSpec)
                .body("total", is(12));
    }

    @Test
    void userFirstNameByIdTest(){
        given()
                .spec(baseRequestSpec)
                .when()
                .get("/api/users?page=2")
                .then()
                .spec(baseResponseSpec)
                .body("data.find {it.id == 7}.first_name",is("Michael"));
    }

    @Test
    void findNamesInDataTest(){
        given()
                .spec(baseRequestSpec)
                .when()
                .get("/api/unknown")
                .then()
                .spec(baseResponseSpec)
                .body("data.name",hasItems("cerulean", "fuchsia rose", "true red", "aqua sky", "tigerlily", "blue turquoise"));
    }

    @Test
    void createUserWithLombokTest(){
        UserBodyLombokModel userBody = new UserBodyLombokModel();
        userBody.setName("Dmitry");
        userBody.setJob("QA");

        given()
                .spec(createUserRequestSpec)
                .body(userBody)
                .when()
                .post()
                .then()
                .spec(createUserResponseSpec)
                .body("name", is("Dmitry"))
                .body("job", is("QA"));
    }

    @Test
    void createUserWithPojoTest(){
        UserBodyPojoModel userBody = new UserBodyPojoModel();
        userBody.setName("Dmitry");
        userBody.setJob("QA");

        given()
                .spec(createUserRequestSpec)
                .body(userBody)
                .when()
                .post()
                .then()
                .spec(createUserResponseSpec)
                .body("name", is("Dmitry"))
                .body("job", is("QA"));
    }

    @Test
    void registerWithLombokTest(){
        RegisterBodyLombokModel registerBody = new RegisterBodyLombokModel();
        registerBody.setEmail("eve.holt@reqres.in");
        registerBody.setPassword("pistol");

        given()
                .spec(registerRequestSpec)
                .body(registerBody)
                .when()
                .post()
                .then()
                .spec(registerResponseSpec)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void registerWithPojoTest(){
        RegisterBodyPojoModel registerBody = new RegisterBodyPojoModel();
        registerBody.setEmail("eve.holt@reqres.in");
        registerBody.setPassword("pistol");

        given()
                .spec(registerRequestSpec)
                .body(registerBody)
                .when()
                .post()
                .then()
                .spec(registerResponseSpec)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }
}
