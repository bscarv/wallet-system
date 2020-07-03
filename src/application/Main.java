package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Operador;

public class Main extends Application {

	private static Scene mainScene;	
	
	@Override	
	public void start(Stage primaryStage) {
		try {
			
			Stage loginStage = new Stage();
			loginStage.initModality(Modality.WINDOW_MODAL);
			loginStage.initOwner(primaryStage);
	        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/view/AcessoView.fxml"));
			Scene loginScene = new Scene(loginLoader.load());
			loginStage.setTitle("Login");
			loginStage.setX(80);
			loginStage.setY(80);
			loginStage.setScene(loginScene);
			loginStage.setResizable(false);
//			loginStage.initStyle(StageStyle.DECORATED);
//			loginStage.initStyle(StageStyle.UNDECORATED);
//			loginStage.initStyle(StageStyle.TRANSPARENT);
//			loginStage.initStyle(StageStyle.UNIFIED);
//			loginStage.initStyle(StageStyle.UTILITY);

			primaryStage.setTitle("Wallet System");
			primaryStage.setX(50);
			primaryStage.setY(50);
			primaryStage.setWidth(1000);
			primaryStage.setHeight(800);
			
	        primaryStage.show();

	        loginStage.setOnCloseRequest(event -> primaryStage.close());
	        loginStage.showAndWait();
	        
	        Operador op = Operador.obterSingletonOperador();	        
	        op.imprimirCarteira();        	        
	        
	        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/view/MainView.fxml"));			
			Scene mainScene = new Scene(mainLoader.load());
			primaryStage.setScene(mainScene);
			primaryStage.setResizable(false);
			
//			pane.setFitToHeight(true);
//			pane.setFitToWidth(true);			
//			mainScene = new Scene(pane);	        
//			primaryStage.setScene(mainScene);	        
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Scene getMainScene() {
		return mainScene;
	}

	public static void main(String[] args) {
		launch(args);
	}
}

//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.List;
//
//import model.entities.Operacao;
//import model.entities.Operador;
//import model.services.Acesso;
//import model.services.Arquivo;
//
//public class Main {
//
//	public static void main(String[] args) throws ParseException {
//		Operador op = Operador.obterSingletonOperador("Bruno", "Silva de Carvalho", "08680092703", "13run0");
//		
//		if (Acesso.cadastrar(op)) {
//			System.out.println("Cadastro bem sucedido!");
//		}
//		else {
//			System.out.println("Cadastro negado!");
//		}			
//		
////		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//		
//		
//		op.setSenha("13run0");
//		if (Acesso.entrar(op)) {
//			System.out.println("Entrei! " + op);			
////			List<Operacao> list = Arquivo.obterOperacoesCSV("operacoes2.csv");
////			List<Operacao> list = new ArrayList<>();
////			Ativo at  = new Ativo("BCFF11");
////			Operacao operacao = new Operacao(sdf.parse("21/06/2020"), 
////												at, 
////												10,
////												100.00,
////												TipoOperacao.valueOf("COMPRA"));
////			list.add(operacao);
////			op.realizarOperacoes(list);	
//			op.imprimirCarteira();
//		}
//		else {
//			System.out.println("Não entrei...");
//		}
//	}
//}
