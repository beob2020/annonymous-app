package org.beob2020.user.entity;

import org.beob2020.user.dtos.CreateUserRequestDto;
import org.beob2020.user.dtos.Role;

import java.time.LocalDateTime;

public class TestObjectUtil {

    public static UserEntity createUserEntity() {
        UserEntity user = new UserEntity();
        user.setRole(Role.ADMIN);
        user.setFirstName("David");
        user.setLastName("Samuel");
        user.setEmail("test@yahoo.com");
        user.setCreatedDate(LocalDateTime.of(2020, 12, 12, 12, 12));
        return user;
    }

}
