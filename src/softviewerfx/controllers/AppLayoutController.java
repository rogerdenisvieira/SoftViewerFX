/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softviewerfx.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import static javafx.scene.input.KeyCode.N;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import softviewerfx.dao.FileProcessor;
import softviewerfx.dao.LineProcessor;
import softviewerfx.models.DataBean;
import softviewerfx.models.LineBean;

/**
 *
 * @author Roger
 */
public class AppLayoutController implements Initializable {

    private FileChooser fileChooser;
    private FileProcessor fileProcessor;

    @FXML
    private Label filePathLabel;
    @FXML
    private TableColumn<LineBean, String> indexColumn;
    @FXML
    private TableColumn<LineBean, String> valueColumn;
    @FXML
    private TableColumn<DataBean, String> dataNameColumn;
    @FXML
    private TableColumn<DataBean, String> dataValueColumn;

    @FXML
    TableView<LineBean> fileLinesTable;
    @FXML
    TableView<DataBean> dataTable;

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Carga SRC", "*.txt"),
                new FileChooser.ExtensionFilter("FOP", "*.fop"),
                new FileChooser.ExtensionFilter("REM", "*.rem")
        );

        valueColumn.setCellValueFactory(cellData -> cellData.getValue().lineValueProperty());
        indexColumn.setCellValueFactory(cellData -> cellData.getValue().lineIndexProperty());

        dataNameColumn.setCellValueFactory(cellData -> cellData.getValue().dataProperty());
        dataValueColumn.setCellValueFactory(cellData -> cellData.getValue().dataValueProperty());

    }

    @FXML
    public void openFile() {
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            try {
                fileProcessor = new FileProcessor(file);
                filePathLabel.setText(file.getAbsolutePath());
                fileLinesTable.setItems(fileProcessor.getFileLines());
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @FXML
    public void processMouseClick() {
        LineBean line = fileLinesTable.getSelectionModel().getSelectedItem();
        int[] indexes = fileProcessor.getModuleIndexes();

        System.out.println(line.getLineValue().toString());
        try {
            dataTable.setItems(LineProcessor.processLine(line, indexes[0], indexes[1]));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @FXML
    public void processGoTo(){
       Alert alert = new Alert(AlertType.INFORMATION);
alert.setTitle("Information Dialog");
alert.setHeaderText("Look, an Information Dialog");
alert.setContentText("I have a great message for you!");

alert.showAndWait();
        
        System.out.println("clicou em Go To");
    }

}
