package br.com.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.Model.Funcionarios;
import br.com.SingleConnection.SingleConnection;

public class IDaoFuncionarioimpl implements IDaoFuncionario {

	
	
	private EntityManager entityManager = SingleConnection.geEntityManager();
	
	@Override
	public Funcionarios consultarFuncionario(String login, String senha) {
		
		Funcionarios funcionario = null;
		
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		funcionario = (Funcionarios) entityManager.createQuery("select f from Funcionarios f where f.login = '" + login+"' and f.senha = '"+senha+"'").getSingleResult();
		
		transaction.commit();
		
		return funcionario;
	}

	
}
