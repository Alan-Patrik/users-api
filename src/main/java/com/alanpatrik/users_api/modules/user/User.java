package com.alanpatrik.users_api.modules.user;

import com.alanpatrik.users_api.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User  {

    @Id
    private String id;
    private String name;
    private String username;
    @JsonIgnore
    private String password;
    private Role role;
}
