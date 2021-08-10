package br.com.DAO;

import br.com.Model.Equipamentos;

public class DaoEqui<E> extends DaoGeneric<Equipamentos> {

	
	public void removerEquipamento(Equipamentos equipamentos) {
		
		getEntityManager().getTransaction().begin();
		
		getEntityManager().remove(getEntityManager().contains(equipamentos) ? equipamentos : getEntityManager().merge(equipamentos));
		
		
		getEntityManager().getTransaction().commit();
	}
	
	public Integer contarEquipamentosEstoque() {
		getEntityManager().getTransaction().begin();
		Integer total = Integer.parseInt(getEntityManager().createQuery("select count(id) from Equipamentos").getSingleResult().toString()); 
		getEntityManager().getTransaction().commit();
		return total;
	}
}
