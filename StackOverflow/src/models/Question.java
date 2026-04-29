package models;
import Enum.PostObjectType;

import java.util.UUID;

public class Question extends PostObject {
    String question;
    String questionId;
    PostObjectType type = PostObjectType.QUESTION;
    public Question(String userId, String question) {
        this.userId = userId;
        this.question = question;
        this.questionId = UUID.randomUUID().toString();
    }

    public String getQuestionId(){
        return questionId;
    }
    public String getQuestion(){
        return question;
    }

}
