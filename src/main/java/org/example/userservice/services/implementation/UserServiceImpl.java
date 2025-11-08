package org.example.userservice.services.implementation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.userservice.api.dto.user.PostOrPutUserDto;
import org.example.userservice.api.dto.user.UserDto;
import org.example.userservice.api.mapper.UserMapper;
import org.example.userservice.common.exceptions.EntityNotFoundException;
import org.example.userservice.infrastructure.dao.UserDao;
import org.example.userservice.services.UserService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;

    public UserDto getUserById(Long id) {
        var user = userDao.findById(id);
        if (Objects.isNull(user)) {
            throw new EntityNotFoundException("User", id);
        }
        return UserMapper.toDto(user);
    }

    public List<UserDto> getAllUsers() {
        return userDao.findAll().stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    public UserDto createUser(PostOrPutUserDto userDto) {
        return UserMapper.toDto(userDao.save(UserMapper.toEntity(userDto)));
    }

    public UserDto updateUser(Long id, PostOrPutUserDto userDto) {
        return UserMapper.toDto(userDao.update(id, UserMapper.toEntity(userDto)));
    }

    public void deleteUser(Long id) {
        userDao.delete(id);
    }
}
