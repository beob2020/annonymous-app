package org.beob2020.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.beob2020.user.entity.UserEntity;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseUserDto {
    private UUID userId;
    private String email;
    private Role role;
    private String firstName;
    private String lastName;
}
