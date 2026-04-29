package Dao;

import models.Answer;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class AnswerDao {
    ConcurrentHashMap<String, Answer> answerMap = new ConcurrentHashMap<>();

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

    public Boolean upVote(String id){
        answerMap.get(id).upVote();
        return true;
    }
}
