package Service;

import Dao.CommentDao;
import models.Comment;
public class PostCommentService {

    CommentDao commentDao;
    public PostCommentService(){
        this.commentDao = new CommentDao();
    }

   public Comment postComment(String userId, String comment, String parentAnswerId){
        Comment commentObject = new Comment(userId,comment,parentAnswerId);
        commentDao.addComment(commentObject);
        return commentObject;

   }
}
