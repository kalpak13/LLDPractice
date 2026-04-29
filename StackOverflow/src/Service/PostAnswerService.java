package Service;

import Dao.AnswerDao;
import models.Answer;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class PostAnswerService {

    AnswerDao answerDao;
    Map<String, Set<String>> answerToVotersMap = new ConcurrentHashMap<>();
    private final ReentrantLock lock = new ReentrantLock();
    public PostAnswerService(){
        this.answerDao = new AnswerDao();
    }

    public Answer postAnswer(String userId, String answer, String parentPostId){
        lock.lock();
        try{
            Answer answerObject = new Answer(userId,answer,parentPostId);
            answerDao.addAnswer(answerObject);
            return answerObject;
        }
        finally {
            lock.unlock();
        }
    }

    public Boolean answerExists(String answerId){
        return answerDao.answerExists(answerId);
    }

    public Answer getAnswer(String answerId){
        return answerDao.getAnswer(answerId);
    }

    public Boolean upVote(String userId, String id){
        if(!answerExists(id)) return false;
        if(getAnswer(id).getUserId().equals(userId)) return false;

       Set<String> voters = answerToVotersMap.computeIfAbsent(id, k -> ConcurrentHashMap.newKeySet());
       if(!voters.add(userId)){
           return false;
       }

       lock.lock();
       try{
           answerDao.upVote(id);
           return true;
       }
        finally {
            lock.unlock();
        }
    }
}
