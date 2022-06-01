package com.beshanov.catchbadguy;

import com.beshanov.catchbadguy.configuration.SpringConfiguration;
import com.beshanov.catchbadguy.processing.DataScreenProcessor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class CommandLineRunner {

    public static void main(String[] args) throws IOException, ClientException, ApiException {
        Logger logger = Logger.getLogger(CommandLineRunner.class);
        long start = System.currentTimeMillis();
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        DataScreenProcessor processor = context.getBean("dataScreenProcessor", DataScreenProcessor.class);
        processor.startProcessing();
        long end = System.currentTimeMillis();
        logger.info("Execution time: " + (end - start) + " ms");
    }
}
