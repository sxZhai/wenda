package wenda.controller;

import org.attoparser.trace.MarkupTraceEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import wenda.model.Comment;
import wenda.model.EntityType;
import wenda.model.HostHolder;
import wenda.service.CommentService;
import wenda.service.QuestionService;
import wenda.utils.WendaUtil;

import java.util.Date;

@Controller
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    @Autowired
    HostHolder hostHolder;
    @Autowired
    CommentService commentService;
    @Autowired
    QuestionService questionService;
//    @Autowired
//    EventProducer eventProducer;

    @PostMapping(value = {"/addComment"})
    public String addComment(int questionId, String content) {
        try {
            // 增加question的评论
            Comment comment = new Comment();
            comment.setContent(content);
            if (hostHolder.getUser() != null) {
                comment.setUserId(hostHolder.getUser().getId());
            } else {
                comment.setUserId(WendaUtil.ANONYMOUS_USERID);
            }
            comment.setCreatedDate(new Date());
            comment.setEntityType(EntityType.ENTITY_QUESTION);
            comment.setEntityId(questionId);
            commentService.addComment(comment);
//            // 更新question的评论数
            int count = commentService.getCommentCount(comment.getEntityId(), comment.getEntityType());
            questionService.updateCommentCount(comment.getEntityId(), count);
//            // 推送异步事件
//            eventProducer.fireEvent(new EventModel(MarkupTraceEvent.EventType.COMMENT).setActorId(comment.getUserId()).setEntityId(questionId));
        } catch (Exception e) {
            logger.error("增加评论失败" + e.getMessage());
        }
        return "redirect:/question/" + questionId;
    }
}