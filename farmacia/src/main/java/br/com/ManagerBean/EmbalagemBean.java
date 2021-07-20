package br.com.ManagerBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.DAO.DaoGeneric;
import br.com.Model.Embalagens;

@ManagedBean(name = "EmbalagemBean")
@ViewScoped
public class EmbalagemBean {

	
	private Embalagens embalagens = new Embalagens();
	private DaoGeneric<Embalagens> daoGeneric = new DaoGeneric<Embalagens>();
	private List<Embalagens> embalagensLista = new ArrayList<Embalagens>();
	
	
	
	
	
	public String salvar() {
		
		daoGeneric.salvar(embalagens);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "informação", "Embalagem cadastrada com sucesso!"));
		return "";
	}
	
	public String novo() {
		embalagens = new Embalagens();
		return "";
	}
	
	
	
	
	
	
	
	
	
	
	public Embalagens getEmbalagens() {
		return embalagens;
	}
	public void setEmbalagens(Embalagens embalagens) {
		this.embalagens = embalagens;
	}
	public List<Embalagens> getEmbalagensLista() {
		return embalagensLista;
	}
	
	
	
	
	
	
	
}
