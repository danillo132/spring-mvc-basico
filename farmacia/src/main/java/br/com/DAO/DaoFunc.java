package br.com.DAO;

import br.com.Model.Funcionarios;

public class DaoFunc<E> extends DaoGeneric<Funcionarios> {
	
	
	
	public void removerFuncionarios(Funcionarios funcionario) {
		
		getEntityManager().getTransaction().begin();
		
		getEntityManager().remove(funcionario);
		getEntityManager().getTransaction().commit();
	}

}
