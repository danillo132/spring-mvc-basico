package br.com.ManagerBean;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.xml.bind.DatatypeConverter;

import org.primefaces.event.FileUploadEvent;

import com.google.gson.Gson;

import br.com.DAO.DaoGeneric;
import br.com.Model.Fornecedores;

@ManagedBean(name = "FornecedoresBean")
@ViewScoped
public class FornecedoresBean {

	
	
	private Fornecedores fornecedores = new Fornecedores();
	private DaoGeneric<Fornecedores> daoGeneric = new DaoGeneric<Fornecedores>();
	private List<Fornecedores> fornecedoresLista = new ArrayList<Fornecedores>();

	
	
	
	
public List<String> list(String query){
	String queryLowerCase = query.toLowerCase();
		List<String> autocomplete = new ArrayList<>();
		fornecedoresLista = daoGeneric.listar(Fornecedores.class);
		
		for (Fornecedores string : fornecedoresLista) {
			autocomplete.add(string.getNomeEmpresa());
		}
		
		return autocomplete.stream().filter(t -> t.toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
	}
	
	public String salvar() {
		
		daoGeneric.salvar(fornecedores);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Fornecedor salvo com sucesso!"));
		
		return "";
	}
	
	public String novo() {
		
		fornecedores = new Fornecedores();
		
		return "";
	}
	
	
	
	
	
public void pesquisaCep(AjaxBehaviorEvent event) {
		
		try {
					
					URL url = new URL("https://viacep.com.br/ws/"+fornecedores.getCep()+"/json/");
					URLConnection connection = url.openConnection();
					InputStream  is = connection.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
					
					
						String cep = "";
						StringBuilder jsonCep = new StringBuilder();
						
						
						while((cep = reader.readLine()) != null) {
							jsonCep.append(cep);
						}
						
						Fornecedores fornecedoresCep = new Gson().fromJson(jsonCep.toString(), Fornecedores.class);
						
						fornecedores.setCep(fornecedoresCep.getCep());
						fornecedores.setLogradouro(fornecedoresCep.getLogradouro());
						fornecedores.setComplemento(fornecedoresCep.getComplemento());
						fornecedores.setBairro(fornecedoresCep.getBairro());
						
						fornecedores.setLocalidade(fornecedoresCep.getLocalidade());
						fornecedores.setUf(fornecedoresCep.getUf());
					
					
					
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
	
	
	
	
	
	
	
	public void upload(FileUploadEvent image) {
		
		String imagem = "data:image/jpg;base64," + DatatypeConverter.printBase64Binary(image.getFile().getContent());
		fornecedores.setImagem(imagem);
	}
	
	
	
	
	
	
	public Fornecedores getFornecedores() {
		return fornecedores;
	}
	public void setFornecedores(Fornecedores fornecedores) {
		this.fornecedores = fornecedores;
	}
	public List<Fornecedores> getFornecedoresLista() {
		return fornecedoresLista;
	}
	
	
	
}
