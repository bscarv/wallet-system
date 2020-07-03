package view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.entities.Operador;
import model.services.Acesso;
import view.utils.Alerts;
import view.utils.Utils;

public class AcessoController implements Initializable{

	@FXML
	private Button btLogin;
	@FXML
	private Button btNovoOperador;
	@FXML
	private Button btSair;
	@FXML 
	private TextField tFCPF;
	@FXML 
	private PasswordField pFSenha;
	
	@FXML
	public void onBTLoginAction(ActionEvent event) {		
		Stage thisStage = Utils.currentStage(event);	
		Operador op = Operador.obterSingletonOperador(tFCPF.getText(), pFSenha.getText());		
		
		if (Acesso.entrar(op)) {
//			System.out.println("Entrei! " + op);			
			thisStage.close();
		}
		else {
			Alerts.showAlert("Erro!", "Erro!", "Acesso negado!", AlertType.ERROR);			
		}		
	}	
	
	@FXML
	public void onBTNovoOperadorAction(ActionEvent event) {
		Stage thisStage = Utils.currentStage(event);		
	}
	
	
	@FXML
	public void onBTSairAction(ActionEvent event) {
		Stage thisStage = Utils.currentStage(event);
		thisStage.close();	
		((Stage)thisStage.getOwner()).close();
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}

}
