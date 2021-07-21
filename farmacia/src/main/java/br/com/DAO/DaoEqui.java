package br.com.DAO;

import br.com.Model.Equipamentos;

public class DaoEqui<E> extends DaoGeneric<Equipamentos> {

	
	public void removerEquipamento(Equipamentos equipamentos) {
		
		getEntityManager().getTransaction().begin();
		
		getEntityManager().remove(getEntityManager().contains(equipamentos) ? equipamentos : getEntityManager().merge(equipamentos));
		
		
		getEntityManager().getTransaction().commit();
	}
}
