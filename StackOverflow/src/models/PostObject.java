package models;

import java.util.List;
import Enum.PostObjectType;
public abstract class PostObject {
    String userId;
    String parentPostId;
    List<String> comments;
    PostObjectType type;

    public String getParentPostId(){
        return parentPostId;
    }
    public  String getPostType(){
        return type.toString();
    };
    public  String getUserId(){
        return userId;
    };

    public  List<String> getComments(){
        return comments;
    }


}
