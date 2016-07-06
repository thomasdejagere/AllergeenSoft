package gui;

import domain.Controller;
import domain.Gerecht;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Thomas
 */
public class VoegGerechtToeFrame extends GridPane {

    @FXML
    private ListView lstAllergenen;
    @FXML
    private ComboBox cboGerechtSoort;
    @FXML
    private TextField txtGerechtNaam;
    @FXML
    private Button btnSlaOp;
    @FXML
    private Label lblStatus;
    @FXML
    private Button btnTerug;
    @FXML
    private Label lblError;
    @FXML
    private Label lblTitel;
    @FXML
    private Button btnJa;
    @FXML
    private Button btnNee;
    @FXML
    private Label lblGerechtsoort;
    @FXML
    private Label lblGerechtnaam;
    
    private Controller controller;
    private HashSet<String> foutieveInput;
    private AlgemeenFrame algemeenFrame;

    public VoegGerechtToeFrame(Controller controller, AlgemeenFrame frame) throws SQLException {
        foutieveInput = new HashSet<>();
        constructor(controller, false, frame);

    }

    public VoegGerechtToeFrame(Controller controller, HashSet<String> foutieveInput, AlgemeenFrame frame) throws SQLException {
        this.foutieveInput = foutieveInput;
        constructor(controller, true, frame);

    }

    public void slaOp() throws SQLException {
        String gerechtNaam = txtGerechtNaam.getText().toLowerCase();
        lblError.setText("");
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(gerechtNaam);
        boolean b = m.find();
        
        boolean controle = true;
        
        lblGerechtnaam.setTextFill(Color.BLACK);
        lblGerechtsoort.setTextFill(Color.BLACK);
            
        if (b) {
            lblError.setText(lblError.getText() + "\n* Gerechtnaam mag geen speciale tekens bevatten!\n");
            lblGerechtnaam.setTextFill(Color.RED);
            controle = false;
        }
        if(cboGerechtSoort.getSelectionModel().getSelectedItem() == null || cboGerechtSoort.getSelectionModel().getSelectedItem().toString().equals("Kies een gerechtsoort")){
            lblError.setText(lblError.getText() + "* Selecteer een gerechtsoort\n");
            lblGerechtsoort.setTextFill(Color.RED);
            controle = false;
        }
        
        if (txtGerechtNaam.getText()
                .equals("")) {
            lblError.setText(lblError.getText() + "* Geef een gerechtnaam in\n");
            lblGerechtnaam.setTextFill(Color.RED);
            controle = false;
        }
        if (controle) {
            lblGerechtnaam.setTextFill(Color.BLACK);
            lblGerechtsoort.setTextFill(Color.BLACK);
            String debug2 = txtGerechtNaam.getText();
            Gerecht debug = controller.geefGerecht(txtGerechtNaam.getText());
            if (controller.geefGerecht(txtGerechtNaam.getText().toLowerCase()) != null) {
                controller.pasProductAan(txtGerechtNaam.getText().toLowerCase(), cboGerechtSoort.getSelectionModel().getSelectedItem().toString(), lstAllergenen.getSelectionModel().getSelectedItems());
            } else {
                controller.voegGerechtToe(txtGerechtNaam.getText().toLowerCase(), cboGerechtSoort.getSelectionModel().getSelectedItem().toString(), lstAllergenen.getSelectionModel().getSelectedItems());
            }

            txtGerechtNaam.setText("");
            cboGerechtSoort.getSelectionModel().clearSelection();
            lstAllergenen.getSelectionModel().clearSelection();
            lblStatus.setText("Gerecht is succesvol toegevoegd aan de databank.");
            Iterator<String> iterator = foutieveInput.iterator();
            gerechtNaam = gerechtNaam.toUpperCase();
            while (iterator.hasNext()) {
                if (iterator.next().equalsIgnoreCase(gerechtNaam)) {
                    iterator.remove();
                    break;
                }
            }
            foutieveInput.remove(gerechtNaam);
            if (!foutieveInput.isEmpty()) {
                txtGerechtNaam.setText(this.foutieveInput.toArray()[0].toString());
            } else {
                voegGeenGerechtMeerToe();
                /*lblStatus.setText("Wilt u nog een gerecht \ntoevoegen/aanpassen?");
                btnNee.setVisible(true);
                btnJa.setVisible(true);
                btnTerug.setVisible(false);
                btnSlaOp.setVisible(false);*/
            }
        } else {
            lblStatus.setText("");
            if (!lblError.getText().equals("")) {
                lblError.setVisible(true);
            }

        }

    }

    public void terug() {
        Stage thisStage = (Stage) btnTerug.getScene().getWindow();
        thisStage.close();
        if (algemeenFrame.ietsInInput()) {
            algemeenFrame.zoekAllergenen();
        }
    }

    private void constructor(Controller controller, boolean foutieveInputBoolean, AlgemeenFrame frame) throws SQLException {
        this.controller = controller;
        this.algemeenFrame = frame;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("VoegGerechtToeFrame.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        lstAllergenen.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lstAllergenen.setItems(controller.geefAllergenen());
        cboGerechtSoort.setItems(controller.geefGerechtSoorten());
        cboGerechtSoort.setValue("Kies een gerechtsoort");
        if (foutieveInputBoolean) {
            txtGerechtNaam.setText(this.foutieveInput.toArray()[0].toString());
        }
    }

    public void voegProductToe(String g) {
        txtGerechtNaam.setText(g);
    }

    public void pasProductAan(String g) {
        lblTitel.setText("Pas product aan");
        Gerecht gerecht = controller.geefGerecht(g.toLowerCase());
        if (gerecht.getGerechtSoort() != null) {
            String soort = gerecht.getGerechtSoort().getNaam();
            cboGerechtSoort.getSelectionModel().select(soort);
        }
        List<String> allergenenInProduct = gerecht.getAllergenenList();
        allergenenInProduct.stream().forEach((s) -> {
            lstAllergenen.getSelectionModel().select(s);
        });
        txtGerechtNaam.setText(g);
    }

    public void voegGeenGerechtMeerToe() {
        btnSlaOp.setVisible(true);
        btnTerug.setVisible(true);
        terug();
    }

    public void voegNogEenGerechtToe() {
        btnSlaOp.setVisible(true);
        btnTerug.setVisible(true);
        btnJa.setVisible(false);
        btnNee.setVisible(false);
        lblStatus.setText("");
    }
}
