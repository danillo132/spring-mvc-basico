package br.com.repository;

import br.com.Model.Funcionarios;

public interface IDaoFuncionario {

	
	
	Funcionarios consultarFuncionario(String login, String senha);
	
	Funcionarios consultarEmail(String email);
}
