package org.example.userservice.api.mapper;

import org.example.userservice.api.dto.user.PostOrPutUserDto;
import org.example.userservice.api.dto.user.UserDto;
import org.example.userservice.domain.entities.User;

public class UserMapper {

    public static UserDto toDto(User user) {
        if (user == null) return null;
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getAge(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }

    public static User toEntity(PostOrPutUserDto dto) {
        if (dto == null) return null;
        return new User(dto.name(), dto.age(), dto.email());
    }

    public static User toEntity(Long id, PostOrPutUserDto dto) {
        if (dto == null) return null;
        var user = new User(dto.name(), dto.age(), dto.email());
        user.setId(id);
        return user;
    }
}
