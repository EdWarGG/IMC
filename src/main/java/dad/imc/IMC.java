package dad.imc;

import javafx.application.Application;
import javafx.beans.binding.DoubleExpression;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class IMC extends Application {
	
	//view
	private VBox root;
	
	private HBox pesoHBox;
	private Label pesoLabel;
	private TextField pesoTf;
	private Label kgLabel; 
	
	private HBox alturaHBox;
	private Label alturaLabel;
	private TextField alturaTf;
	private Label cmLabel;
	
	private HBox imcHBox;
	private Label imcLabel;
	private Label valorLabel;
	
	private Label concLabel; 
	
	//model
	DoubleProperty peso = new SimpleDoubleProperty();
	DoubleProperty altura = new SimpleDoubleProperty();
	DoubleProperty imc = new SimpleDoubleProperty();

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		pesoLabel = new Label();
		pesoLabel.setText("Peso:");
		pesoTf = new TextField();
		kgLabel = new Label();
		kgLabel.setText("kg");
		pesoHBox = new HBox(5, pesoLabel, pesoTf, kgLabel);

		alturaLabel = new Label();
		alturaLabel.setText("Altura:");
		alturaTf = new TextField();
		cmLabel = new Label();
		cmLabel.setText("cm");
		alturaHBox = new HBox(5, alturaLabel, alturaTf, cmLabel);
		
		imcLabel = new Label();
		imcLabel.setText("IMC:");
		valorLabel = new Label();
		imcHBox = new HBox(5, imcLabel, valorLabel);
		
		concLabel = new Label();
		concLabel.setText("Bajo | Normal | Sobrepeso | Obeso");
		
		root = new VBox();
		root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(pesoHBox, alturaHBox, imcHBox, concLabel);
		root.setFillWidth(false);
		root.setSpacing(5);
		
		Scene scene = new Scene(root, 320, 200);
		
		primaryStage.setTitle("IMC:fxml");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		//bindings
		pesoTf.textProperty().bindBidirectional(peso, new NumberStringConverter());
		alturaTf.textProperty().bindBidirectional(altura, new NumberStringConverter());
		valorLabel.textProperty().bind(imc.asString("%.1f"));
		
		//logic
		DoubleExpression alturaM = altura.divide(100);
		DoubleExpression calcIMC = peso.divide((alturaM).multiply(alturaM));
		imc.bind(calcIMC);
		
		imc.addListener((o, ov, nv) -> {
			if(nv.doubleValue() < 18.5) 
				concLabel.setText("Bajo peso");
			if(nv.doubleValue() >= 18.5 && nv.doubleValue() <= 25) 
				concLabel.setText("Normal");
			if(nv.doubleValue() >= 25 && nv.doubleValue() <= 30) 
				concLabel.setText("Sobrepeso");
			if(nv.doubleValue() >= 30) 
				concLabel.setText("Obeso");
				
		});

	}
	
	public static void main(String[] args) {
		launch(args);
		
	}

}
