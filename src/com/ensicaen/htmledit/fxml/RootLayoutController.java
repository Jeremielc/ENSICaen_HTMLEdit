package com.ensicaen.htmledit.fxml;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 *
 * @author Jérémie Leclerc : jeremie.leclerc@fime.com
 */
public class RootLayoutController implements Initializable {

    @FXML private MenuItem newFile, openFile, saveFile, saveFileAs, closeFile, quit;
    @FXML private MenuItem cut, copy, paste;//touppercase tolowercase
    @FXML private MenuItem about;

    @FXML private TextArea htmlEditor;
    @FXML private WebView webView;
    @FXML private WebEngine webEngine;

    @FXML private Text characters, lines;

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

    @FXML
    public void handleSaveFile() {

    }

    @FXML
    public void handleSaveFileAs() {

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
}
