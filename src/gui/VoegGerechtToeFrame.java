/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.Controller;
import domain.Gerecht;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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
    
    private Controller controller;
    private Set<String> foutieveInput;
    private AlgemeenFrame algemeenFrame;
    public VoegGerechtToeFrame(Controller controller, AlgemeenFrame frame) throws SQLException {
        foutieveInput = new HashSet<>();
        constructor(controller, false, frame);
        
    }

    public VoegGerechtToeFrame(Controller controller, Set<String> foutieveInput, AlgemeenFrame frame) throws SQLException {
        this.foutieveInput = foutieveInput;
        constructor(controller, true, frame);

    }

    public void slaOp() throws SQLException {
        String gerechtNaam = txtGerechtNaam.getText().toLowerCase();
        lblError.setText("");
        boolean controle = true;
        if (cboGerechtSoort.getSelectionModel().getSelectedItem().toString().equals("Kies een gerechtsoort")) {
            lblError.setText(lblError.getText() + "* Selecteer een gerechtsoort\n");
            controle = false;
        }
        if (txtGerechtNaam.getText().equals("")) {
            lblError.setText(lblError.getText() + "* Geef een gerechtnaam in\n");
            controle = false;
        }
        if (controle) {
            if(controller.geefGerecht(txtGerechtNaam.getText()) != null){
                controller.pasProductAan(txtGerechtNaam.getText().toLowerCase(), cboGerechtSoort.getSelectionModel().getSelectedItem().toString(), lstAllergenen.getSelectionModel().getSelectedItems());
            }else{
                controller.voegGerechtToe(txtGerechtNaam.getText().toLowerCase(), cboGerechtSoort.getSelectionModel().getSelectedItem().toString(), lstAllergenen.getSelectionModel().getSelectedItems());
            }
            
            txtGerechtNaam.setText("");
            cboGerechtSoort.getSelectionModel().clearSelection();
            lstAllergenen.getSelectionModel().clearSelection();
            lblStatus.setText("Gerecht is succesvol toegevoegd aan de databank.");
            foutieveInput.remove(gerechtNaam);
            if (!foutieveInput.isEmpty()) {
            txtGerechtNaam.setText(this.foutieveInput.toArray()[0].toString());
            }
            else{
               lblStatus.setText("Wilt u nog een gerecht \ntoevoegen/aanpassen?");
               btnNee.setVisible(true);
               btnJa.setVisible(true);
               btnTerug.setVisible(false);
               btnSlaOp.setVisible(false);
            }
        }
        else{
            if (!lblError.getText().equals("")) {
            lblError.setVisible(true);
        }
        
        }
        
    }

    public void terug() {
        Stage thisStage = (Stage) btnTerug.getScene().getWindow();
        thisStage.close();
        if(algemeenFrame.ietsInInput())
        algemeenFrame.zoekAllergenen();
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
    public void voegProductToe (String g){
        txtGerechtNaam.setText(g);
    }
    public void pasProductAan(String g) {
        lblTitel.setText("Pas product aan");
        Gerecht gerecht = controller.geefGerecht(g);
        if(gerecht.getGerechtSoort() != null){
        String soort = gerecht.getGerechtSoort().getNaam();
        cboGerechtSoort.getSelectionModel().select(soort);
        }
        List<String> allergenenInProduct = gerecht.getAllergenenList();
        allergenenInProduct.stream().forEach((s) -> {
            lstAllergenen.getSelectionModel().select(s);
        });
        txtGerechtNaam.setText(g);
    }
    public void voegGeenGerechtMeerToe(){
        btnSlaOp.setVisible(true);
        btnTerug.setVisible(true);
        terug();
    }
    public void voegNogEenGerechtToe(){
        btnSlaOp.setVisible(true);
        btnTerug.setVisible(true);
        btnJa.setVisible(false);
        btnNee.setVisible(false);
        lblStatus.setText("");
    }
}