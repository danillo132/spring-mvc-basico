package br.com.ManagerBean;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

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

import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;

import com.google.gson.Gson;

import br.com.DAO.DaoClientes;
import br.com.DAO.DaoFunc;
import br.com.Model.Clientes;
import br.com.Model.Funcionarios;
import br.com.lazyDataTable.LazyClientes;

@ManagedBean(name = "ClienBean")
@SessionScoped
public class ClienBean {

	private Clientes clientes = new Clientes();
	private Funcionarios funcionario = new Funcionarios();
	private DaoFunc<Funcionarios> daoFuncionario = new DaoFunc<Funcionarios>();
	private DaoClientes<Clientes> daoCliente = new DaoClientes<Clientes>();
	private LazyClientes<Clientes> clientesLista = new LazyClientes<Clientes>();

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();

		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		HttpSession session = request.getSession();

		Funcionarios funcionarioUser = (Funcionarios) session.getAttribute("funcionariologado");

		funcionario = daoFuncionario.pesquisar(funcionarioUser.getId(), Funcionarios.class);
		clientesLista.load(0, 5, null, null);
		contarTotalClientes();
	}

	public String salvar() {
		clientes.setFuncionarios(funcionario);

		daoCliente.salvar(clientes);
		clientesLista.list.add(clientes);
		funcionario = daoFuncionario.pesquisar(funcionario.getId(), Funcionarios.class);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Cliente salvo com sucesso!"));

		return "";
	}

	public String novo() {

		clientes = new Clientes();

		return "";
	}

	public String removerCliente() {
		
		try {
		daoCliente.removerCliente(clientes);
		clientesLista.list.remove(clientes);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Cliente removido com sucesso!"));
		}catch(Exception e) {
			
			if(e.getCause() instanceof ConstraintViolationException) {

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Existem orçamentos e/ou receitas para esse cliente! POR FAVOR RECARREGUE A PÁGINA!"));
			}else {
				e.printStackTrace();
			}
			}
			
			
		return "";
	}
	
	
	public Clientes editarCliente()  {
		
		try {
		String idCliente = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idCliente");
		FacesContext.getCurrentInstance().getExternalContext().redirect("cadCliente.jsf");
		
		clientes = daoCliente.pesquisar(Long.valueOf(idCliente), Clientes.class);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return clientes;
	}

	public void upload(FileUploadEvent image) {

		String imagem = "data:image/jpg;base64," + DatatypeConverter.printBase64Binary(image.getFile().getContent());
		clientes.setImagem(imagem);
	}
	
	
	public void contarTotalClientes() {
		int totaldeClientes = 0;
		
		totaldeClientes = daoCliente.contarTotalClientes();
		clientes.setTotalClientes(totaldeClientes);
		
	}

	public void pesquisaCep(AjaxBehaviorEvent event) {

		try {

			URL url = new URL("https://viacep.com.br/ws/" + clientes.getCep() + "/json/");
			URLConnection connection = url.openConnection();
			InputStream is = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			String cep = "";
			StringBuilder jsonCep = new StringBuilder();

			while ((cep = reader.readLine()) != null) {
				jsonCep.append(cep);
			}

			Clientes clientesCep = new Gson().fromJson(jsonCep.toString(), Clientes.class);

			clientes.setCep(clientesCep.getCep());
			clientes.setLogradouro(clientesCep.getLogradouro());
			clientes.setComplemento(clientesCep.getComplemento());
			clientes.setBairro(clientesCep.getBairro());

			clientes.setLocalidade(clientesCep.getLocalidade());
			clientes.setUf(clientesCep.getUf());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Clientes getClientes() {
		return clientes;
	}

	public void setClientes(Clientes clientes) {
		this.clientes = clientes;
	}

	public LazyClientes<Clientes> getClientesLista() {
		return clientesLista;
	}

	public Funcionarios getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionarios funcionario) {
		this.funcionario = funcionario;
	}

	public DaoClientes<Clientes> getDaoCliente() {
		return daoCliente;
	}

	public void setDaoCliente(DaoClientes<Clientes> daoCliente) {
		this.daoCliente = daoCliente;
	}

}
