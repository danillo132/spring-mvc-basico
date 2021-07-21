package br.com.DAO;

import br.com.Model.Embalagens;

public class DaoEmbalagem<E> extends DaoGeneric<Embalagens> {

	
	public void removerEmbalagem(Embalagens embalagens) {
		
		getEntityManager().getTransaction().begin();
		
		getEntityManager().remove(getEntityManager().contains(embalagens) ? embalagens : getEntityManager().merge(embalagens));
		
		getEntityManager().getTransaction().commit();
	}
}
