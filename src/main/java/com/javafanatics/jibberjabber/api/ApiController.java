package com.javafanatics.jibberjabber.api;

import com.javafanatics.jibberjabber.jibber.Jibber;
import com.javafanatics.jibberjabber.jibber.JibberService;
import com.javafanatics.jibberjabber.user.User;
import com.javafanatics.jibberjabber.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class ApiController {

    private UserService userService;
    private JibberService jibberService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setJibberService(JibberService jibberService) {
        this.jibberService = jibberService;
    }

    @GetMapping("/users")
    public ArrayList<ApiUserObject> users() {
        List<User> users = userService.getAllUsers();
        ArrayList<User> userList = new ArrayList<>(users);

        ArrayList<ApiUserObject> completedUsers = new ArrayList<>();
        List<String> newFollowingList = new ArrayList<>();
        List<String> newFollowersList = new ArrayList<>();

        for (User u : userList) {
            Set<User> followingList = u.getFollowing();
            Set<User> followersList = u.getFollowers();
            for (User f: followersList) {
                newFollowersList.add(f.getHandle());
            }
            for (User f: followingList) {
                newFollowingList.add(f.getHandle());
            }
            ApiUserObject user = new ApiUserObject(u.getId(),u.getEmail(),u.getHandle(),newFollowersList,newFollowingList);
            completedUsers.add(user);
            newFollowingList = new ArrayList<>();
            newFollowersList = new ArrayList<>();
        }
        return completedUsers;
    }

    @GetMapping("/users/{handle}")
    public ApiUserObject user(@PathVariable String handle) {
        User user = userService.getUserByHandle(handle);
        Set<User> followingList = user.getFollowing();
        Set<User> followersList = user.getFollowers();
        List<String> newFollowingList = new ArrayList<>();
        List<String> newFollowersList = new ArrayList<>();
        for (User u: followersList) {
            newFollowersList.add(u.getHandle());
        }
        for (User u: followingList) {
            newFollowingList.add(u.getHandle());
        }
        return new ApiUserObject(user.getId(),user.getHandle(),user.getEmail(), newFollowersList, newFollowingList);
    }

    @GetMapping("/users/{handle}/jibbers")
    public ArrayList<ApiJibberObject> userJibbers(@PathVariable String handle) {
        List<Jibber> jibbers = jibberService.findByHandle(handle);
        ArrayList<ApiJibberObject> completedJibbers = new ArrayList<>();
        for (Jibber j : jibbers){
            completedJibbers.add(new ApiJibberObject(j.getId(),j.getMessage(),j.getCreatedDate(),j.getUser().getHandle()));
        }
        return completedJibbers;
    }

    @GetMapping("/jibbers")
    public ArrayList<ApiJibberObject> jibbers() {
        List<Jibber> jibbers = jibberService.findAll();
        ArrayList<ApiJibberObject> completedJibbers = new ArrayList<>();
        for (Jibber j : jibbers){
            completedJibbers.add(new ApiJibberObject(j.getId(),j.getMessage(),j.getCreatedDate(),j.getUser().getHandle()));
        }
        return completedJibbers;
    }

    @GetMapping("/jibbers/{id}")
    public ArrayList<ApiJibberObject> jibbersId(@PathVariable int id) {
        List<Jibber> jibbers = jibberService.findByid(id);
        ArrayList<ApiJibberObject> completedJibbers = new ArrayList<>();
        for (Jibber j : jibbers){
            completedJibbers.add(new ApiJibberObject(j.getId(),j.getMessage(),j.getCreatedDate(),j.getUser().getHandle()));
        }
        return completedJibbers;
    }
}
