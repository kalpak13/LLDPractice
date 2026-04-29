package Dao;

import models.Comment;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class CommentDao {
    ConcurrentHashMap<String, Comment> commentMap = new ConcurrentHashMap<>();

    public Comment getComment(String commentId){
        return commentMap.get(commentId);
    }
    public Boolean addComment(Comment comment){
          if(comment == null || commentExists(comment.getCommentId()))
              return false;
          commentMap.put(comment.getCommentId(), comment);
          return true;
    }
    public Boolean commentExists(String commentId){
        return commentMap.containsKey(commentId);
    }
}
