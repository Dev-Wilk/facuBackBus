package com.facu.backbus.dto;

import com.facu.backbus.model.enums.UserType;

public class UserDTO {
    private Long id;
    private String fullName;
    private String login;
    private String password;
    private UserType userType;

    public UserDTO() {
    }

    public UserDTO(Long id, String fullName, String login, String password, UserType userType) {
        this.id = id;
        this.fullName = fullName;
        this.login = login;
        this.password = password;
        this.userType = userType;
    }

    public UserDTO(com.facu.backbus.model.User user) {
        this.id = user.getId();
        this.fullName = user.getFullName();
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.userType = user.getUserType();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
