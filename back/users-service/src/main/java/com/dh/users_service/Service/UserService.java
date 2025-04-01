package com.dh.users_service.Service;

import com.dh.users_service.DTO.Account;
import com.dh.users_service.DTO.CreateAccountDTO;
import com.dh.users_service.DTO.UserAccountDTO;
import com.dh.users_service.DTO.UserDTO;
import com.dh.users_service.Entity.User;
import com.dh.users_service.Exception.UserNotFoundException;
import com.dh.users_service.Repository.AccountFeignRepository;
import com.dh.users_service.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountFeignRepository accountFeignRepository;

    @Autowired
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserAccountDTO create(User user, String authorization) {
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        User userDB = userRepository.save(user);
        CreateAccountDTO createAccountDTO = new CreateAccountDTO(user.getFirstName(), user.getLastName());
        Account account = accountFeignRepository.createAccount(userDB.getId(), createAccountDTO, authorization);
        String name = user.getFirstName() + " " + user.getLastName();
        return new UserAccountDTO(userDB.getId(), userDB.getFirstName(), userDB.getLastName(), userDB.getEmail(), userDB.getPhone(), userDB.getDni(), account.getId(), account.getCvu(), name, account.getAlias(), account.getBalance());
    }

    @Override
    public UserDTO findById(UUID id) {
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()){
            throw new UserNotFoundException(id);
        }

        User userDb = user.get();

        return new UserDTO(userDb.getId(), userDb.getFirstName(), userDb.getLastName(), userDb.getEmail(), userDb.getPhone(), userDb.getDni());

    }



}
