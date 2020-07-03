package model.services;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import model.dao.FactoryDao;
import model.dao.impl.AcessoDaoJDBC;
import model.entities.Operador;

public class Acesso {

	public static boolean cadastrar(Operador op) {		
		boolean ret = false;
		if (validarFormato(op)) {
			AcessoDaoJDBC obj = (AcessoDaoJDBC) FactoryDao.criarAcessoDao();
			op.setSenha(criptografar(op.getSenha()));
			//TODO: obj.verificarUsuarioJaExistente(op);
			if(obj.verificarOperadorDisponivel(op)) {
				ret = obj.cadastrar(op);				
			}
			else {
				ret = false;
			}			
		} else {
			System.out.println("Senha ou CPF inválidos!");
			ret = false;
		}
		return ret;
	}

	public static boolean entrar(Operador op) {
		
		boolean ret = false;
		if (validarFormato(op)) {
			AcessoDaoJDBC obj = (AcessoDaoJDBC) FactoryDao.criarAcessoDao();
			op.setSenha(criptografar(op.getSenha()));
			ret = obj.entrar(op);
		}
		return ret;
	}
	
	private static boolean validarFormato(Operador op) {		
		return validarFormatoSenha(op.getSenha()) && validarFormatoCPF(op.get_CPF());
	}

	private static boolean validarFormatoCPF(String _CPF) {
		//TODO: Implementar
		return true;
	}

	private static boolean validarFormatoSenha(String senha) {
		//TODO: Implementar
		return true;
	}

	private static String criptografar(String senhaOriginal) {
		String senhaCrypto = null;
		MessageDigest algoritmo; 
		byte messageDigest[]; 
		StringBuilder hexString; 
		try { 
			algoritmo =MessageDigest.getInstance("SHA-256"); //64 letras 
			//algoritmo = MessageDigest.getInstance("MD5");  //32 letras 
			messageDigest = algoritmo.digest(senhaOriginal.getBytes("UTF-8"));
			hexString = new StringBuilder(); 
			for (byte b : messageDigest) { 
				hexString.append(String.format("%02X", 0xFF & b)); 
			} 
			senhaCrypto = hexString.toString(); 
		} catch (NoSuchAlgorithmException e){ 
			e.printStackTrace(); 
		} catch (UnsupportedEncodingException e) { 
			e.printStackTrace(); 
		} 
		//System.out.println("Senha normal: " + senhaOriginal + " - Senha criptografada: " + senhaCrypto); 
		return senhaCrypto; 
	}

}
