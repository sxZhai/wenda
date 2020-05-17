package wenda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wenda.async.EventModel;
import wenda.async.EventProducer;
import wenda.async.EventType;
import wenda.model.Comment;
import wenda.model.EntityType;
import wenda.model.HostHolder;
import wenda.service.CommentService;
import wenda.service.LikeService;
import wenda.utils.WendaUtil;

@Controller
public class LikeController {
    @Autowired
    LikeService likeService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    CommentService commentService;
    @Autowired
    EventProducer eventProducer;

    @PostMapping(value = "/like")
    @ResponseBody
    public String like(int commentId) {
        if (hostHolder.getUser() == null) {
            return WendaUtil.getJsonString(999);
        }
        // 获取点赞的那条评论
        Comment comment = commentService.getCommentById(commentId);
        // 异步队列发送私信给被赞人
        eventProducer.fireEvent(new EventModel(EventType.LIKE)
                .setActorId(hostHolder.getUser().getId()).setEntityId(commentId)
                .setEntityType(EntityType.ENTITY_COMMENT).setEntityOwnerId(comment.getUserId())
                .setExts("questionId", String.valueOf(comment.getEntityId())));

        // 返回前端点赞数
        long likeCount = likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);
        return WendaUtil.getJsonString(0, String.valueOf(likeCount));
    }

    @PostMapping(value = "/dislike")
    @ResponseBody
    public String dislike(int commentId) {
        if (hostHolder.getUser() == null) {
            return WendaUtil.getJsonString(999);
        }
        long likeCount = likeService.disLike(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);
        return WendaUtil.getJsonString(0, String.valueOf(likeCount));
    }
}