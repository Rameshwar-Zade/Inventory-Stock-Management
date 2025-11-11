package com.gt.mapper;

import com.gt.dto.UserDTO;
import com.gt.entity.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        if (user == null) return null;
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setMobileNo(user.getMobileNo());
        dto.setAddress(user.getAddress());
        dto.setStatus(user.getStatus());
        dto.setRoleName(user.getRole() != null ? user.getRole().getName() : null);
        return dto;
    }
}
