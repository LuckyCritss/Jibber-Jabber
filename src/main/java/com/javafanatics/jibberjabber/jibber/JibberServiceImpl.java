package com.javafanatics.jibberjabber.jibber;

import com.javafanatics.jibberjabber.user.User;
import com.javafanatics.jibberjabber.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JibberServiceImpl implements JibberService {


    UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository){
        this.userRepository  = userRepository ;
    }

    JibberRepository JibberRepository;

    @Autowired
    public void setJibberRepository(JibberRepository JibberRepository){
        this.JibberRepository = JibberRepository;
    }


    @Override
    public List<Jibbers> getAll() {
        return JibberRepository.findAll( );
    }

    @Override
    public void saveOrUpdate(Jibbers jibberTweet, int userId){
        User user = JibberRepository.getUserById(userId);
        jibberTweet.setUser(user);
        JibberRepository.saveAll(jibberTweet);
    }


}
