package org.beob2020.user.service;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.beob2020.user.dtos.CreateUserRequestDto;
import org.beob2020.user.dtos.Role;
import org.beob2020.user.dtos.UpdateUserRequestDto;
import org.beob2020.user.entity.EntityPage;
import org.beob2020.user.entity.UserEntity;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserService {
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

    @Transactional
    public static void updateUser(UpdateUserRequestDto userDto, UserEntity userEntity) {
        userEntity.setEmail(userDto.getEmail());
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.persist();
    }

    @Transactional
    public static void createNewUser(CreateUserRequestDto createUserDto) {
        UserEntity user = new UserEntity();
        user.setEmail(createUserDto.getEmail());
        user.setFirstName(createUserDto.getFirstName());
        user.setLastName(createUserDto.getLastName());
        user.setRole(Role.ADMIN);
        user.persist();
    }
}
