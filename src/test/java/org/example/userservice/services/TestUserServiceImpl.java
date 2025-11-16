package org.example.userservice.services;

import org.example.userservice.api.dto.user.PostOrPutUserDto;
import org.example.userservice.api.dto.user.UserDto;
import org.example.userservice.api.mapper.UserMapper;
import org.example.userservice.common.exceptions.EntityNotFoundException;
import org.example.userservice.domain.entities.User;
import org.example.userservice.infrastructure.dao.UserDao;
import org.example.userservice.services.implementation.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getUserByIdReturnsUser() {
        User user = new User( "Test", (short) 23, "example@mail.com");
        user.setId(1L);

        when(userDao.findById(1L)).thenReturn(user);

        UserDto dto = userService.getUserById(1L);

        assertEquals(user.getId(), dto.id());
        assertEquals(user.getName(), dto.name());
        verify(userDao).findById(1L);
    }

    @Test
    void getUserByIdThrowsExceptionWhenNotFound() {
        when(userDao.findById(99L)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> userService.getUserById(99L));
        verify(userDao).findById(99L);
    }

    @Test
    void getAllUsersReturnsList() {
        User user1 = new User( "Test", (short) 23, "example@mail.com");
        User user2 = new User( "Test2", (short) 33, "example2@mail.com");

        when(userDao.findAll()).thenReturn(List.of(user1, user2));

        var list = userService.getAllUsers();

        assertEquals(2, list.size());
        assertEquals(user2.getName(), list.get(1).name());
        verify(userDao).findAll();
    }

    @Test
    void createUserSavesEntity() {
        PostOrPutUserDto dto = new PostOrPutUserDto("Test", (short) 23, "example@mail.com");
        User saved = UserMapper.toEntity(dto);
        saved.setId(10L);

        when(userDao.save(any(User.class))).thenReturn(saved);

        UserDto result = userService.createUser(dto);

        assertEquals(10L, result.id());
        assertEquals(dto.name(), result.name());
        verify(userDao).save(any(User.class));
    }

    @Test
    void updateUserUpdatesEntity() {
        PostOrPutUserDto dto = new PostOrPutUserDto("Test", (short) 23, "example@mail.com");
        User updated = UserMapper.toEntity(dto);
        updated.setId(5L);

        when(userDao.update(eq(5L), any(User.class))).thenReturn(updated);

        UserDto result = userService.updateUser(5L, dto);

        assertEquals(5L, result.id());
        assertEquals("Test", result.name());
        verify(userDao).update(eq(5L), any(User.class));
    }

    @Test
    void deleteUserCallsDao() {
        doNothing().when(userDao).delete(7L);

        userService.deleteUser(7L);

        verify(userDao).delete(7L);
    }
}
