package br.com.ManagerBean;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import br.com.DAO.DaoClientes;
import br.com.DAO.DaoOrcamentos;
import br.com.Model.Clientes;
import br.com.Model.Orcamentos;
import br.com.lazyDataTable.LazyClientes;
import br.com.lazyDataTable.LazyOrcamentos;

@ManagedBean(name = "orcamentosBean")
@SessionScoped
public class OrcamentosBean {

	private Orcamentos orcamentos = new Orcamentos();
	private Clientes clientes = new Clientes();
	private DaoClientes<Clientes> daoCliente = new DaoClientes<Clientes>();
	private DaoOrcamentos<Orcamentos> daoOrcamentos = new  DaoOrcamentos<Orcamentos>();
	private LazyOrcamentos<Orcamentos> orcamentosLista = new LazyOrcamentos<Orcamentos>();
	private String campoPesquisa;
	
	
	
	
	public String salvar() {
		
		
		orcamentos.setClientes(clientes);
		
		daoOrcamentos.salvar(orcamentos);
		
		orcamentosLista.list.add(orcamentos);
		
		orcamentos = new Orcamentos();
		clientes = daoCliente.pesquisar(clientes.getId(), Clientes.class);
		orcamentosLista.loading(0, 5, null, null, clientes.getId());
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Orçamento salvo com sucesso!"));
		
		
		return "";
		
	}
	
	public String novo() {
		
		orcamentos = new Orcamentos();
		
		return "";
	}
	
	
	
	
	
	

	
	public String removerOrcamento() {
		
		daoOrcamentos.removerOrcamento(orcamentos);
		orcamentosLista.list.remove(orcamentos);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Orçamento removido com sucesso!"));
		
		return "";
	}
	
	public Clientes orcamentoCliente() {
		
		try {
			String idCliente = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idCliente");
			FacesContext.getCurrentInstance().getExternalContext().redirect("orcamentos.jsf");
			clientes = daoCliente.pesquisar(Long.valueOf(idCliente), Clientes.class);
			orcamentosLista.loading(0, 5, null, null, clientes.getId());
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return clientes;
	}
	
	public void pesquisaOrcamento() {
		
		try {
		clientes = daoCliente.pesquisaCpf(campoPesquisa, Clientes.class);
		orcamentosLista.loading(0, 5, null, null, clientes.getId());
		}catch(Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro:", "Usuário não possui orçamentos!"));
		}
	}


	public Orcamentos getOrcamentos() {
		return orcamentos;
	}


	public void setOrcamentos(Orcamentos orcamentos) {
		this.orcamentos = orcamentos;
	}


	public Clientes getClientes() {
		return clientes;
	}


	


	public LazyOrcamentos<Orcamentos> getOrcamentosLista() {
		return orcamentosLista;
	}


	public void setCampoPesquisa(String campoPesquisa) {
		this.campoPesquisa = campoPesquisa;
	}
	
	public String getCampoPesquisa() {
		return campoPesquisa;
	}
	
	
	
}
