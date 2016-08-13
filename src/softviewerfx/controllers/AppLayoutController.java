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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import softviewerfx.dao.FileParser;
import softviewerfx.dao.LineParser;
import softviewerfx.models.DataBean;
import softviewerfx.models.LineBean;

/**
 *
 * @author Roger
 */
public class AppLayoutController implements Initializable {

    private FileChooser fileChooser;
    private FileParser fileProcessor;
    private File fileInUse;

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
    private ComboBox ddlLayoutSelect;
    @FXML
    TableView<LineBean> fileLinesTable;
    @FXML
    TableView<DataBean> dataTable;
    @FXML
    private Button btnProcess;
    

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
        
        disableComponents(true);
        this.fileLinesTable.disableProperty().set(true);
        this.dataTable.disableProperty().set(true);

        
        

        ObservableList<String> options
                = FXCollections.observableArrayList(
                        "Option 1",
                        "Option 2",
                        "Option 3"
                );

        ddlLayoutSelect.setItems(options);

    }

    @FXML
    public void openFile() {
        this.fileInUse = fileChooser.showOpenDialog(new Stage());
        if(this.fileInUse != null){
            disableComponents(false);
            filePathLabel.setText(fileInUse.getAbsolutePath());
        }

    }

    @FXML
    public void processMouseClick() {
        LineBean line = fileLinesTable.getSelectionModel().getSelectedItem();
        int selectedIndex = this.ddlLayoutSelect.getSelectionModel().getSelectedIndex();
        int[] indexes = fileProcessor.getModuleIndexes(selectedIndex);

        System.out.println(line.getLineValue().toString());
        try {
            dataTable.setItems(LineParser.processLine(line, indexes[0], indexes[1]));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void processGoTo() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText("I have a great message for you!");

        alert.showAndWait();

        System.out.println("clicou em Go To");
    }

    @FXML
    public void btnProcessClicked() {
        if (fileInUse != null) {

            try {
                fileProcessor = new FileParser(fileInUse);                
                fileLinesTable.setItems(fileProcessor.getFileLines());
                disableComponents(true);
                this.fileLinesTable.disableProperty().set(false);
                this.dataTable.disableProperty().set(false);
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    private void disableComponents(boolean b){   
        ddlLayoutSelect.disableProperty().set(b);
        btnProcess.disableProperty().set(b);
    }
}
