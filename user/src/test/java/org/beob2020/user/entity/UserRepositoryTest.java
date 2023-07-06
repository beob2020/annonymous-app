package org.beob2020.user.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class UserRepositoryTest {

    @Test
    @TestTransaction
    public void shouldCreateAndFindUser() {
        //given
        UserEntity user = new UserEntity();
        user.setRole(UserEntity.Rights.ADMIN);
        user.setFirstName("test");
        user.setLastName("json");
        user.setEmail("test@yahoo.com");
        user.setCreatedDate(LocalDateTime.of(2020, 12, 12, 12, 12));
        //when
        UserEntity.persist(user);
        assertNotNull(user.getUserId());

        PanacheEntityBase byId = UserEntity.findById(user.getUserId());
        assertEquals("test", user.getFirstName());
        assertEquals("json", user.getLastName());
        assertEquals("test@yahoo.com", user.getEmail());
        assertEquals(UserEntity.Rights.ADMIN, user.getRole());
        assertEquals(LocalDateTime.of(2020, 12, 12, 12, 12), user.getCreatedDate());


    }

    @Test
    @TestTransaction
    public void shouldFindAllUsersInPages() {
        //given
        UserEntity test1 = new UserEntity();
        test1.setRole(UserEntity.Rights.ADMIN);
        test1.setFirstName("test");
        test1.setLastName("json");
        test1.setEmail("test@yahoo.com");
        test1.setCreatedDate(LocalDateTime.of(2020, 12, 12, 12, 12));

        UserEntity user2 = new UserEntity();
        user2.setRole(UserEntity.Rights.ADMIN);
        user2.setFirstName("test");
        user2.setLastName("json");
        user2.setEmail("test1@yahoo.com");
        user2.setCreatedDate(LocalDateTime.of(2020, 12, 12, 12, 12));
        List<UserEntity> users = List.of(test1, user2);
        UserEntity.persist(test1);
        UserEntity.persist(user2);

        //when
        EntityPage<UserEntity> result = UserEntity.getAllUsersInPages(0, 10, "id");

        //then
        Assertions.assertEquals(users, result.getContent());
    }
}
