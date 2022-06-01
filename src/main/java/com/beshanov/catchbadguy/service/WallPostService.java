package com.beshanov.catchbadguy.service;

import com.beshanov.catchbadguy.service.factory.VkClientFactory;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.groups.responses.GetByIdObjectLegacyResponse;
import com.vk.api.sdk.objects.wall.WallComment;
import com.vk.api.sdk.objects.wall.WallpostFull;
import com.vk.api.sdk.objects.wall.responses.GetCommentsResponse;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WallPostService {

  private final VkClientFactory clientFactory;

  @Autowired
  public WallPostService(VkClientFactory clientFactory) {
    this.clientFactory = clientFactory;
  }

  public List<WallpostFull> getWallPostsByGroupId(UserActor actor, String groupIdString,
      Integer postCount) throws ClientException, ApiException {
    Integer groupId = getIntIdByShortLink(actor, groupIdString);
    GetResponse posts = clientFactory.getClient().wall().get(actor).ownerId(-groupId)
        .count(postCount).execute();
    return posts.getItems();
  }

  public List<WallComment> getWallCommentsByPost(UserActor actor, WallpostFull post)
      throws ClientException, ApiException {
    GetCommentsResponse commentsResponse = clientFactory.getClient().wall().getComments(actor)
        .ownerId(post.getOwnerId()).postId(post.getId()).execute();
    return commentsResponse.getItems();
  }

  //changed
  public Integer getIntIdByShortLink(UserActor actor, String shortLink)
      throws ClientException, ApiException {
    List<GetByIdObjectLegacyResponse> groupList = clientFactory.getClient().groups()
        .getByIdObjectLegacy(actor)
        .groupId(shortLink).execute();
    return groupList.get(0).getId();
  }
}