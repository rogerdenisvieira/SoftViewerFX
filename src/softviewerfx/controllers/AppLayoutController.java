/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softviewerfx.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
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

    @FXML
    private Spinner spinGoToLine;

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

        this.spinGoToLine = new Spinner();

        try {
            settingsReader = new SettingsParser();
        } catch (FileNotFoundException ex) {
            showAlert(ex);
        }

        valueColumn.setCellValueFactory(cellData -> cellData.getValue().lineValueProperty());
        indexColumn.setCellValueFactory(cellData -> cellData.getValue().lineIndexProperty());

        dataNameColumn.setCellValueFactory(cellData -> cellData.getValue().dataProperty());
        dataValueColumn.setCellValueFactory(cellData -> cellData.getValue().dataValueProperty());

        disableComponents(true);
        this.fileLinesTable.disableProperty().set(true);
        this.dataTable.disableProperty().set(true);
        this.spinGoToLine.setDisable(true);

        this.fillComboBox();

    }

    @FXML
    public void openFile() {

        this.fileInUse = fileChooser.showOpenDialog(new Stage());

        if (this.fileInUse != null) {
            disableComponents(false);
            this.fileLinesTable.disableProperty().set(true);
            this.dataTable.disableProperty().set(true);
            this.spinGoToLine.setDisable(true);
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
        } catch (Exception ex) {
            showAlert(ex);
        }
    }

    @FXML
    public void processGoTo() {
        System.out.println("clicou em Go To");
        TextInputDialog dialog = new TextInputDialog("walter");
        dialog.setTitle("Text Input Dialog");
        dialog.setHeaderText("Look, a Text Input Dialog");
        dialog.setContentText("Please enter your name:");

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println("Your name: " + result.get());
        }

// The Java 8 way to get the response value (with lambda expression).
        result.ifPresent(name -> System.out.println("Your name: " + name));

    }

    public void showAlert(Exception ex) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("An error has been occurred.");
        alert.setContentText(ex.getMessage());

// Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

// Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
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
                this.spinGoToLine.setDisable(false);
                this.spinGoToLine.setEditable(true);
                this.spinGoToLine = new Spinner(0, fileLinesTable.getItems().size(), 0);
            } catch (FileNotFoundException ex) {
                showAlert(ex);
            }
        }
    }

    private void disableComponents(boolean b) {
        ddlLayoutSelect.disableProperty().set(b);
        btnProcess.disableProperty().set(b);
    }

    //popula o combobox com os valores do arquivo de configuração
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
