package Service;

import Dao.QuestionDao;
import models.Post;
import models.Question;
import Exception.InvalidPostException;
import java.util.HashMap;
import java.util.Map;

public class PostQuestionService {
    Map<String, Question> postMap = new HashMap<>();

    QuestionDao questionDao;
    public PostQuestionService(){
        this.questionDao = new QuestionDao();
    }
    public Question postQuestion(String userid, String question) {
        if(!postExists(question)){throw new InvalidPostException("Invalid Post","INVALID_POST");}
        Question questionObject = new Question(userid,question);
        postMap.put(questionObject.getQuestionId(),questionObject);
        questionDao.addQuestion(questionObject);
        return questionObject;
    }
    public Boolean postExists(String postId){
        return postMap.containsKey(postId);
    }
    public Question getQuestion(String postId){
        return postMap.get(postId);
    }
}
