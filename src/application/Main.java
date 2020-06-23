package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.entities.Ativo;
import model.entities.Operacao;
import model.entities.Operador;
import model.entities.enums.TipoOperacao;
import model.services.Acesso;

public class Main {

	public static void main(String[] args) throws ParseException {
		Operador op = Operador.obterSingletonOperador("08680092703", "13run0");
		//Acesso.cadastrar(op);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		if (Acesso.entrar(op)) {
			System.out.println("Entrei! " + op);			
			//List<Operacao> list = Arquivo.obterOperacoesCSV("operacoes.csv");
			List<Operacao> list = new ArrayList<>();
			Ativo at  = new Ativo("XPML11");
			Operacao operacao = new Operacao(sdf.parse("22/06/2020"), 
												at, 
												10,
												100.00,
												TipoOperacao.valueOf("VENDA"));
			list.add(operacao);
			op.realizarOperacoes(list);			
		}
		else {
			System.out.println("Não entrei...");
		}
	}
}
