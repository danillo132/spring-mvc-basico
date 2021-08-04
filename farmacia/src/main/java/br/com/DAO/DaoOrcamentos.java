package br.com.DAO;

import br.com.Model.Orcamentos;

public class DaoOrcamentos<E> extends DaoGeneric<Orcamentos> {

	
	public void removerOrcamento(Orcamentos orcamentos) {
		getEntityManager().getTransaction().begin();
		
		getEntityManager().remove(getEntityManager().contains(orcamentos) ? orcamentos : getEntityManager().merge(orcamentos));
		
		getEntityManager().getTransaction().commit();
	}
	
	public Integer contarOrcamentos(Long idCliente) {
		getEntityManager().getTransaction().begin();
		Integer total = Integer.parseInt(getEntityManager().createQuery("select count(id) from Orcamentos where clientes_id = " + idCliente).getSingleResult().toString()); 
		getEntityManager().getTransaction().commit();
		return total;
	}
}
