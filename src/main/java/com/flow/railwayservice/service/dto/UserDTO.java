package com.flow.railwayservice.service.dto;

import com.flow.railwayservice.config.Constants;

import com.flow.railwayservice.domain.RUser;
import com.flow.railwayservice.dto.UserRole;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.*;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A DTO representing a user, with his authorities.
 */
public class UserDTO {

    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String login;

    @Size(max = 50)
    private String name;

    @Email
    @Size(min = 5, max = 100)
    private String email;

    private boolean activated = false;

    @Size(min = 2, max = 5)
    private String langKey;

    private UserRole role;

    public UserDTO() {
    }

    public UserDTO(RUser user) {
        this(user.getLogin(), user.getName(),
            user.getEmail(), user.getActivated(), user.getLangKey(),
            user.getRole());
    }

    public UserDTO(String login, String name,
        String email, boolean activated, String langKey, UserRole role) {

        this.login = login;
        this.name = name;
        this.email = email;
        this.activated = activated;
        this.langKey = langKey;
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public String getName(){
    	return name;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActivated() {
        return activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public UserRole getRole(){
    	return role;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
            "login='" + login + '\'' +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", activated=" + activated +
            ", langKey='" + langKey + '\'' +
            ", role=" + role +
            "}";
    }
}
