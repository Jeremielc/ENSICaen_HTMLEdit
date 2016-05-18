package com.ensicaen.htmledit.fxml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
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
    private TextArea htmlEditor;
    @FXML
    private WebView webView;
    @FXML
    private WebEngine webEngine;

    @FXML
    private Text infos, characters, lines;

    private Stage primaryStage;
    private boolean fileHasName = false;
    private File workingDir = null;
    private String filename;

    public RootLayoutController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        characters.setText("0");
        lines.setText("0");
    }

    @FXML
    public void updateGui() {
        webEngine = webView.getEngine();
        webEngine.loadContent(htmlEditor.getText());

        /*String text = htmlEditor.getText();
        characters.setText(String.valueOf(text.length()));
        int nbLine = 0;
        
        StringTokenizer st = new StringTokenizer(text, "\n");
        while (st.hasMoreTokens()) {
            nbLine++;
        }
        lines.setText(String.valueOf(nbLine));*/
    }

    @FXML
    public void handleNewFile() {

    }

    @FXML
    public void handleOpenFile() {

    }

    @FXML //Done
    public void handleSaveFile() {
        if (fileHasName) {
            File file = new File(workingDir.getAbsolutePath() + File.separator + filename);
            try (FileWriter fw = new FileWriter(file)) {
                String text = htmlEditor.getText();
                for (char c : text.toCharArray()) {
                    fw.write(c);
                }
                
                infos.setText("File successfully saved.");
            } catch (IOException ex) {
                ex.printStackTrace(System.err);
                infos.setText("File cannot be saved.");
            }
        } else {
            handleSaveFileAs();
        }
    }

    @FXML //Done
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

            try (FileWriter fw = new FileWriter(file)) {
                String text = htmlEditor.getText();
                for (char c : text.toCharArray()) {
                    fw.write(c);
                }

                infos.setText("File successfully saved.");
            } catch (IOException ex) {
                ex.printStackTrace(System.err);
                infos.setText("File cannot be saved.");
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
        }
    }

    @FXML
    public void handleCloseFile() {

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

    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
