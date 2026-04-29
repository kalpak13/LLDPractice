package Dao;

import models.User;


import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;


public class UserDao {

    ConcurrentHashMap<String, User> userMap = new ConcurrentHashMap<>();


    public User getUser(String userId){
        if(userExists(userId)){
            return userMap.get(userId);
        }
        return null;

    }

    public boolean userExists(String userId){
        return userMap.containsKey(userId);
    }

    public boolean addUser(User user){
        if(userExists(user.getUserId())){
            return false;
        }
        userMap.put(user.getUserId(), user);
        return true;
    }

}
