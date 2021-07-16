package br.com.ManagerBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.DAO.DaoGeneric;
import br.com.Model.Fornecedores;
import br.com.Model.MateriaPrima;

@ManagedBean(name = "MateriaBean")
@ViewScoped
public class MateriaBean {

	
	private MateriaPrima materiaPrima = new MateriaPrima();
	private DaoGeneric<MateriaPrima> daoGeneric = new DaoGeneric<MateriaPrima>();
	private List<MateriaPrima> materiaPrimas = new ArrayList<MateriaPrima>();
	
	
	public String salvar() {
		daoGeneric.salvar(materiaPrima);
		
		return "";
	}
	
	public String novo() {
		materiaPrima = new MateriaPrima();
		
		return "";
	}

	public MateriaPrima getMateriaPrima() {
		return materiaPrima;
	}

	public void setMateriaPrima(MateriaPrima materiaPrima) {
		this.materiaPrima = materiaPrima;
	}

	public List<MateriaPrima> getMateriaPrimas() {
		return materiaPrimas;
	}

	
	
	
}
