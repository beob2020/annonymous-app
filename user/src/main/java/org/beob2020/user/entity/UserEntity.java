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
import org.beob2020.user.dtos.Role;
import org.beob2020.util.CustomLocalDateTimeDeserializer;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

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


    public static EntityPage<UserEntity> getAllUsersInPages(int page, int pageSize, String sort) {
        PanacheQuery<PanacheEntityBase> queryAllUsers = UserEntity.findAll(Sort.by(sort));
        if (queryAllUsers == null || queryAllUsers.list().isEmpty()) {
            throw new NotFoundException("No Users found");
        }
        PanacheQuery<PanacheEntityBase> pagedUsers = queryAllUsers.page(Page.of(page, pageSize));

           Set<UserEntity> sortedLastNameList = pagedUsers.stream()
                    .map(UserEntity.class::cast)
                   .sorted((o1, o2) -> o1.getLastName().compareToIgnoreCase(o2.getLastName()))
                    .collect(Collectors.toCollection(LinkedHashSet::new));

        return EntityPage.<UserEntity>builder()
                .content(sortedLastNameList)
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
