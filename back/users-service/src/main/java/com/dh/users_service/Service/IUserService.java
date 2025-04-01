package com.dh.users_service.Service;

import com.dh.users_service.DTO.UserAccountDTO;
import com.dh.users_service.DTO.UserDTO;
import com.dh.users_service.Entity.User;

import java.util.UUID;

public interface IUserService {
    public UserAccountDTO create(User user, String authorization);
    public UserDTO findById(UUID id);
}
