package Service;

import models.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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

    ConcurrentHashMap<String, List<String>> userToPostObjectMap = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, List<String>>  postToAnswerMap = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, List<String>>  answerToCommentMap = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, List<String>>  questionToCommentMap = new ConcurrentHashMap<>();
    ConcurrentHashMap<String, List<String>>  tagToQuestionMap = new ConcurrentHashMap<>();
    UserService userService;
    PostQuestionService postQuestionService ;
    PostAnswerService postAnswerService;
    PostCommentService postCommentService;
    UtilService utilService;

    public SystemWrapper(){
        this.userService = new UserService();
        this.postQuestionService = new PostQuestionService();
        this.postAnswerService = new PostAnswerService();
        this.postCommentService = new PostCommentService();
        this.utilService = new UtilService();
    }

    public ResponseClass RegisterUser(String userId, String userName){
        boolean created = userService.addUser(new User(userId, userName));
        userToPostObjectMap.computeIfAbsent(userId, k -> new ArrayList<>());
        if(!created){
            return new ErrorResponse("User already exists", "USER_ALREADY_EXISTS");
        }
        return new SuccessResponse("User registered", "SUCCESS", userService.getUser(userId));
    }

    public ResponseClass PostQuestion(String userid,String question){
        //only valid user can post question/answer/comment
        if(!userService.userExists(userid)){throw new InvalidUserException("Invalid User","INVALID_USER");}
        List<String>tags = utilService.extractTags(question);
        Question postedQuestion = postQuestionService.postQuestion(userid, question,tags);
        userToPostObjectMap.computeIfAbsent(userid, k -> new ArrayList<>()).add(postedQuestion.getQuestionId());

        for(String tag:tags){
            tagToQuestionMap.computeIfAbsent(tag, k -> new ArrayList<>()).add(postedQuestion.getQuestionId());
        }

        postToAnswerMap.computeIfAbsent(postedQuestion.getQuestionId(), k -> new ArrayList<>());
        questionToCommentMap.computeIfAbsent(postedQuestion.getQuestionId(), k -> new ArrayList<>());

        return new SuccessResponse("Question Posted", "SUCCESS", postedQuestion);
    }


    public ResponseClass AnswerQuestion(String userid, String questionId, String answer){

        if(!userService.userExists(userid)){throw new InvalidUserException("Invalid User","INVALID_USER");}
        if(!postQuestionService.postExists(questionId)){throw new InvalidPostException("Invalid Post","INVALID_POST_ID");}
        Answer postedAnswer = postAnswerService.postAnswer(userid, answer,questionId);
        postToAnswerMap.computeIfAbsent(questionId, k -> new ArrayList<>()).add(postedAnswer.getAnswerId());
        userToPostObjectMap.computeIfAbsent(userid, k -> new ArrayList<>()).add(postedAnswer.getAnswerId());
        answerToCommentMap.computeIfAbsent(postedAnswer.getAnswerId(), k -> new ArrayList<>());
        return new SuccessResponse("Answer Posted", "SUCCESS", postedAnswer);
    }

    public void CommentOnPost(String userId, String comment ,String parentId, boolean isCommentOnQuestion){
        if(!userService.userExists(userId)){throw new InvalidUserException("Invalid User","INVALID_USER");}

        if(isCommentOnQuestion){
            if(!postQuestionService.postExists(parentId)){throw new InvalidPostException("Invalid Post","INVALID_POST_ID");}
            //call comment on question
            Comment comment1 = postCommentService.postComment(userId, comment, parentId);
            addCommentToQuestion(parentId,comment1.getCommentId());
            userToPostObjectMap.computeIfAbsent(userId, k -> new ArrayList<>()).add(comment1.getCommentId());


        }else{
            if(!postAnswerService.answerExists(parentId)){throw new InvalidPostException("Invalid Post","INVALID_ANSWER_ID");}
            //call comment on answer
            Comment comment1 = postCommentService.postComment(userId, comment, parentId);
            addCommentToAnswer(parentId,comment1.getCommentId());
            userToPostObjectMap.computeIfAbsent(userId, k -> new ArrayList<>()).add(comment1.getCommentId());

        }

    }

    private void addCommentToAnswer(String answerId,String commentId) {

        answerToCommentMap.computeIfAbsent(answerId, k -> new ArrayList<>()).add(commentId);

    }

    private void addCommentToQuestion (String postId,String questionId){
        questionToCommentMap.computeIfAbsent(postId, k -> new ArrayList<>()).add(questionId);
    }

    public ResponseClass getQuestionsByUser(String userId){

        if(!userService.userExists(userId)){throw new InvalidUserException("Invalid User","INVALID_USER");}
        List<String> postids = userToPostObjectMap.getOrDefault(userId, new ArrayList<>());
        List<Question> questions = new ArrayList<>();
        for(String postid:postids){
            if(postQuestionService.postExists(postid)){
                questions.add(postQuestionService.getQuestion(postid));
            }
        }
        return new SuccessResponse("Questions fetched", "SUCCESS",questions);
    }
    public ResponseClass getAnswerByUser(String  userId){
        if(!userService.userExists(userId)){throw new InvalidUserException("Invalid User","INVALID_USER");}
        return getResponseClass(userId, userToPostObjectMap);

    }


    public  ResponseClass upVote(String userId,String id,Boolean isQuestionUpvote){
        if(!userService.userExists(userId)){throw new InvalidUserException("Invalid User","INVALID_USER");}
        if(!postQuestionService.postExists(id) && !postAnswerService.answerExists(id)){throw new InvalidPostException("Invalid Post","INVALID_POST_ID");}

        if(isQuestionUpvote){

            if(postQuestionService.upVote(id)){
                return new SuccessResponse("Question Upvoted", "SUCCESS");
            }else{
                return new ErrorResponse("Something went wrong , try again later", "SYSTEM_ERROR");
            }

        }else{
            if(postAnswerService.upVote(id)){
                return new SuccessResponse("Answer Upvoted", "SUCCESS");
            }else{
                return new ErrorResponse("Something went wrong , try again later", "SYSTEM_ERROR");
            }
        }
    }

    public ResponseClass getQuestionsByTag(String userid, String tag){
        if(!userService.userExists(userid)){throw new InvalidUserException("Invalid User","INVALID_USER");}
        List<String> questionIds = tagToQuestionMap.getOrDefault(tag, new ArrayList<>());
        List<Question> questions = new ArrayList<>();
        for(String questionId:questionIds){
            if(postQuestionService.postExists(questionId)){
                questions.add(postQuestionService.getQuestion(questionId));
            }
        }
        return new SuccessResponse("Questions fetched", "SUCCESS",questions);
    }

    private ResponseClass getResponseClass(String userId, ConcurrentHashMap<String, List<String>> userToPostObjectMap) {
        List<String> postids = userToPostObjectMap.getOrDefault(userId, new ArrayList<>());
        List<Answer> answers = new ArrayList<>();
        for(String posted :postids){
            if(postAnswerService.answerExists(posted)){
                answers.add(postAnswerService.getAnswer(posted));
            }
        }
        return new SuccessResponse("Answers fetched", "SUCCESS",answers);
    }




}
