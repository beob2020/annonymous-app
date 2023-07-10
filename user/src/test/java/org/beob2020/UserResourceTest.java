package org.beob2020;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.quarkus.test.InjectMock;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.persistence.EntityManager;
import org.beob2020.user.dtos.Role;
import org.beob2020.user.dtos.UpdateUserRequestDto;
import org.beob2020.user.entity.TestObjectUtil;
import org.beob2020.user.entity.UserEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;


@QuarkusTest
public class UserResourceTest {
    @InjectMock
    EntityManager entityManager;

    @Test
    public void shouldFindAllUsersInPages() {
        //given
        UserEntity test1 = new UserEntity();
        test1.setRole(Role.ADMIN);
        test1.setFirstName("test1");
        test1.setLastName("json1");

    }

    @Test
    @TestTransaction
    public void testCreateUserEndpoint() {
        UserEntity user = TestObjectUtil.createUserEntity();
        ObjectMapper mapper = new ObjectMapper();
        ObjectMapper objectMapper = mapper.registerModule(new JavaTimeModule());

        try {
            String userJson = objectMapper.writeValueAsString(user);
            given()
                    .body(userJson)
                    .contentType(ContentType.JSON)
                    .when().post("/api/createUser")
                    .then()
                    .statusCode(201)
                    .body(is("User is Created"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void shouldFindUserById() {
        //given
        UserEntity user = new UserEntity();
        user.setUserId(UUID.randomUUID());
        user.setRole(Role.ADMIN);
        user.setFirstName("test");
        user.setLastName("json");
        user.setEmail("test@yahoo.com");
        user.setCreatedDate(LocalDateTime.of(2020, 12, 12, 12, 12));
        when(entityManager.find(UserEntity.class, user.getUserId())).thenReturn(user);


        given()
                .pathParam("id", user.getUserId())
                .when().get("/api/getUserById/{id}")
                .then()
                .statusCode(200)
                .body("firstName", is("test"))
                .body("lastName", is("json"))
                .body("email", is("test@yahoo.com"))
                .body("role", is("ADMIN"))
                .body("createdDate", is("2020-12-12T12:12:00"));
    }
    @Test
    @TestTransaction
    public void shouldFindUserAndUpdate() {
        //given
        UserEntity user = new UserEntity();
        user.setUserId(UUID.randomUUID());
        user.setRole(Role.ADMIN);
        user.setFirstName("test");
        user.setLastName("json");
        user.setEmail("test@yahoo.com");
        user.setCreatedDate(LocalDateTime.of(2020, 12, 12, 12, 12));
        when(entityManager.find(UserEntity.class, user.getUserId())).thenReturn(user);

        UpdateUserRequestDto updateUserRequestDto = new UpdateUserRequestDto();
        updateUserRequestDto.setFirstName("test2");
        updateUserRequestDto.setLastName("json2");
        updateUserRequestDto.setEmail("newTest@yahoo.com");

        given()
                .pathParam("id", user.getUserId())
                .contentType(ContentType.JSON)
                .body(updateUserRequestDto)
                .when().put("/api/updateUser/{id}")
                .then()
                .statusCode(200)
                .body(is("User is updated " + user.getUserId()));
    }
}
