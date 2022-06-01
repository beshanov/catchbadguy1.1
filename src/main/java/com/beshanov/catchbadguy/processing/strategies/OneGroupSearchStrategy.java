package com.beshanov.catchbadguy.processing.strategies;

import com.beshanov.catchbadguy.domain.Comment;
import com.beshanov.catchbadguy.domain.SearchOptions;
import com.beshanov.catchbadguy.output.OutputAdapterService;
import com.beshanov.catchbadguy.service.WallPostService;
import com.beshanov.catchbadguy.utils.KeyWordsHelper;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.base.CommentsInfo;
import com.vk.api.sdk.objects.wall.WallComment;
import com.vk.api.sdk.objects.wall.WallpostFull;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OneGroupSearchStrategy implements SearchStrategy {

  @Autowired
  private UserActor actor;

  @Autowired
  private WallPostService wallPostService;

  @Autowired
  private OutputAdapterService outputAdapterService;

  @Override
  public void start(SearchOptions searchOptions) throws ClientException, ApiException {
    Logger logger = Logger.getLogger(OneGroupSearchStrategy.class);

    Integer intId = wallPostService.getIntIdByShortLink(actor, searchOptions.getGroupId());
    Integer postCount = searchOptions.getPostCount();

    List<WallpostFull> postList = wallPostService.getWallPostsByGroupId(actor,
        searchOptions.getGroupId(), postCount);
    long value = 0;
    outputAdapterService.startHtmlTableBuilding();
    for (WallpostFull post : postList) {
      CommentsInfo commentsInfo = post.getComments();
      if (commentsInfo.getCount() > 0) {
        List<WallComment> commentList = wallPostService.getWallCommentsByPost(actor, post);
        for (WallComment comment : commentList) {
          String coincidence = KeyWordsHelper.checkText(
              new String(comment.getText().getBytes(), StandardCharsets.UTF_8));
          logger.trace("Comment text: " + comment.getText());
          if (coincidence != null) {
            logger.info("Есть совпадение: " + coincidence + ": " + comment.getText());
            Comment commentEntity = new Comment();
            commentEntity.setId(comment.getId());
            commentEntity.setDate(comment.getDate());
            commentEntity.setText(comment.getText());
            commentEntity.setUserId(comment.getFromId());
            commentEntity.setUserUrl("https://vk.com/id" + comment.getFromId());
            commentEntity.setPostUrl(
                "https://vk.com/wall-" + intId + "_" + post.getId() + "?reply=" + comment.getId());
            System.out.println(value + ":::" + comment + "\n\n\n");
            outputAdapterService.writeEntry(coincidence, commentEntity);
          }
        }
      }
    }

    outputAdapterService.endHtmlTableBuilding();
    System.out.println(value);
  }
}
