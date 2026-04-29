package Service;

import Dao.QuestionDao;
import models.Question;
import Exception.InvalidPostException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class PostQuestionService {
    Map<String, Question> postMap = new ConcurrentHashMap<>();
    Map<String, Set<String>> questionToVotersMap = new ConcurrentHashMap<>();
    private final ReentrantLock lock = new ReentrantLock();

    QuestionDao questionDao;
    public PostQuestionService(){
        this.questionDao = new QuestionDao();
    }
    public Question postQuestion(String userid, String question, List<String> tags) {
        if(userid == null || userid.isBlank() || question == null || question.isBlank()){
            throw new InvalidPostException("INVALID_POST", "Question cannot be empty");
        }
        lock.lock();
        try {
            Question questionObject = new Question(userid, question);
            questionObject.setTags(tags);
            postMap.put(questionObject.getQuestionId(), questionObject);
            questionDao.addQuestion(questionObject);
            return questionObject;
        }
        finally {
            lock.unlock();
        }
    }
    public Boolean postExists(String postId){
        return postMap.containsKey(postId);
    }
    public Question getQuestion(String postId){
        return postMap.get(postId);
    }

    public Boolean upVote(String userId, String id){
        if(!postExists(id)){return false;}
        if(postMap.get(id).getUserId().equals(userId)) {return false;}

        Set<String> voters = questionToVotersMap.computeIfAbsent(id, k -> ConcurrentHashMap.newKeySet());
        if(!voters.add(userId)){
            return false;
        }

        lock.lock();
        try{
            questionDao.upVote(id);
            return true;
        }
        finally {
            lock.unlock();
        }

    }
}
