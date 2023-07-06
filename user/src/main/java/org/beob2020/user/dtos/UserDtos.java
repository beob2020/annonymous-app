package org.beob2020.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDtos {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
}