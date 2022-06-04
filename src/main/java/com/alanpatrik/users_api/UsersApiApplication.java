package com.alanpatrik.users_api;

import com.alanpatrik.users_api.domain.document.Role;
import com.alanpatrik.users_api.domain.document.User;
import com.alanpatrik.users_api.domain.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UsersApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsersApiApplication.class, args);
    }

}
