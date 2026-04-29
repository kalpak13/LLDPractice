package Service;

import Dao.AnswerDao;
import models.Answer;

public class PostAnswerService {

    AnswerDao answerDao;
    public PostAnswerService(){
        this.answerDao = new AnswerDao();
    }

    public Answer postAnswer(String userId, String answer, String parentPostId){
        Answer answerObject = new Answer(userId,answer,parentPostId);
        answerDao.addAnswer(answerObject);
        return answerObject;
    }

    public Boolean answerExists(String answerId){
        return answerDao.answerExists(answerId);
    }

    public Answer getAnswer(String answerId){
        return answerDao.getAnswer(answerId);
    }
}
