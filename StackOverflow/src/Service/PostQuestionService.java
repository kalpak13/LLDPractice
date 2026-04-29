package Service;

import Dao.QuestionDao;
import models.Post;
import models.Question;
import Exception.InvalidPostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class PostQuestionService {
    Map<String, Question> postMap = new HashMap<>();
    private final ReentrantLock lock = new ReentrantLock();

    QuestionDao questionDao;
    public PostQuestionService(){
        this.questionDao = new QuestionDao();
    }
    public Question postQuestion(String userid, String question, List<String> tags) {
        if(userid == null || question == null){throw new InvalidPostException("Invalid Post","INVALID_POST");}
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

    public Boolean upVote(String id){
        if(!postExists(id)){return false;}
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
