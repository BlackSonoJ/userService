package org.example.userservice.api.console;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import org.example.userservice.api.dto.user.PostOrPutUserDto;
import org.example.userservice.api.dto.user.UserDto;
import org.example.userservice.common.exceptions.GlobalExceptionHandler;
import org.example.userservice.infrastructure.dao.UserDao;
import org.example.userservice.services.UserService;

import java.util.List;

@ApplicationScoped
public class ConsoleOut {

    @Inject
    private UserService userService;

    @Inject
    private GlobalExceptionHandler exceptionHandler;

    public void getAllUsers() {
        try {
            userService.getAllUsers().stream().map(Object::toString).forEach(System.out::println);
        } catch (Exception e) {
            exceptionHandler.handle(e);
        }
    }

    public void getUserById(Long id) {
        try {
            System.out.println(userService.getUserById(id).toString());
        } catch (Exception e) {
            exceptionHandler.handle(e);
        }
    }

    public void createUser(@Valid PostOrPutUserDto userDto) {
        try {
            System.out.println(userService.createUser(userDto).toString());
        } catch (Exception e) {
            exceptionHandler.handle(e);
        }
    }

    public void updateUser(Long id, @Valid PostOrPutUserDto userDto) {
        try {
            System.out.println(userService.updateUser(id, userDto).toString());
        } catch (Exception e) {
            exceptionHandler.handle(e);
        }
    }

    public void deleteUser(Long id) {
        try {
            userService.deleteUser(id);
            System.out.println("User with id " + id + " has been deleted");
        } catch (Exception e) {
            exceptionHandler.handle(e);
        }
    }

}
