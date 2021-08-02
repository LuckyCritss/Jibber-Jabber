package com.javafanatics.jibberjabber.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements  UserService{

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }


    @Override
    public List<User> getAll(){
        return userRepository.findAll();
    }

    @Override
    public void save(User user) throws UserService.PasswordMisMatchException {


        if (!user.getPassWord().equals(user.getCheckPassWord()) ) {
            throw new UserService.PasswordMisMatchException("password mismatch");
        }
        user.setPassWord(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(user.getPassWord()));
        userRepository.save(user);
    }

}