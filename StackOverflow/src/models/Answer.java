package models;

import java.util.UUID;
import Enum.PostObjectType;
public class Answer extends PostObject{
    String answer;
    String answerId;

    public Answer(String userId, String answer,String parentPostId) {
        this.userId = userId;
        this.answer = answer;
        this.parentPostId = parentPostId;
        this.answerId = UUID.randomUUID().toString();
        this.type = PostObjectType.ANSWER;
    }

    public String getAnswerId(){
        return answerId;
    }
    public String getAnswer(){
        return answer;
    }

}
