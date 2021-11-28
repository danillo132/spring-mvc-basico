package br.com.DAO;

import java.util.ArrayList;
import java.util.List;

import br.com.Model.Funcionarios;

public class DaoFunc<E> extends DaoGeneric<Funcionarios> {
	List<Funcionarios> funcs = new ArrayList<Funcionarios>();
	
	
	public void removerFuncionarios(Funcionarios funcionario) throws Exception {
		
		getEntityManager().getTransaction().begin();
		
		getEntityManager().remove(getEntityManager().contains(funcionario) ? funcionario : getEntityManager().merge(funcionario));
		getEntityManager().getTransaction().commit();
	}
	
	public List graficoFunc(String funcao) {
		getEntityManager().getTransaction().begin();
		funcs = getEntityManager().createQuery(" from Funcionarios where funcao like '"+funcao+"' " ).getResultList(); 
		getEntityManager().getTransaction().commit();
		return funcs;
	}
	
	public Integer contarTotalFuncionarios() {
		getEntityManager().getTransaction().begin();
		Integer total = Integer.parseInt(getEntityManager().createQuery("select count(id) from Funcionarios where ativo = TRUE").getSingleResult().toString()); 
		getEntityManager().getTransaction().commit();
		return total;
	}
	
	public Integer contarTotalFuncionariosInativos() {
		getEntityManager().getTransaction().begin();
		Integer total = Integer.parseInt(getEntityManager().createQuery("select count(id) from Funcionarios where ativo = FALSE").getSingleResult().toString()); 
		getEntityManager().getTransaction().commit();
		return total;
	}
	
	public Integer contarTotalFuncionariosFuncao(String funcao) {
		getEntityManager().getTransaction().begin();
		Integer total = Integer.parseInt(getEntityManager().createQuery("select count(id) from Funcionarios where funcao like '"+funcao+"'").getSingleResult().toString()); 
		getEntityManager().getTransaction().commit();
		return total;
	}
	
	public Boolean pesquisaImagem(Long id) {
		getEntityManager().getTransaction().begin();
		
		Boolean imagemAtiva = (Boolean) getEntityManager().createQuery("select case when fotoIconBase64 is null then 'FALSE' else 'TRUE' end from Funcionarios where id = " + id).getSingleResult();
		
		
		return imagemAtiva;
	}
	
	

}
