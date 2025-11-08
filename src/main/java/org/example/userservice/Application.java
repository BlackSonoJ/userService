package org.example.userservice;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.userservice.api.console.ConsoleOut;
import org.example.userservice.api.dto.user.PostOrPutUserDto;

@ApplicationScoped
public class Application {

    @Inject
    private ConsoleOut consoleOut;

    public void start() {
        System.out.println("🚀 Starting User Service Application...");
        var userDto = new PostOrPutUserDto("name", (short) 12, "example@mail.ru");
        consoleOut.createUser(userDto);
        consoleOut.getAllUsers();
        consoleOut.getUserById(1L);
        var userUpdateDto = new PostOrPutUserDto("Alex", (short) 21, "example@gmail.com");
        consoleOut.updateUser(1L, userUpdateDto);
        consoleOut.getAllUsers();
        consoleOut.deleteUser(1L);
        consoleOut.getAllUsers();

        System.out.println("✅ Application finished successfully!");
    }
}
