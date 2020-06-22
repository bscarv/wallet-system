package application;

import java.util.List;

import model.entities.Operacao;
import model.entities.Operador;
import model.services.Acesso;
import model.services.Arquivo;

public class Main {

	public static void main(String[] args) {
		Operador op = Operador.obterSingletonOperador("08680092703", "13run0");
		//Acesso.cadastrar(op);
		
		if (Acesso.entrar(op)) {
			System.out.println("Entrei! " + op);
			
			List<Operacao> list = Arquivo.obterOperacoesCSV("operacoes.csv");
			op.realizarOperacoes(list);			
		}
		else {
			System.out.println("Não entrei...");
		}
	}
}
