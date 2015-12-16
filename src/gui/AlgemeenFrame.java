
package gui;

import domain.Controller;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Wiebe
 */
public class AlgemeenFrame extends GridPane {

    @FXML
    private TextArea txaInput;
    @FXML
    private TextArea txaOutput;
    @FXML
    private Button btnOutput;
    @FXML
    private Button btnVoegGerechtToe;
    @FXML
    private ListView lstInput;
    @FXML
    private Label lblStatus;
    @FXML
    private Button btnVoegFoutieveGerechtenToe;
    @FXML
    private Button btnPasProductAan;
    @FXML
    private Button btnVoegFoutieveGerechtToe;
    private Controller controller;
    private Set<String> foutieveInput;

    public AlgemeenFrame(Controller controller) {
        this.controller = controller;
        foutieveInput = new HashSet<>();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AlgemeenFrame.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (Exception ex) {
            
            JOptionPane.showMessageDialog(null, "Fout in de applicatie:\n"
                    + "Stuur volgende foutmelding door naar de ontwikkelaar:\n\n"
                    + "Fout in AlgemeenFrame.constructor(Controller): " + ex.getMessage()                    
            );
            throw new RuntimeException(ex);
        }
        pasProductAanListView();

    }

    public AlgemeenFrame(Controller controller, String status) {
        this.controller = controller;
        foutieveInput = new HashSet<>();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AlgemeenFrame.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Fout in de applicatie:\n"
                    + "Stuur volgende foutmelding door naar de ontwikkelaar:\n\n"
                    + "Fout in AlgemeenFrame.constructor(Controller, String status): " + ex.getMessage() );
            throw new RuntimeException(ex);
        }
        lblStatus.setText(status);
        pasProductAanListView();
    }

    public void zoekAllergenen() {
        if (!txaInput.getText().equals("")) {
            lstInput.setItems(controller.zetOmNaarObservableList(txaInput.getText()));
            lstInput.setVisible(true);
            txaInput.setVisible(false);
            foutieveInput = new HashSet<>();
            List<String> result = controller.zoekAllergenen(txaInput.getText());
            if (!result.isEmpty() || result.get(0).equals(" ")) {
                lstInput.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
                    @Override
                    public ListCell<String> call(ListView<String> param) {
                        ListCell<String> cell = new ListCell<String>() {
                            @Override
                            protected void updateItem(String t, boolean bln) {
                                super.updateItem(t, bln);
                                getStyleClass().remove("mystyleclass");
                                if (t != null) {
                                    if (!t.equals("")) {
                                        if (t.trim().length() != 0) {
                                            setText(t);
                                            if (!controller.checkGerecht(t)) {
                                                if (!getStyleClass().contains("mystyleclass")) {
                                                    getStyleClass().add("mystyleclass");
                                                    foutieveInput.add(t);
                                                    btnVoegFoutieveGerechtenToe.setVisible(true);
                                                } else {
                                                    getStyleClass().remove("mystyleclass");
                                                }
                                            } else {
                                                setText(t);
                                            }
                                        }
                                    }
                                }
                            }
                        };
                        return cell;
                    }
                });

                String resultString = "";
                for (int i = 0; i < result.size(); i++) {
                    resultString += result.get(i) + "\n";
                }
                txaOutput.setText(resultString);

            }
        }

    }

    public void voegGerechtToe(ActionEvent e) throws SQLException {
        toonAndereFrame(false);
    }

    public void nieuweInput() {
        lstInput.setVisible(false);
        txaInput.setVisible(true);
        txaInput.setText("");
        txaOutput.setText("");
        foutieveInput = new HashSet<>();
        btnVoegFoutieveGerechtenToe.setVisible(false);
        btnPasProductAan.setVisible(false);
        btnVoegFoutieveGerechtToe.setVisible(false);
    }

    public void voegFoutieveGerechtenToe() throws SQLException {
        btnVoegFoutieveGerechtenToe.setVisible(false);
        toonAndereFrame(true);
    }

    public void toonAndereFrame(boolean foutieveInput) throws SQLException {
        Stage stage = new Stage();
        Scene scene;
        if (foutieveInput) {
            scene = new Scene(new VoegGerechtToeFrame(controller, this.foutieveInput, this));
        } else {
            scene = new Scene(new VoegGerechtToeFrame(controller, this));
        }
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        scene.getStylesheets().add("/gui/styles.css");
        stage.setScene(scene);

        // The stage will not get smaller than its preferred (initial) size.
        stage.setOnShown((WindowEvent t) -> {
            stage.setMinWidth(stage.getWidth());
            stage.setMinHeight(stage.getHeight());
        });
        stage.show();
    }

    public void pasProductAan() throws SQLException {
        Stage stage = new Stage();
        Scene scene;
        VoegGerechtToeFrame frame = new VoegGerechtToeFrame(controller, this);
        frame.pasProductAan(lstInput.getSelectionModel().getSelectedItem().toString());
        scene = new Scene(frame);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        scene.getStylesheets().add("/gui/styles.css");
        stage.setScene(scene);

        // The stage will not get smaller than its preferred (initial) size.
        stage.setOnShown((WindowEvent t) -> {
            stage.setMinWidth(stage.getWidth());
            stage.setMinHeight(stage.getHeight());
        });
        stage.show();
    }

    private void pasProductAanListView() {
        lstInput.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (lstInput.getSelectionModel().getSelectedItem() != null) {
                    if (!controller.checkGerecht(lstInput.getSelectionModel().getSelectedItem().toString())) {
                        btnVoegFoutieveGerechtToe.setVisible(true);
                        btnPasProductAan.setVisible(false);
                    } else {
                        btnPasProductAan.setVisible(true);
                        btnVoegFoutieveGerechtToe.setVisible(false);
                    }
                }
            }
        });
    }

    public boolean ietsInInput() {
        return !txaInput.getText().trim().isEmpty();

    }

    public void voegFoutieveGerechtToe() throws SQLException {
        Stage stage = new Stage();
        Scene scene;
        VoegGerechtToeFrame frame = new VoegGerechtToeFrame(controller, this);
        frame.voegProductToe(lstInput.getSelectionModel().getSelectedItem().toString());
        scene = new Scene(frame);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        scene.getStylesheets().add("/gui/styles.css");
        stage.setScene(scene);

        // The stage will not get smaller than its preferred (initial) size.
        stage.setOnShown((WindowEvent t) -> {
            stage.setMinWidth(stage.getWidth());
            stage.setMinHeight(stage.getHeight());
        });
        stage.show();
    }
}
