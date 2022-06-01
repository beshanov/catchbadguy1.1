package com.beshanov.catchbadguy.auth;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CredentialsReader {
    public static CredentialsAuth getCredentialsFromProperties() {
        Logger logger = Logger.getGlobal();

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("config/input.properties"));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Input reading error!", e);
        }
        String appId = properties.getProperty("app_id");
        Integer userId = Integer.valueOf(properties.getProperty("user_id"));
        String accessToken = properties.getProperty("access_token");


        CredentialsAuth credentialsAuth = new CredentialsAuth();
        credentialsAuth.setAppId(appId);
        credentialsAuth.setUserId(userId);
        credentialsAuth.setAccessToken(accessToken);

        return credentialsAuth;
    }
}
