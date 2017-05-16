/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softviewerfx.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import softviewerfx.models.LineBean;

/**
 *
 * @author roger
 */
public class FileParser {

    private final BufferedReader reader;
    private final ObservableList<LineBean> fileLines;
    private final File file;

    public FileParser(File file) throws FileNotFoundException {
        this.file = file;
        this.reader = new BufferedReader(new FileReader(file));
        this.fileLines = FXCollections.observableArrayList();

    }

    public ObservableList<LineBean> getFileLines() {
        String thisLineValue;
        int thisLinexindex = 0;

        try {
            while ((thisLineValue = reader.readLine()) != null) {
                this.fileLines.add(new LineBean(
                        new SimpleStringProperty(String.valueOf(thisLinexindex)),
                        new SimpleStringProperty(thisLineValue)
                )
                );
                thisLinexindex++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileLines;
    }



}
