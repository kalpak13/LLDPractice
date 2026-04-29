package models;

import java.util.List;
import Enum.PostObjectType;

public class Post {
    String postId;
    String userId;
    Question question;
    List<Answer> answers;
    String postType;

    public Post(Question question,String userId){
        this.postId = question.getQuestionId();
        this.userId = userId;
        this.question = question;
        this.postType = PostObjectType.QUESTION.toString();
    }

    public Boolean setQuestion(Question question){
        this.question = question;
        return true;
    }
}
