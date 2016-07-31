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
public class FileProcessor {

    private final BufferedReader reader;
    private final ObservableList<LineBean> fileLines;
    private final File file;

    public FileProcessor(File file) throws FileNotFoundException {
        this.file = file;
        this.reader = new BufferedReader(new FileReader(file));
        this.fileLines = FXCollections.observableArrayList();

    }

    public ObservableList<LineBean> getFileLines() {
        String thisLineValue;
        int thisLinexindex = 0;

        try {
            while ((thisLineValue = reader.readLine()) != null) {
//                if (thisLine.charAt(thisLine.length() - 1) == '#') {
                this.fileLines.add(new LineBean(
                        new SimpleStringProperty(String.valueOf(thisLinexindex)),
                        new SimpleStringProperty(thisLineValue)
                )
                );
                thisLinexindex++;
            }

//            if (fileLines.size() >= 2) {
//                fileLines.remove(0);
//                fileLines.remove(fileLines.size() - 1);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileLines;
    }

    public int[] getModuleIndexes() {
        String fileName = this.file.getName();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        int[] indexes = new int[2];

        if (fileExtension.equals("txt")) {
            indexes[0] = 0;
            indexes[1] = 10;
        } else if (fileExtension.equals("REM")) {
            indexes[0] = 41;
            indexes[1] = 43;
        }

        return indexes;
    }

}
