package Dao;

import models.Answer;

import java.util.HashMap;

public class AnswerDao {
    HashMap<String, Answer> answerMap = new HashMap<>();

    public Boolean answerExists(String answerId){
        return answerMap.containsKey(answerId);
    }
    public Boolean addAnswer(Answer answer){
        if(answer == null || answerExists(answer.getAnswerId()))
            return false;
        answerMap.put(answer.getAnswerId(), answer);
        return true;
    }
    public Answer getAnswer(String answerId){
        if(!answerExists(answerId)){
            return null;
        }
        return answerMap.get(answerId);
    }
}
