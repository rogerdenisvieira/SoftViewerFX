/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softviewerfx.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
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
import softviewerfx.dao.SettingsParser;
import softviewerfx.models.DataBean;
import softviewerfx.models.LineBean;

/**
 *
 * @author Roger
 */
public class AppLayoutController implements Initializable {

    private SettingsParser settingsReader;
    private FileChooser fileChooser;
    private FileParser fileProcessor;
    private static File fileInUse;

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

        try {
            settingsReader = new SettingsParser();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        valueColumn.setCellValueFactory(cellData -> cellData.getValue().lineValueProperty());
        indexColumn.setCellValueFactory(cellData -> cellData.getValue().lineIndexProperty());

        dataNameColumn.setCellValueFactory(cellData -> cellData.getValue().dataProperty());
        dataValueColumn.setCellValueFactory(cellData -> cellData.getValue().dataValueProperty());

        disableComponents(true);
        this.fileLinesTable.disableProperty().set(true);
        this.dataTable.disableProperty().set(true);

        this.fillComboBox();

    }

    @FXML
    public void openFile() {

        this.fileInUse = fileChooser.showOpenDialog(new Stage());

        if (this.fileInUse != null) {
            disableComponents(false);
            this.fileLinesTable.disableProperty().set(true);
            this.dataTable.disableProperty().set(true);
            filePathLabel.setText(fileInUse.getAbsolutePath());
        }

    }

    @FXML
    public void processLineFocused() {
        int begin, end;

        //guarda o objeto referente à linha selecionada.
        LineBean line = fileLinesTable.getSelectionModel().getSelectedItem();

        //busca o índice selecionado do combobox
        int selectedIndex = this.ddlLayoutSelect.getSelectionModel().getSelectedIndex();

        //busca as configurações conforme a seleção do item
        String[] settings = settingsReader.readSettings().get(selectedIndex);

        System.out.println(line.getLineValue().toString());
        try {
            //resgata os índices
            begin = Integer.parseInt(settings[1]);
            end = Integer.parseInt(settings[2]);

            dataTable.setItems(LineParser.processLine(line, begin, end));
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

    //popula o combobox com os valores do arquivo de configuração
    private void disableComponents(boolean b) {
        ddlLayoutSelect.disableProperty().set(b);
        btnProcess.disableProperty().set(b);
    }

    private void fillComboBox() {

        //recebe o retorno dos valores do arquivo de configuração
        List<String[]> setOfValues = settingsReader.readSettings();

        ObservableList<String> options = FXCollections.observableArrayList();

        //itera sobre os valores de configuração adicionando a chave de cada
        setOfValues.stream().forEach((s) -> {
            options.add(s[0]);
        });

        //popula o combobox
        ddlLayoutSelect.setItems(options);
    }
}
