package br.com.DAO;

import br.com.Model.Embalagens;

public class DaoEmbalagem<E> extends DaoGeneric<Embalagens> {

	
	public void removerEmbalagem(Embalagens embalagens) {
		
		getEntityManager().getTransaction().begin();
		
		getEntityManager().remove(getEntityManager().contains(embalagens) ? embalagens : getEntityManager().merge(embalagens));
		
		getEntityManager().getTransaction().commit();
	}
	
	public Integer contarEmbalagensEstoque() {
		getEntityManager().getTransaction().begin();
		Integer total = Integer.parseInt(getEntityManager().createQuery("select count(id) from Embalagens").getSingleResult().toString()); 
		getEntityManager().getTransaction().commit();
		return total;
	}
}
