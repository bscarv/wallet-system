package model.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.entities.Ativo;
import model.entities.Operacao;
import model.entities.enums.TipoOperacao;

public class Arquivo {
	
	public static List<Operacao> obterOperacoesCSV(String path){
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		List<Operacao> list = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line;
			String[] parts;
			Ativo at;
			do {
				line = br.readLine();
				if(line != null) {					
					parts = line.split(",");
					at = new Ativo(parts[1]);
					list.add(new Operacao(sdf.parse(parts[0]), 
											at, 
											(Integer)Integer.parseInt(parts[2]),
											(Double)Double.parseDouble(parts[3]),
											TipoOperacao.valueOf(parts[4])));
				}
			}while(line != null);
			
		} catch (IOException e) {
			System.out.println("Erro lendo arquivo: " + e.getMessage());
		} catch (NumberFormatException e) {
			System.out.println("Erro convertendo data: " + e.getMessage());			
		} catch (ParseException e) {
			System.out.println("Erro convertendo data: " + e.getMessage());
		}	
		
		return list;
	}

}
