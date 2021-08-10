package br.com.DAO;

import br.com.Model.MateriaPrima;

public class DaoMateria<E> extends DaoGeneric<MateriaPrima> {

	public void removerMateriaPrima(MateriaPrima materiaPrima) throws Exception {
		
		getEntityManager().getTransaction().begin();
		
		getEntityManager().remove(getEntityManager().contains(materiaPrima) ? materiaPrima : getEntityManager().merge(materiaPrima));
		
		getEntityManager().getTransaction().commit();
		
	}
	
	public Integer contarMateriaEstoque() {
		getEntityManager().getTransaction().begin();
		Integer total = Integer.parseInt(getEntityManager().createQuery("select count(id) from MateriaPrima").getSingleResult().toString()); 
		getEntityManager().getTransaction().commit();
		return total;
	}
	
}
