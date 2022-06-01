package com.beshanov.catchbadguy.configuration;

import com.beshanov.catchbadguy.auth.CredentialsAuth;
import com.beshanov.catchbadguy.auth.CredentialsReader;
import com.beshanov.catchbadguy.domain.SearchOptions;
import com.beshanov.catchbadguy.processing.strategies.AllGroupsSearchStrategy;
import com.beshanov.catchbadguy.processing.strategies.OneGroupSearchStrategy;
import com.beshanov.catchbadguy.processing.strategies.SearchStrategy;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import io.netty.util.internal.StringUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.beshanov.catchbadguy")
public class SpringConfiguration {
    @Bean
    public VkApiClient vkApiClient() {
        TransportClient transportClient = HttpTransportClient.getInstance();
        return new VkApiClient(transportClient);
    }

    @Bean
    public SearchStrategy searchStrategy() {
        SearchOptions searchOptions = SearchOptionsReader.getSearchOptions();
        String groupIdString = searchOptions.getGroupId();

        if (StringUtil.isNullOrEmpty(groupIdString)) {
            return new AllGroupsSearchStrategy();
        } else {
            return new OneGroupSearchStrategy();
        }
    }

    @Bean
    public UserActor userActor() {
        CredentialsAuth credentials = CredentialsReader.getCredentialsFromProperties();
        return new UserActor(credentials.getUserId(), credentials.getAccessToken());
    }
}
