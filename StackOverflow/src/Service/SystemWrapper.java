package Service;

import models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

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
    ConcurrentHashMap<String, List<String>>  keywordToQuestionMap = new ConcurrentHashMap<>();
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
        userToPostObjectMap.computeIfAbsent(userId, k -> new CopyOnWriteArrayList<>());
        if(!created){
            return new ErrorResponse("User already exists", "USER_ALREADY_EXISTS");
        }
        return new SuccessResponse("User registered", "SUCCESS", userService.getUser(userId));
    }

    public ResponseClass PostQuestion(String userid,String question){
        return PostQuestion(userid, question, utilService.extractTags(question));
    }

    public ResponseClass PostQuestion(String userid,String question, List<String> tags){
        //only valid user can post question/answer/comment
        if(!userService.userExists(userid)){throw new InvalidUserException("INVALID_USER","Invalid User");}
        Question postedQuestion = postQuestionService.postQuestion(userid, question,tags);
        userToPostObjectMap.computeIfAbsent(userid, k -> new CopyOnWriteArrayList<>()).add(postedQuestion.getQuestionId());

        for(String tag:tags){
            tagToQuestionMap.computeIfAbsent(tag, k -> new CopyOnWriteArrayList<>()).add(postedQuestion.getQuestionId());
        }

        indexQuestionKeywords(postedQuestion);

        postToAnswerMap.computeIfAbsent(postedQuestion.getQuestionId(), k -> new CopyOnWriteArrayList<>());
        questionToCommentMap.computeIfAbsent(postedQuestion.getQuestionId(), k -> new CopyOnWriteArrayList<>());

        return new SuccessResponse("Question Posted", "SUCCESS", postedQuestion);
    }


    public ResponseClass AnswerQuestion(String userid, String questionId, String answer){

        if(!userService.userExists(userid)){throw new InvalidUserException("INVALID_USER","Invalid User");}
        if(!postQuestionService.postExists(questionId)){throw new InvalidPostException("INVALID_POST_ID","Invalid Post");}
        Answer postedAnswer = postAnswerService.postAnswer(userid, answer,questionId);
        postToAnswerMap.computeIfAbsent(questionId, k -> new CopyOnWriteArrayList<>()).add(postedAnswer.getAnswerId());
        userToPostObjectMap.computeIfAbsent(userid, k -> new CopyOnWriteArrayList<>()).add(postedAnswer.getAnswerId());
        answerToCommentMap.computeIfAbsent(postedAnswer.getAnswerId(), k -> new CopyOnWriteArrayList<>());
        return new SuccessResponse("Answer Posted", "SUCCESS", postedAnswer);
    }

    public void CommentOnPost(String userId, String comment ,String parentId, boolean isCommentOnQuestion){
        if(!userService.userExists(userId)){throw new InvalidUserException("INVALID_USER","Invalid User");}

        if(isCommentOnQuestion){
            if(!postQuestionService.postExists(parentId)){throw new InvalidPostException("INVALID_POST_ID","Invalid Post");}
            //call comment on question
            Comment comment1 = postCommentService.postComment(userId, comment, parentId);
            addCommentToQuestion(parentId,comment1.getCommentId());
            userToPostObjectMap.computeIfAbsent(userId, k -> new CopyOnWriteArrayList<>()).add(comment1.getCommentId());


        }else{
            if(!postAnswerService.answerExists(parentId)){throw new InvalidPostException("INVALID_ANSWER_ID","Invalid Answer");}
            //call comment on answer
            Comment comment1 = postCommentService.postComment(userId, comment, parentId);
            addCommentToAnswer(parentId,comment1.getCommentId());
            userToPostObjectMap.computeIfAbsent(userId, k -> new CopyOnWriteArrayList<>()).add(comment1.getCommentId());

        }

    }

    private void addCommentToAnswer(String answerId,String commentId) {

        answerToCommentMap.computeIfAbsent(answerId, k -> new CopyOnWriteArrayList<>()).add(commentId);

    }

    private void addCommentToQuestion (String postId,String questionId){
        questionToCommentMap.computeIfAbsent(postId, k -> new CopyOnWriteArrayList<>()).add(questionId);
    }

    public ResponseClass getQuestionsByUser(String userId){

        if(!userService.userExists(userId)){throw new InvalidUserException("INVALID_USER","Invalid User");}
        List<String> postids = userToPostObjectMap.getOrDefault(userId, List.of());
        List<Question> questions = new ArrayList<>();
        for(String postid:postids){
            if(postQuestionService.postExists(postid)){
                questions.add(postQuestionService.getQuestion(postid));
            }
        }
        return new SuccessResponse("Questions fetched", "SUCCESS",questions);
    }
    public ResponseClass getAnswerByUser(String  userId){
        if(!userService.userExists(userId)){throw new InvalidUserException("INVALID_USER","Invalid User");}
        return getResponseClass(userId, userToPostObjectMap);

    }


    public  ResponseClass upVote(String userId,String id,Boolean isQuestionUpvote){
        if(!userService.userExists(userId)){throw new InvalidUserException("INVALID_USER","Invalid User");}
        if(!postQuestionService.postExists(id) && !postAnswerService.answerExists(id)){throw new InvalidPostException("INVALID_POST_ID","Invalid Post");}

        if(isQuestionUpvote){

            if(postQuestionService.upVote(userId, id)){
                return new SuccessResponse("Question Upvoted", "SUCCESS");
            }else{
                return new ErrorResponse("Cannot upvote this question", "INVALID_VOTE");
            }

        }else{
            if(postAnswerService.upVote(userId, id)){
                return new SuccessResponse("Answer Upvoted", "SUCCESS");
            }else{
                return new ErrorResponse("Cannot upvote this answer", "INVALID_VOTE");
            }
        }
    }

    public ResponseClass getQuestionsByTag(String userid, String tag){
        if(!userService.userExists(userid)){throw new InvalidUserException("INVALID_USER","Invalid User");}
        List<String> questionIds = tagToQuestionMap.getOrDefault(tag, List.of());
        List<Question> questions = new ArrayList<>();
        for(String questionId:questionIds){
            if(postQuestionService.postExists(questionId)){
                questions.add(postQuestionService.getQuestion(questionId));
            }
        }
        return new SuccessResponse("Questions fetched", "SUCCESS",questions);
    }

    private ResponseClass getResponseClass(String userId, ConcurrentHashMap<String, List<String>> userToPostObjectMap) {
        List<String> postids = userToPostObjectMap.getOrDefault(userId, List.of());
        List<Answer> answers = new ArrayList<>();
        for(String posted :postids){
            if(postAnswerService.answerExists(posted)){
                answers.add(postAnswerService.getAnswer(posted));
            }
        }
        return new SuccessResponse("Answers fetched", "SUCCESS",answers);
    }

    public ResponseClass getAnswersByQuestion(String questionId){
        if(!postQuestionService.postExists(questionId)){throw new InvalidPostException("INVALID_POST_ID","Invalid Post");}
        return getResponseClass(questionId, postToAnswerMap);
    }

    public ResponseClass getCommentsByQuestion(String questionId){
        if(!postQuestionService.postExists(questionId)){throw new InvalidPostException("INVALID_POST_ID","Invalid Post");}
        return getCommentsResponse(questionId, questionToCommentMap);
    }

    public ResponseClass getCommentsByAnswer(String answerId){
        if(!postAnswerService.answerExists(answerId)){throw new InvalidPostException("INVALID_ANSWER_ID","Invalid Answer");}
        return getCommentsResponse(answerId, answerToCommentMap);
    }

    public ResponseClass searchQuestionsByKeyword(String userId, String keyword){
        if(!userService.userExists(userId)){throw new InvalidUserException("INVALID_USER","Invalid User");}
        if(keyword == null || keyword.isBlank()){
            return new SuccessResponse("Questions fetched", "SUCCESS", List.of());
        }

        List<String> questionIds = keywordToQuestionMap.getOrDefault(normalizeKeyword(keyword), List.of());
        List<Question> questions = new ArrayList<>();
        for(String questionId: questionIds){
            if(postQuestionService.postExists(questionId)){
                questions.add(postQuestionService.getQuestion(questionId));
            }
        }
        return new SuccessResponse("Questions fetched", "SUCCESS", questions);
    }

    public ResponseClass getQuestionsByUserProfile(String requestingUserId, String profileUserId){
        if(!userService.userExists(requestingUserId)){throw new InvalidUserException("INVALID_USER","Invalid User");}
        if(!userService.userExists(profileUserId)){throw new InvalidUserException("INVALID_PROFILE_USER","Invalid Profile User");}
        return getQuestionsByUser(profileUserId);
    }

    private ResponseClass getCommentsResponse(String parentId, ConcurrentHashMap<String, List<String>> parentToCommentMap) {
        List<String> commentIds = parentToCommentMap.getOrDefault(parentId, List.of());
        List<Comment> comments = new ArrayList<>();
        for(String commentId: commentIds){
            Comment comment = postCommentService.getComment(commentId);
            if(comment != null){
                comments.add(comment);
            }
        }
        return new SuccessResponse("Comments fetched", "SUCCESS", comments);
    }

    private void indexQuestionKeywords(Question question){
        Set<String> keywords = ConcurrentHashMap.newKeySet();
        for(String token: question.getQuestion().split("\\W+")){
            String keyword = normalizeKeyword(token);
            if(!keyword.isBlank()){
                keywords.add(keyword);
            }
        }

        for(String keyword: keywords){
            keywordToQuestionMap.computeIfAbsent(keyword, k -> new CopyOnWriteArrayList<>()).add(question.getQuestionId());
        }
    }

    private String normalizeKeyword(String keyword){
        return keyword.toLowerCase().trim();
    }




}
