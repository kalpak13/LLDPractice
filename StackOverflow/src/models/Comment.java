package models;

import java.util.UUID;
import Enum.PostObjectType;

public class Comment {

    String userId;
    String comment;
    String commentId;
    PostObjectType type;
    String parentId;

    public Comment(String userId, String comment, String parentId) {
        this.userId = userId;
        this.comment = comment;
        this.commentId = UUID.randomUUID().toString();
        this.parentId = parentId;
        this.type = PostObjectType.COMMENT;
    }
    public String getCommentId(){
        return commentId;
    }
    public String getComment(){
        return comment;
    }
}
