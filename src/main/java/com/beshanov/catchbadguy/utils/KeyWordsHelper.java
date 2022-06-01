package com.beshanov.catchbadguy.utils;

import org.apache.log4j.Logger;

import java.io.FileInputStream;

public class KeyWordsHelper {
    private static String[] keyWords;

    static {
        Logger logger = Logger.getLogger(KeyWordsHelper.class);

        String keyWordsString = null;

        try {
            logger.info("Начало чтения файла ключевых слов");
            FileInputStream fileInputStream = new FileInputStream("config/keywords.txt");
            keyWordsString = new String(fileInputStream.readAllBytes());
            logger.info("Чтение файла ключевых слов закончилось успешно");
            logger.debug("Ключевые слова: " + keyWordsString);
        } catch (Exception e) {
            logger.error("Key words parsing error! Check config/keywords.txt file", e);
        }

        if (keyWordsString != null) {
            keyWords = keyWordsString.split(";");
        }
    }

    public static String checkText(String text) {
        for (String word : keyWords) {
            if (text.toLowerCase().contains(word.toLowerCase())) {
                return word;
            }
        }
        return null;
    }
}
