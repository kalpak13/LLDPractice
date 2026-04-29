package Service;

import models.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import Exception.InvalidUserException;
import Exception.InvalidPostException;

public class SystemWrapper {

    /*
    postid == questionid
      mappings:
        userid -> postobjectid( postobject can be of type post, answer or comment)

        postid ->answerid

        postid -> commenid
        answerid ->commentid


     */

    HashMap<String, List<String>> userToPostObjectMap = new HashMap<>();
    HashMap<String, List<String>>  postToAnswerMap = new HashMap<>();
    HashMap<String, List<String>>  answerToCommentMap = new HashMap<>();
    HashMap<String, List<String>>  questionToCommentMap = new HashMap<>();
    UserService userService;
    PostQuestionService postQuestionService ;
    PostAnswerService postAnswerService;
    PostCommentService postCommentService;

    public SystemWrapper(){
        this.userService = new UserService();
        this.postQuestionService = new PostQuestionService();
        this.postAnswerService = new PostAnswerService();
        this.postCommentService = new PostCommentService();
    }

    public ResponseClass PostQuestion(String userid,String question){
        //only valid user can post question/answer/comment
        if(!userService.userExists(userid)){throw new InvalidUserException("Invalid User","INVALID_USER");}
        Question postedQuestion = postQuestionService.postQuestion(userid, question);
        userToPostObjectMap.get(userid).add(postedQuestion.getQuestionId());
        return new SuccessResponse("Question Posted", "SUCCESS");

    }

    public ResponseClass AnswerQuestion(String userid, String questionId, String answer){

        if(!postQuestionService.postExists(questionId)){throw new InvalidPostException("Invalid Post","INVALID_POST_ID");}
        Answer postedAnswer = postAnswerService.postAnswer(userid, answer, questionId);
        postToAnswerMap.get(questionId).add(postedAnswer.getAnswerId());
        userToPostObjectMap.get(userid).add(postedAnswer.getAnswerId());
        return new SuccessResponse("Answer Posted", "SUCCESS");
    }

    public void CommentOnPost(String userId, String comment ,String parentId, boolean isCommentOnQuestion){

        if(isCommentOnQuestion){
            if(!postQuestionService.postExists(parentId)){throw new InvalidPostException("Invalid Post","INVALID_POST_ID");}
            //call comment on question
            Comment comment1 = postCommentService.postComment(userId, comment, parentId);
            addCommentToQuestion(parentId,comment1.getCommentId());


        }else{
            if(!postAnswerService.answerExists(parentId)){throw new InvalidPostException("Invalid Post","INVALID_ANSWER_ID");}
            //call comment on answer
            Comment comment1 = postCommentService.postComment(userId, comment, parentId);
            addCommentToAnswer(parentId,comment1.getCommentId());

        }

    }

    private void addCommentToAnswer(String answerId,String commentId) {

        answerToCommentMap.get(answerId).add(commentId);

    }

    private void addCommentToQuestion (String postId,String questionId){
        questionToCommentMap.get(postId).add(questionId);
    }

    public ResponseClass getQuestionsByUser(String userId){

        List<String> postids = userToPostObjectMap.get(userId);
        List<Question> questions = new ArrayList<>();
        for(String postid:postids){
            if(postQuestionService.postExists(postid)){
                questions.add(postQuestionService.getQuestion(postid));
            }
        }
        return new SuccessResponse("Questions fetched", "SUCCESS",questions);
    }

    public ResponseClass getAnswersByQuestion(String questionId){
        List<String> answerIds = postToAnswerMap.get(questionId);
        List<Answer> answers = new ArrayList<>();
        for(String answerId:answerIds){
            if(postAnswerService.answerExists(answerId)){
                answers.add(postAnswerService.getAnswer(answerId));
            }
        }
        return new SuccessResponse("Answers fetched", "SUCCESS",answers);
    }


}
