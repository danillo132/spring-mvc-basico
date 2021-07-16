package br.com.ManagerBean;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import org.primefaces.event.FileUploadEvent;

import com.google.gson.Gson;

import br.com.DAO.DaoGeneric;
import br.com.Model.Funcionarios;
import br.com.repository.IDaoFuncionario;
import br.com.repository.IDaoFuncionarioimpl;

@ManagedBean(name = "FuncBean")
@ViewScoped
public class FuncBean {

	
	private Funcionarios funcionarios = new Funcionarios();
	private DaoGeneric<Funcionarios> daoGeneric = new DaoGeneric<Funcionarios>();
	private List<Funcionarios> funcionariosLista = new ArrayList<Funcionarios>();
	private IDaoFuncionario daoFuncionario = new IDaoFuncionarioimpl();
	
	
	public List<Funcionarios> getFuncionariosLista() {
		return funcionariosLista;
	}
	
	
	
	@PostConstruct
	public void carregaFuncionarios() {
		funcionariosLista = daoGeneric.listar(Funcionarios.class);
	}
	
	public void pesquisaCep(AjaxBehaviorEvent event) {
		
		try {
					
					URL url = new URL("https://viacep.com.br/ws/"+funcionarios.getCep()+"/json/");
					URLConnection connection = url.openConnection();
					InputStream  is = connection.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
					
					
						String cep = "";
						StringBuilder jsonCep = new StringBuilder();
						
						
						while((cep = reader.readLine()) != null) {
							jsonCep.append(cep);
						}
						
						Funcionarios funcionariosCep = new Gson().fromJson(jsonCep.toString(), Funcionarios.class);
						
						funcionarios.setCep(funcionariosCep.getCep());
						funcionarios.setLogradouro(funcionariosCep.getLogradouro());
						funcionarios.setComplemento(funcionariosCep.getComplemento());
						funcionarios.setBairro(funcionariosCep.getBairro());
						
						funcionarios.setLocalidade(funcionariosCep.getLocalidade());
						funcionarios.setUf(funcionariosCep.getUf());
					
					
					
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
	
	
	public String salvar() {
		
		
		daoGeneric.salvar(funcionarios);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Funcionário cadastrado com sucesso!"));
		return "";
	}
	
	
	public boolean permiteAcesso(String acesso) {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();

		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		HttpSession session = request.getSession();

		Funcionarios funcionarioUser =  (Funcionarios) session.getAttribute("funcionariologado");
		
		return funcionarioUser.getFuncao().equals(acesso);
	}
	
	public String logar() {
		
		
		try {
			Funcionarios funcionariosUser = daoFuncionario.consultarFuncionario(funcionarios.getLogin(), funcionarios.getSenha());
			
			
			if (funcionariosUser != null) {

				// Adicionar o usuario na sessao usuariologado
				FacesContext context = FacesContext.getCurrentInstance();
				ExternalContext externalContext = context.getExternalContext();

				HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
				HttpSession session = request.getSession();

				session.setAttribute("funcionariologado", funcionariosUser);
				
				
				
				return "home.jsf";
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Login ou senha incorretos"));
			return "index.jsf";
		}
		
		
			return "";	
		
	}
	
	public String deslogar() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();

		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		HttpSession session = request.getSession();
		session.removeAttribute("funcionariologado");
		session.invalidate();

		return "index.jsf";
	}
	
	
	
	
	public String novo() {
		funcionarios = new Funcionarios();
		
		return "";
	}
	
	
	public void upload(FileUploadEvent image) {
		
		String imagem = "data:image/jpg;base64," + DatatypeConverter.printBase64Binary(image.getFile().getContent());
		funcionarios.setImagem(imagem);
	}
	
	
	
	
	
	
	public void setFuncionarios(Funcionarios funcionarios) {
		this.funcionarios = funcionarios;
	}
	
	public Funcionarios getFuncionarios() {
		return funcionarios;
	}
	
}
