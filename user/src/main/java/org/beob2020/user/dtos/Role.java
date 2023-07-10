package org.beob2020.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@AllArgsConstructor
@NoArgsConstructor
public enum Role {
    ADMIN,
    USER;

    @Setter
    @Getter
     private String value;
        public String getValue() {
            return value;
        }

}
