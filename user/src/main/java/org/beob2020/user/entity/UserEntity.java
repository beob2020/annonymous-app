package org.beob2020.user.entity;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_table")
public class UserEntity extends PanacheEntity {

    public enum Rights {
        USER, ADMIN
    }

    @NotNull
    @Enumerated(EnumType.STRING)
    private Rights role;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private LocalDateTime createdDate = LocalDateTime.now();

    public static Page<UserEntity> getAllAdminUsersInPages(int page, int pageSize) {
        PanacheQuery<UserEntity> query = UserEntity.find("role", Rights.ADMIN);
        query = query.page(io.quarkus.panache.common.Page.of(page, pageSize));
        return new Page<>(page, pageSize, query.count(), list("role", Rights.ADMIN, page, pageSize));
    }
}
