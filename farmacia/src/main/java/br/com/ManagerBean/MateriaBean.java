package br.com.ManagerBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.DAO.DaoMateria;
import br.com.Model.Fornecedores;
import br.com.Model.MateriaPrima;
import br.com.lazyDataTable.LazyMateriaPrima;

@ManagedBean(name = "MateriaBean")
@SessionScoped
public class MateriaBean {

	
	private MateriaPrima materiaPrima = new MateriaPrima();
	private DaoMateria<MateriaPrima> daoMateria = new DaoMateria<MateriaPrima>();
	private LazyMateriaPrima<MateriaPrima> materiaLista = new LazyMateriaPrima<MateriaPrima>();
	
	@PostConstruct
	public void init() {
		materiaLista.load(0, 5, null, null);
		qtdMaterianoEstoque();
	}
	
	public String salvar() {
		daoMateria.salvar(materiaPrima);
		materiaLista.list.add(materiaPrima);
		materiaPrima = new MateriaPrima();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Máteria-prima salva com sucesso!"));
		
		return "";
	}
	
	public String novo() {
		materiaPrima = new MateriaPrima();
		
		return "";
	}
	
	
	public String removerMateria() throws IOException {
		
		try {
			daoMateria.removerMateriaPrima(materiaPrima);
			materiaLista.list.remove(materiaPrima);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Informação: " , "Matéria-prima removida com sucesso!"));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return "";
	}
	
	public List<String> listarFarmaco(String query){
		String queryLowerCase = query.toLowerCase();
			List<String> autocomplete = new ArrayList<>();
			materiaLista.list = daoMateria.listar(MateriaPrima.class);
			
			for (MateriaPrima string : materiaLista.list) {
				autocomplete.add(string.getNome());
			}
			
			return autocomplete.stream().filter(t -> t.toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
		}
	
	
	
	public MateriaPrima editarMateria() {
		
		try {
		String idMateria = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idMateria");
		
		FacesContext.getCurrentInstance().getExternalContext().redirect("cadMateria.jsf");
		
		materiaPrima = daoMateria.pesquisar(Long.valueOf(idMateria), MateriaPrima.class);
		}catch(Exception e){
			
			e.printStackTrace();
		}
		return materiaPrima;
	}
	
	
	public Integer qtdMaterianoEstoque() {
		int qtdTotalMateria = 0;
		
		qtdTotalMateria = daoMateria.contarMateriaEstoque();
		materiaPrima.setQtdTotalEstoque(qtdTotalMateria);
		return qtdTotalMateria;
		
	}
	
	

	public MateriaPrima getMateriaPrima() {
		return materiaPrima;
	}

	public void setMateriaPrima(MateriaPrima materiaPrima) {
		this.materiaPrima = materiaPrima;
	}

	public LazyMateriaPrima<MateriaPrima> getMateriaLista() {
		return materiaLista;
	}

	
	
	
}
