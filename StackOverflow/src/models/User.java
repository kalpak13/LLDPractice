package models;

public class User {
    String userId;
    String userName;

    public String getUserId(){
        return userId;
    }
    public String getUserName(){
        return userName;
    }
    public User(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
