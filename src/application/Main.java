package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import model.entities.Operacao;
import model.entities.Operador;
import model.services.Acesso;
import model.services.Arquivo;

public class Main {

	public static void main(String[] args) throws ParseException {
		Operador op = Operador.obterSingletonOperador("08680092703", "13run0");
		//Acesso.cadastrar(op);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		if (Acesso.entrar(op)) {
//			System.out.println("Entrei! " + op);			
			List<Operacao> list = Arquivo.obterOperacoesCSV("operacoes2.csv");
//			List<Operacao> list = new ArrayList<>();
//			Ativo at  = new Ativo("BCFF11");
//			Operacao operacao = new Operacao(sdf.parse("21/06/2020"), 
//												at, 
//												10,
//												100.00,
//												TipoOperacao.valueOf("COMPRA"));
//			list.add(operacao);
//			op.realizarOperacoes(list);	
			op.imprimirCarteira();
		}
		else {
			System.out.println("Não entrei...");
		}
	}
}
