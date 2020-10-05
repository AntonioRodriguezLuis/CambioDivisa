package dad.javafx.cambiodivisa;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CambioDivisaController extends Application {

	private Divisa euro = new Divisa("Euro", 1.0);
	private Divisa libra = new Divisa("Libra", 0.9);
	private Divisa dolar = new Divisa("Dolar", 1.17);
	private Divisa yen = new Divisa("Yen", 124.17);

	private Divisa[] divisas = { euro, libra, dolar, yen };

	private TextField origenText;
	private TextField destinoText = new TextField();
	private ComboBox<Divisa> origenComboBox = new ComboBox<>();
	private ComboBox<Divisa> destinoComboBox = new ComboBox<>();
	private Button cambiarButton = new Button();

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TextField
		origenText = new TextField();
		origenText.setText("0");
		origenText.setPrefColumnCount(4);

		destinoText = new TextField();
		destinoText.setText("0");
		destinoText.setPrefColumnCount(4);
		destinoText.setEditable(false);

		// button
		cambiarButton.setText("Cambiar");
		cambiarButton.setOnAction(e -> onComprobarButtonAction(e));

		// ComboBox
		origenComboBox = new ComboBox<>();
		origenComboBox.getItems().addAll(divisas);
		origenComboBox.getSelectionModel().selectFirst();

		destinoComboBox = new ComboBox<>();
		destinoComboBox.getItems().addAll(divisas);
		destinoComboBox.getSelectionModel().selectFirst();

		HBox origenBox = new HBox();
		origenBox.setSpacing(5);
		origenBox.setAlignment(Pos.BASELINE_CENTER);
		origenBox.getChildren().addAll(origenText, origenComboBox);
		// origenBox.setStyle("-fx-background-color: green");

		HBox destinoBox = new HBox();
		destinoBox.setSpacing(5);
		destinoBox.setAlignment(Pos.CENTER);
		destinoBox.getChildren().addAll(destinoText, destinoComboBox);

		VBox root = new VBox();
		root.setSpacing(5);
		root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(origenBox, destinoBox, cambiarButton);

		Scene escena = new Scene(root, 320, 200);

		primaryStage.setScene(escena);
		primaryStage.setTitle("Cambio de divisa");
		primaryStage.setResizable(true);
		primaryStage.show();
	}

	private void onComprobarButtonAction(ActionEvent e) {
		try {
			Double cantidadOrigen = Double.parseDouble(origenText.getText());
			Divisa divisaorigen = origenComboBox.getSelectionModel().getSelectedItem();
			Divisa divisaDestino = destinoComboBox.getSelectionModel().getSelectedItem();
			Double cantidadDestino = divisaDestino.fromEuro(divisaorigen.toEuro(cantidadOrigen));
			destinoText.setText("" + cantidadDestino);
		} catch (NumberFormatException e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Cambio de divisa");
			alert.setHeaderText("ERROR: Carácter alfabético.");
			alert.setContentText(
					"No introduzca caracteres alfabéticos o símbolos, solo estan permitidos los caracteres numéricos");
			alert.showAndWait();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
