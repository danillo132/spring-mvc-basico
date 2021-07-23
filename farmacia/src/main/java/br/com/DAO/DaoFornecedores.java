package br.com.DAO;

import br.com.Model.Fornecedores;

public class DaoFornecedores<E> extends DaoGeneric<Fornecedores> {

	
	public void removerFornecedor(Fornecedores fornecedores) {
		
		getEntityManager().getTransaction().begin();
		getEntityManager().remove(getEntityManager().contains(fornecedores) ? fornecedores : getEntityManager().merge(fornecedores));
		getEntityManager().getTransaction().commit();
	}
	
}
