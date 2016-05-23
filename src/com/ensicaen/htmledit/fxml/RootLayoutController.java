package com.ensicaen.htmledit.fxml;

import com.ensicaen.htmledit.main.MainApp;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Jérémie Leclerc : jeremie.leclerc@fime.com
 */
public class RootLayoutController implements Initializable {

    @FXML
    private MenuItem newFile, openFile, saveFile, saveFileAs, closeFile, quit;
    @FXML
    private MenuItem cut, copy, paste;
    @FXML
    private MenuItem about;
    
    @FXML
    private TabPane tabPane;

    @FXML
    private TextArea htmlEditor;
    @FXML
    private WebView webView;
    @FXML
    private WebEngine webEngine;

    @FXML
    private Text infos, characters, lines;

    private Stage primaryStage;
    private boolean fileHasName = false, fileIsSaved = false;
    private String lastContent;
    private File workingDir = null;
    private String filename;

    public RootLayoutController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        infos.setText("IDLE");
        characters.setText("0");
        lines.setText("0");
    }

    @FXML
    public void updateGui() {
        webEngine = webView.getEngine();
        webEngine.loadContent(htmlEditor.getText());

        String text = htmlEditor.getText();
        characters.setText(String.valueOf(text.length()));
        int nbLine = 0;

        if (text.contains("\n")) {
            String temp;
            StringTokenizer st = new StringTokenizer(text, "\n");
            while (st.hasMoreTokens()) {
                temp = st.nextToken();
                nbLine++;
            }
        }

        lines.setText(String.valueOf(nbLine));
    }

    @FXML
    public void handleNewFile() {
        handleCloseFile();
        fileHasName = false;
        fileIsSaved = false;
        lastContent = "";
        filename = "new_file.html";
        tabPane.getTabs().get(0).setText(filename);
        
        tabPane.getTabs().add(new Tab("new_file.html"));
    }

    @FXML
    public void handleOpenFile() {
        FileChooser chooser = new FileChooser();
        if (workingDir != null) {
            chooser.setInitialDirectory(workingDir);
        } else {
            chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        }

        File file = chooser.showOpenDialog(primaryStage);
        if (file != null) {
            handleCloseFile();

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                htmlEditor.clear();
                updateGui();

                String readed = "";
                do {
                    readed = br.readLine();
                    if (readed != null) {
                        htmlEditor.appendText(readed + "\n");
                    }
                } while (readed != null);

                infos.setText("File loaded successfully.");
                fileIsSaved = true;
                filename = file.getName();
                tabPane.getTabs().get(0).setText(filename);
            } catch (IOException ex) {
                ex.printStackTrace(System.err);
                infos.setText("Cannot load file.");
            }
        } else {
            infos.setText("Cannot load file.");
        }

        updateGui();
    }

    @FXML
    public void handleSaveFile() {
        if (fileHasName) {
            File file = new File(workingDir.getAbsolutePath() + File.separator + filename);
            try (FileWriter fw = new FileWriter(file, false)) {
                String text = htmlEditor.getText();
                for (char c : text.toCharArray()) {
                    fw.write(c);
                }

                infos.setText("File successfully saved.");
                fileIsSaved = true;
                lastContent = htmlEditor.getText();
            } catch (IOException ex) {
                ex.printStackTrace(System.err);
                infos.setText("File cannot be saved.");
                fileIsSaved = false;
            }
        } else {
            handleSaveFileAs();
        }
    }

    @FXML
    public void handleSaveFileAs() {
        FileChooser chooser = new FileChooser();
        if (workingDir != null) {
            chooser.setInitialDirectory(workingDir);
        } else {
            chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        }

        File file = chooser.showSaveDialog(primaryStage);

        if (file != null) {
            fileHasName = true;
            workingDir = file.getParentFile();

            try (FileWriter fw = new FileWriter(file, false)) {
                String text = htmlEditor.getText();
                for (char c : text.toCharArray()) {
                    fw.write(c);
                }

                infos.setText("File successfully saved.");
                fileIsSaved = true;
                lastContent = htmlEditor.getText();
            } catch (IOException ex) {
                ex.printStackTrace(System.err);
                infos.setText("File cannot be saved.");
                fileIsSaved = false;
            }

            if (!file.getName().endsWith(".html")) {
                file.renameTo(new File(file.getAbsolutePath() + ".html"));
                filename = file.getName() + ".html";
            } else {
                filename = file.getName();
            }
        } else {
            fileHasName = false;
            workingDir = null;
            infos.setText("File cannot be saved.");
            fileIsSaved = false;
        }
    }

    @FXML
    public void handleCloseFile() {
        if (fileIsSaved) {
            if (lastContent != null) {
                if (lastContent.compareTo(htmlEditor.getText()) != 0) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.initOwner(primaryStage);
                    alert.setTitle("Are you sure ?");
                    alert.setContentText("It seems that your modifications are not saved.\n"
                            + "Do you wish to continue without saving ?\n");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {

                    } else {
                        handleSaveFile();
                    }
                }
            }

            htmlEditor.clear();
            updateGui();
            filename = "new_file.html";
            tabPane.getTabs().get(0).setText(filename);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(primaryStage);
            alert.setTitle("Are you sure ?");
            alert.setContentText("It seems that your modifications are not saved.\n"
                    + "Do you wish to continue without saving ?\n");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

            } else {
                handleSaveFile();
            }

            htmlEditor.clear();
            updateGui();
            filename = "new_file.html";
            tabPane.getTabs().get(0).setText(filename);
        }
    }

    @FXML
    public void handleQuit() {
        Platform.exit();
    }

    @FXML
    public void handleCut() {

    }

    @FXML
    public void handleCopy() {

    }

    @FXML
    public void handlePaste() {
        
    }

    @FXML
    public void handleAbout() {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.initOwner(primaryStage);
        info.setTitle("About");
        info.setContentText("HTML Editor with live preview. \n"
                + "Written by Pierrick HUE and Jérémie LECLERC.");

        info.show();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
