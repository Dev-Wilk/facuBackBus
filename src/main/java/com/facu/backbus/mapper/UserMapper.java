
package com.facu.backbus.mapper;

import com.facu.backbus.dto.UserDTO;
import com.facu.backbus.model.User;


public class UserMapper {
    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setLogin(user.getLogin());
        dto.setPassword(user.getPassword());
        dto.setUserType(user.getUserType());
        return dto;
    }
    
    public static User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setId(dto.getId());
        user.setFullName(dto.getFullName());
        user.setLogin(dto.getLogin());
        user.setPassword(dto.getPassword());
        user.setUserType(dto.getUserType());
        return user;
    }
}