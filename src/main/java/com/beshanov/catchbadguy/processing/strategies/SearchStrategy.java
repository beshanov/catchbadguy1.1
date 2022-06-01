package com.beshanov.catchbadguy.processing.strategies;

import com.beshanov.catchbadguy.domain.SearchOptions;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

public interface SearchStrategy {
    void start(SearchOptions searchOptions) throws ClientException, ApiException;
}
