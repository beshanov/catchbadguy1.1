package com.beshanov.catchbadguy.output;

import com.beshanov.catchbadguy.domain.Comment;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;

@Component
public class OutputHtmlTableBuilder {
    private Logger logger = Logger.getLogger(OutputHtmlTableBuilder.class);
    private String css = "<style>table, th, td { border: 1px solid black;}</style>";

    public void startHtmlTableBuilding() {
        StringBuilder builder = new StringBuilder();
        builder.append("<!DOCTYPE html><html>")
                .append("<head>")
                .append("<title>")
                .append("CatchBadGuy Result")
                .append("</title>")
                .append(css)
                .append("</head>")
                .append("<body>")
                .append("<table>");

        File file = new File("output.html");
        try (FileWriter fr = new FileWriter(file, false);) {
            fr.write(builder.toString());
        } catch (IOException e) {
            logger.error("File writing error", e);
        }
    }

    public void endHtmlTableBuilding() {
        StringBuilder builder = new StringBuilder();
        builder.append("</table>")
                .append("</body>")
                .append("</html>");
        File file = new File("output.html");
        try (FileWriter fr = new FileWriter(file, true);) {
            fr.write(builder.toString());
        } catch (IOException e) {
            logger.error("File writing error", e);
        }
    }

    public void writeOutputEntry(String word, Comment comment) {
        StringBuilder builder = new StringBuilder();
        builder.append("<tr>")
                .append("<td>")
                .append(Instant.ofEpochSecond((comment.getDate())))
                .append("</td>")
                .append("<td>")
                .append(word)
                .append("</td>")
                .append("<td>")
                .append(comment.getText().replaceAll(word, "<span style=\"background-color:#edbe8e;\">" + word + "</span>"))
                .append("</td>")
                .append("<td>")
                .append(comment.getUserId())
                .append("</td>")
                .append("<td>")
                .append("<a href=\"" + comment.getPostUrl() + "\">" + "comment" + "</a>")
                .append("</td>")
                .append("<td>")
                .append("<a href=\"" + comment.getUserUrl() + "\">" + "user" + "</a>")
                .append("</td>");
        File file = new File("output.html");
        try (FileWriter fr = new FileWriter(file, true);) {
            fr.write(builder.toString());
        } catch (IOException e) {
            logger.error("File writing error", e);
        }
    }
}
