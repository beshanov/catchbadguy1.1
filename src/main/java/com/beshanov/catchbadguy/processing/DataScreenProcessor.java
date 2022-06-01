package com.beshanov.catchbadguy.processing;

import com.beshanov.catchbadguy.configuration.SearchOptionsReader;
import com.beshanov.catchbadguy.domain.SearchOptions;
import com.beshanov.catchbadguy.processing.strategies.SearchStrategy;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataScreenProcessor {

    @Autowired
    private SearchStrategy searchStrategy;

    public void startProcessing() throws ClientException, ApiException {
        SearchOptions searchOptions = SearchOptionsReader.getSearchOptions();
        searchStrategy.start(searchOptions);
    }
}
