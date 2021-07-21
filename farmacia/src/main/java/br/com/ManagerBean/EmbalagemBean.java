package br.com.ManagerBean;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.DAO.DaoEmbalagem;
import br.com.Model.Embalagens;
import br.com.lazyDataTable.LazyEmbalagens;

@ManagedBean(name = "EmbalagemBean")
@SessionScoped
public class EmbalagemBean {

	
	private Embalagens embalagens = new Embalagens();
	private DaoEmbalagem<Embalagens> daoEmbalagem = new DaoEmbalagem<Embalagens>();
	private LazyEmbalagens<Embalagens> embalagensLista = new LazyEmbalagens<Embalagens>();
	
	
	@PostConstruct
	public void init() {
		embalagensLista.load(0, 8, null, null);
		
	}
	
	
	public String salvar() {
		
		daoEmbalagem.salvar(embalagens);
		embalagensLista.list.add(embalagens);
		
		embalagens = new Embalagens();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "informação", "Embalagem cadastrada com sucesso!"));
		return "";
	}
	
	public String novo() {
		embalagens = new Embalagens();
		return "";
	}
	
	public String removerEmbalagem(){
		
		daoEmbalagem.removerEmbalagem(embalagens);
		embalagensLista.list.remove(embalagens);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação:", "Embalagem removida com sucesso!"));
		return "";
	}
	
	
	public Embalagens editarEmbalagem() {
		
		try {
			
			String idEmbalagem = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idEmbalagem");
			
			FacesContext.getCurrentInstance().getExternalContext().redirect("cadEmbalagens.jsf");
			
			embalagens = daoEmbalagem.pesquisar(Long.valueOf(idEmbalagem), Embalagens.class);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return embalagens;
	}
	
	
	
	
	
	
	
	public Embalagens getEmbalagens() {
		return embalagens;
	}
	public void setEmbalagens(Embalagens embalagens) {
		this.embalagens = embalagens;
	}
	
	
	public LazyEmbalagens<Embalagens> getEmbalagensLista() {
		return embalagensLista;
	}
	
	
	
	
	
	
}
