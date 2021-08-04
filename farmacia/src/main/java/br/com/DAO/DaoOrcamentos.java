package br.com.DAO;

import br.com.Model.Orcamentos;

public class DaoOrcamentos<E> extends DaoGeneric<Orcamentos> {

	
	public void removerOrcamento(Orcamentos orcamentos) {
		getEntityManager().getTransaction().begin();
		
		getEntityManager().remove(getEntityManager().contains(orcamentos) ? orcamentos : getEntityManager().merge(orcamentos));
		
		getEntityManager().getTransaction().commit();
	}
}
