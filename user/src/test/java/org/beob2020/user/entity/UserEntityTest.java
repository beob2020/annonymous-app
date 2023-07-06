package org.beob2020.user.entity;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static io.smallrye.common.constraint.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class UserEntityTest {
    @Test
    @TestTransaction
    public void shouldCreateAndFindUser() {

        UserEntity user = new UserEntity();

        user.setRole(UserEntity.Rights.ADMIN);
        user.setFirstName("test");
        user.setLastName("json");
        user.setEmail("test@yahoo.com");
        user.setCreatedDate(LocalDateTime.of(2020, 12, 12, 12, 12));

        UserEntity.persist(user);
        assertNotNull(user.id);

        user = UserEntity.findById(user.id);
        assertEquals("test@yahoo.com", user.getEmail());
        assertEquals("test", user.getFirstName());
        assertEquals("json", user.getLastName());
        assertEquals(UserEntity.Rights.ADMIN, user.getRole());
        assertEquals(LocalDateTime.of(2020, 12, 12, 12, 12), user.getCreatedDate());

    }
}
