package org.beob2020;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.beob2020.user.entity.TestObjectUtil;
import org.beob2020.user.entity.UserEntity;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class UserResourceTest {
    @Test
    public void testCreateUserEndpoint() {
        UserEntity user = TestObjectUtil.createUserEntity();
        ObjectMapper mapper = new ObjectMapper();
        try {
            String userJson = mapper.writeValueAsString(user);
            given()
                    .body(userJson)
                    .contentType(ContentType.JSON)
                    .when().post("/api/createUser")
                    .then()
                    .statusCode(201)
                    .body(is("User is created"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
