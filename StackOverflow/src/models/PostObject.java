package models;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import Enum.PostObjectType;
public abstract class PostObject {
    String userId;
    String parentPostId;
    List<String> comments;
    PostObjectType type;
    AtomicInteger upvotes;

    public PostObject(){
        this.upvotes = new AtomicInteger(0);
    };
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
    public void upVote(){
        this.upvotes.incrementAndGet();
    }


}
