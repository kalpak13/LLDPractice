package Service;

import Dao.UserDao;
import models.User;

public class UserService {

    UserDao userDao;
    public UserService(){
        userDao = new UserDao();
    }

    public User getUser(String userId){
        return userDao.getUser(userId);
    }

    public boolean addUser(User user){
        return userDao.addUser(user);
    }

    public boolean userExists(String userId){
        return userDao.userExists(userId);
    }
}
