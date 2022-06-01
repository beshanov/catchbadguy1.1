package com.beshanov.catchbadguy.processing.strategies;

import com.beshanov.catchbadguy.domain.Comment;
import com.beshanov.catchbadguy.domain.SearchOptions;
import com.beshanov.catchbadguy.output.OutputAdapterService;
import com.beshanov.catchbadguy.service.GroupService;
import com.beshanov.catchbadguy.utils.KeyWordsHelper;
import com.beshanov.catchbadguy.service.WallPostService;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.base.CommentsInfo;
import com.vk.api.sdk.objects.wall.WallComment;
import com.vk.api.sdk.objects.wall.WallpostFull;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class AllGroupsSearchStrategy implements SearchStrategy {

    @Autowired
    private UserActor actor;

    @Autowired
    private WallPostService wallPostService;

    @Autowired
    private OutputAdapterService outputAdapterService;

    @Autowired
    private GroupService groupService;
    private Logger logger = Logger.getLogger(AllGroupsSearchStrategy.class);
    private static long commentsRqNumber;

    @Override
    public void start(SearchOptions searchOptions) throws ClientException, ApiException {
        List<Integer> allGroups = groupService.getAllGroups(actor);
        System.out.println("Число групп, по которым будет осуществляться поиск: " + allGroups.size());
        outputAdapterService.startHtmlTableBuilding();
        for (Integer groupId : allGroups) {
            Integer postCount = searchOptions.getPostCount();

            System.out.println("Запрос постов по id группы: " + groupId);
            List<WallpostFull> postList = wallPostService.getWallPostsByGroupId(actor, String.valueOf(groupId), postCount);

            for (WallpostFull post : postList) {
                processWallPost(groupId, post);
            }
        }
        outputAdapterService.endHtmlTableBuilding();
    }

    private void processWallPost(Integer groupId, WallpostFull post) throws ClientException, ApiException {
        CommentsInfo commentsInfo = post.getComments();
        if (commentsInfo.getCount() > 0) {
            List<WallComment> commentList = wallPostService.getWallCommentsByPost(actor, post);
            for (WallComment comment : commentList) {
                String commentText = new String(comment.getText().getBytes(), StandardCharsets.UTF_8);
                String coincidence = KeyWordsHelper.checkText(commentText);
                logger.trace("Comment text: " + comment.getText());
                if (coincidence != null) {
                    logger.info("Есть совпадение: " + coincidence + ": " + comment.getText());
                    Comment commentEntity = buildCommentEntity(groupId, post, comment);
                    outputAdapterService.writeEntry(coincidence, commentEntity);
                }
            }
        }
    }

    private Comment buildCommentEntity(Integer groupId, WallpostFull post, WallComment comment) {
        Comment commentEntity = new Comment();
        commentEntity.setId(comment.getId());
        commentEntity.setDate(comment.getDate());
        commentEntity.setText(comment.getText());
        commentEntity.setUserId(comment.getFromId());
        commentEntity.setUserUrl("https://vk.com/id" + comment.getFromId());
        commentEntity.setPostUrl("https://vk.com/wall-" + groupId + "_" + post.getId() + "?reply=" + comment.getId());
        return commentEntity;
    }
}
