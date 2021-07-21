package br.com.DAO;

import br.com.Model.MateriaPrima;

public class DaoMateria<E> extends DaoGeneric<MateriaPrima> {

	public void removerMateriaPrima(MateriaPrima materiaPrima) throws Exception {
		
		getEntityManager().getTransaction().begin();
		
		getEntityManager().remove(getEntityManager().contains(materiaPrima) ? materiaPrima : getEntityManager().merge(materiaPrima));
		
		getEntityManager().getTransaction().commit();
		
	}
	
}
