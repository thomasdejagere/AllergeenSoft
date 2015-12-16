
import domain.Controller;
import gui.AlgemeenFrame;
import java.sql.SQLException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Thomas
 */
public class StartUp extends Application{
    @Override
    public void start(Stage stage) throws SQLException {
        try{
            Controller controller = new Controller();
        Scene scene = new Scene(new AlgemeenFrame(controller));
        stage.setTitle("AllergenenSoft");
        stage.setScene(scene);
        scene.getStylesheets().add("css.css");
        stage.sizeToScene();

        // The stage will not get smaller than its preferred (initial) size.
        stage.setOnShown((WindowEvent t) -> {
            stage.setMinWidth(stage.getWidth());
            stage.setMinHeight(stage.getHeight());
        });
        stage.show();
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Er is een fout ontstaan tijdens het laden van de applicatie\n"
                    + "Stuur deze foutmelding door naar de ontwikkelaar:\n"
                    + "in StartUp.java: " + ex.getMessage());
        }
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
