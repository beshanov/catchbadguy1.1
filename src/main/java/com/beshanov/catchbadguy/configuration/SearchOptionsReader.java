package com.beshanov.catchbadguy.configuration;

import com.beshanov.catchbadguy.domain.SearchOptions;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.util.Properties;

public class SearchOptionsReader {
    public static SearchOptions getSearchOptions() {
        Logger logger = Logger.getLogger(SearchOptions.class);

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("config/input.properties"));
        } catch (Exception e) {
            logger.error("Input reading error!", e);
        }

        String groupId = properties.getProperty("group_id");
        String postCount = properties.getProperty("post_count");
        SearchOptions searchOptions = new SearchOptions();
        searchOptions.setGroupId(groupId);
        searchOptions.setPostCount(Integer.valueOf(postCount));
        return searchOptions;
    }
}
