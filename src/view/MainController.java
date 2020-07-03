package view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entities.Ativo;
import model.entities.Posicao;

public class MainController implements Initializable{

	@FXML
	private TableView<Posicao> tableViewCarteira;
	@FXML
	private TableColumn<Posicao, Ativo> tableColumnAtivo;
	@FXML
	private TableColumn<Posicao, Integer> tableColumnCotas;
	@FXML
	private TableColumn<Posicao, Double> tableColumnValorMedio;	
	
	private ObservableList<Posicao> obsList;
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		
	}
	
	private void initializeNodes() {
		tableColumnAtivo.setCellValueFactory(new PropertyValueFactory<>("ativo"));
		tableColumnCotas.setCellValueFactory(new PropertyValueFactory<>("cotas"));
		tableColumnValorMedio.setCellValueFactory(new PropertyValueFactory<>("valorMedio"));
	}

}
