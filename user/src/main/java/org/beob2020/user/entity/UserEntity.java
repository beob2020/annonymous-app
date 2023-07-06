package org.beob2020.user.entity;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.beob2020.util.CustomLocalDateTimeDeserializer;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_user")
public class UserEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", updatable = false)
    public UUID userId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Rights role;

    @NotNull
    @Email
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "created_date", updatable = false)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createdDate = LocalDateTime.now();

    public enum Rights {
        USER, ADMIN
    }

    public static EntityPage<UserEntity> getAllUsersInPages(int page, int pageSize, String sort) {
        PanacheQuery<PanacheEntityBase> queryAllUsers = UserEntity.findAll(Sort.by(sort));
        if (queryAllUsers == null || queryAllUsers.list().isEmpty()) {
            throw new NotFoundException("No Users found");
        }
        queryAllUsers.page(Page.of(page, pageSize));

        return EntityPage.<UserEntity>builder()
                .content(queryAllUsers.list())
                .page(page)
                .pageCount(queryAllUsers.pageCount())
                .totalElements(queryAllUsers.count())
                .build();
    }

    public static UserEntity getUserById(Long id) {
        UserEntity userEntity = UserEntity.findById(id);
        if (userEntity == null) {
            throw new NotFoundException("User not found");
        }
        return userEntity;
    }


}
