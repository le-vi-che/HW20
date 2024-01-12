import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class RegTests {

    @Test
    void successfulRegisterTest() {
        String authData = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}";

        given()
                .body(authData)
                .contentType(JSON)
                .log().uri()

        .when()
            .post("https://reqres.in/api/register")

        .then()
            .log().status()
            .log().body()
            .statusCode(200)
            .body("id", is(4),"token", is("QpwL5tke4Pnpja7X4"));
    }


    @Test
    void successfulCreateTest() {
        String authData = "{\"name\": \"bond\", \"job\": \"qa\"}";

        given()
                .body(authData)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("https://reqres.in/api/users")

                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("bond"),"job", is("qa"));
    }


    @Test
    void unsuccessfullReg400Test() {
        String authData = "";

        given()
                .body(authData)
                .log().uri()

        .when()
            .post("https://reqres.in/api/register")

        .then()
            .log().status()
            .log().body()
            .statusCode(400)
            .body("error", is("Missing email or username"));
    }

    @Test
    void userMissingPassTest() {
        String authData = "{\"email\": \"eveasdas.holt@reqres.in\", \"password\": \"\"}";

        given()
                .body(authData)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("https://reqres.in/api/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void notDefindedEmailTest() {
        String authData = "{\"email\": \"123test@bk.ru\", \"password\": \"pistol\"}";

        given()
                .body(authData)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("https://reqres.in/api/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Note: Only defined users succeed registration"));
    }

    @Test
    void wrongBodyTest() {
        String authData = "%***%";

        given()
                .body(authData)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("https://reqres.in/api/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(400);
    }

    @Test
    void unsuccessReg415Test() {
        given()
                .log().uri()
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(415);
    }
}
