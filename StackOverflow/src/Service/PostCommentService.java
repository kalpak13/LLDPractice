package Service;

import Dao.CommentDao;
import models.Comment;

import java.util.concurrent.locks.ReentrantLock;

public class PostCommentService {

    CommentDao commentDao;
    private final ReentrantLock lock = new ReentrantLock();
    public PostCommentService(){
        this.commentDao = new CommentDao();
    }

   public Comment postComment(String userId, String comment, String parentAnswerId){
        if(comment == null || comment.isBlank()){
            throw new IllegalArgumentException("Comment cannot be empty");
        }
        lock.lock();
        try{
            Comment commentObject = new Comment(userId,comment,parentAnswerId);
            commentDao.addComment(commentObject);
            return commentObject;
        }
        finally {
            lock.unlock();
        }
   }

   public Comment getComment(String commentId){
        return commentDao.getComment(commentId);
   }
}
