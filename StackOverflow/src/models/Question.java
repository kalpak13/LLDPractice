package models;
import Enum.PostObjectType;

import java.util.List;
import java.util.UUID;

public class Question extends PostObject {
    String question;
    String questionId;

    List<String> tags;
    public Question(String userId, String question) {
        this.userId = userId;
        this.question = question;
        this.questionId = UUID.randomUUID().toString();
        this.type = PostObjectType.QUESTION;
    }

    public String getQuestionId(){
        return questionId;
    }
    public String getQuestion(){
        return question;
    }
    public List<String> getTags(){
        return tags;
    }
    public void setTags(List<String> tags){
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId='" + questionId + '\'' +
                ", userId='" + userId + '\'' +
                ", question='" + question + '\'' +
                ", tags=" + tags +
                '}';
    }
}
