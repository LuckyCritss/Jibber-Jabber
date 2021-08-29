package com.javafanatics.jibberjabber.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;


    @Autowired
    public void setUserRepository(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByHandle(String handle) {
        return userRepository.findUserByHandle(handle);
    }

    @Override
    public User getUserComplete(String handle) {
        return userRepository.getUserComplete(handle);
    }

    @Override
    public User getAll() {
        return userRepository.getAll();
    }

    @Override
    public void follow(String userHandle, String toFollowHandle) {
        User user = getUserComplete(userHandle);
        User toFollow = getUserByHandle(toFollowHandle);
        user.getFollowing().add(toFollow);
        userRepository.save(user);
    }

    @Override
    public void unFollow(String userHandle, String unFollowHandle) {
        User user = getUserComplete(userHandle);
        User toFollow = getUserByHandle(unFollowHandle);
        user.getFollowing().remove(toFollow);
        userRepository.save(user);
    }

    @Override
    public Set<User> getUsersIFollow(String handle) {
        User user = getUserComplete(handle);
        return user.getFollowing();
    }

    @Override
    public List<User> getUsersToFollow(String handle, Set<User> usersIFollow) {
        List<User> users = userRepository.findAll();
        users.removeAll(usersIFollow);
        return users;
    }

    @Override
    public void save(User user) throws PasswordException, PasswordMisMatchException {
        if (user.getPassWord() != null && user.getPassWord().length() < 5) {
            throw new PasswordException("password malformed");
        }
        if (user.getPassWord() == null && user.getId() == 0) {
            throw new PasswordException("password malformed");
        }
        if (user.getPassWord().length() > 4 && !user.getPassWord().equals(user.getCheckPassWord()) ) {
            throw new PasswordMisMatchException("password mismatch");
        }

        if (user.getPassWord() == null && user.getId() > 0) {
            user.setPassWord(userRepository.findById(user.getId()).get().getPassWord());
        } else {
            user.setPassWord(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(user.getPassWord()));
        }
        userRepository.save(user);
    }



}
