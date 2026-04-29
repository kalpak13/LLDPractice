package Dao;

import models.Question;

import java.util.HashMap;

public class QuestionDao {

    HashMap<String, Question> questionMap = new HashMap<>();

   public Question getQuestion(String questionId){
        if(!questionExists(questionId)){
            return null;
        }
        return questionMap.get(questionId);
    }
    public Boolean questionExists(String questionId){
        return questionMap.containsKey(questionId);
    }

    public Boolean addQuestion(Question question){
        if(questionExists(question.getQuestionId())){
            return false;
        }
        questionMap.put(question.getQuestionId(), question);
        return true;
    }
}
