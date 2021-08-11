package com.javafanatics.jibberjabber.jibber;

import com.javafanatics.jibberjabber.user.User;
import com.javafanatics.jibberjabber.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JibberServiceImpl implements JibberService {

    private JibberRepository jibberRepository;
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setJibberRepository(JibberRepository jibberRepository) {
        this.jibberRepository = jibberRepository;
    }

    @Override
    public void save(Jibber jibber) {
        jibberRepository.save(jibber);
    }

    @Override
    public List<Jibber> getFeedForUser(User user) {
        user.getFollowing().add(user);
        List <String> following = user.getFollowing()
                .stream()
                .map(u-> u.getHandle())
                .collect(Collectors.toList());
         List<Jibber> jibbers = jibberRepository.findByHandle(following);
         return jibbers;
    }


    @Override
    public List<Jibber> findByHandle(String handle) {
        if (userService.getUserByHandle(handle) == null) {
            return null;
        }
        List<Jibber> jibbers = jibberRepository.findByHandle(handle);
        return jibbers;
    }
}
