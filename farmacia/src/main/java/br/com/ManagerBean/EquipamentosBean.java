package br.com.ManagerBean;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.DAO.DaoEqui;
import br.com.Model.Equipamentos;
import br.com.lazyDataTable.LazyEquipamentos;

@ManagedBean(name = "EquipamentosBean")
@SessionScoped
public class EquipamentosBean {

	private Equipamentos equipamentos = new Equipamentos();
	private DaoEqui<Equipamentos> daoEqui = new DaoEqui<Equipamentos>();
	private LazyEquipamentos<Equipamentos> equipamentosLista = new LazyEquipamentos<Equipamentos>();
	
	
	
	
	@PostConstruct
	public void init() {
		equipamentosLista.load(0, 8, null, null);
	}
	
	public String salvar() {
		
		daoEqui.salvar(equipamentos);
		equipamentosLista.list.add(equipamentos);
		equipamentos = new Equipamentos();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Equipamento salvo com sucesso!"));
		return "";
	}
	
	
	public String novo() {
		
		equipamentos = new Equipamentos();
		
		return "";
	}
	
	
	public String removerEquipamento() {
		
		daoEqui.removerEquipamento(equipamentos);
		equipamentosLista.list.remove(equipamentos);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Equipamento removido com sucesso!"));
		return "";
	}
	
	
public Equipamentos editarEquipamento() {
		
		try {
			
			String idEquipamento = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idEquipamento");
			
			FacesContext.getCurrentInstance().getExternalContext().redirect("cadEquipamentos.jsf");
			
			equipamentos = daoEqui.pesquisar(Long.valueOf(idEquipamento), Equipamentos.class);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return equipamentos;
	}
	
	
	public LazyEquipamentos<Equipamentos> getEquipamentosLista() {
		return equipamentosLista;
	}
	
	
	public Equipamentos getEquipamentos() {
		return equipamentos;
	}

	public void setEquipamentos(Equipamentos equipamentos) {
		this.equipamentos = equipamentos;
	}
	
	
}
