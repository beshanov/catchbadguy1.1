package com.beshanov.catchbadguy.output;

import com.beshanov.catchbadguy.domain.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutputAdapterService {

    private OutputHtmlTableBuilder builder;

    @Autowired
    public OutputAdapterService(OutputHtmlTableBuilder builder) {
        this.builder = builder;
    }

    public void writeEntry(String word, Comment comment) {
        builder.writeOutputEntry(word, comment);
    }

    public void startHtmlTableBuilding()
    {
        builder.startHtmlTableBuilding();
    }

    public void endHtmlTableBuilding() {
        builder.endHtmlTableBuilding();
    }
}
