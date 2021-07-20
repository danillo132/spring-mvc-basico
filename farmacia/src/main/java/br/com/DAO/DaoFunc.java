package br.com.DAO;

import br.com.Model.Funcionarios;

public class DaoFunc<E> extends DaoGeneric<Funcionarios> {
	
	
	
	public void removerFuncionarios(Funcionarios funcionario) throws Exception {
		
		getEntityManager().getTransaction().begin();
		
		getEntityManager().remove(getEntityManager().contains(funcionario) ? funcionario : getEntityManager().merge(funcionario));
		getEntityManager().getTransaction().commit();
	}

}
