package com.beshanov.catchbadguy.service;

import com.beshanov.catchbadguy.service.factory.VkClientFactory;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.groups.responses.GetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    private final VkClientFactory clientFactory;

    @Autowired
    public GroupService(VkClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    public List<Integer> getAllGroups(UserActor actor) throws ClientException, ApiException {
        GetResponse response = clientFactory.getClient().groups().get(actor).execute();
        return response.getItems();
    }
}
