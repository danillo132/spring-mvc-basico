package br.com.ManagerBean;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.component.barchart.BarChart;
import org.primefaces.event.FileUploadEvent;

import org.primefaces.model.chart.ChartSeries;

import com.google.gson.Gson;

import br.com.DAO.DaoClientes;
import br.com.DAO.DaoFunc;
import br.com.DAO.DaoOrcamentos;
import br.com.DAO.DaoPedidos;
import br.com.Model.Clientes;
import br.com.Model.Funcionarios;
import br.com.Model.Orcamentos;
import br.com.Model.Pedidos;
import br.com.lazyDataTable.LazyDataTable;
import br.com.repository.IDaoFuncionario;
import br.com.repository.IDaoFuncionarioimpl;

@ManagedBean(name = "FuncBean")
@SessionScoped
public class FuncBean {

	
	
	private Funcionarios funcionarios = new Funcionarios();
	private Clientes clientes = new Clientes();
	private IDaoFuncionario daoFuncionario = new IDaoFuncionarioimpl();
	private LazyDataTable<Funcionarios> funcionariosLista = new LazyDataTable<Funcionarios>();
	private LazyDataTable<Funcionarios> funcaoFuncionarios = new LazyDataTable<Funcionarios>();
	private DaoFunc<Funcionarios> daoFunc = new DaoFunc<Funcionarios>();
	private DaoOrcamentos<Orcamentos> daoOrcamentos = new DaoOrcamentos<Orcamentos>();
	private DaoPedidos<Pedidos> daoPedidos = new DaoPedidos<Pedidos>();
	private DaoClientes<Clientes> daoClientes  = new DaoClientes<Clientes>();
	private List<Funcionarios> listaAtendentes = new ArrayList<Funcionarios>();
	private List<Clientes> clientesLista = new ArrayList<Clientes>();
	private BarChartModel BarChart = new BarChartModel();
	
	
	@PostConstruct
	public void carregaFuncionarios() {

		 funcionariosLista.load(0, 10, null, null);
		funcaoFuncionarios.loadFuncaoFuncionarios(0, 4, null, null, "Atendente");
		 graficoAtendente();
	
	}
	
	
	public void graficoAtendente() {
		 ChartData data = new ChartData();
	        
	        BarChartDataSet barDataSet = new BarChartDataSet();
	        barDataSet.setLabel("Vendas por atendente");
	        List<String> nomesAtendentes = new ArrayList<String>();
	        List<Number> pedidosAtendentes = new ArrayList<Number>();
	        
	        listaAtendentes = daoFunc.graficoFunc("Atendente");
	        
	        for (Funcionarios funcionarios : listaAtendentes) {
				
	        	int pedidosTotal = 0;
	        	nomesAtendentes.add(funcionarios.getNome());
	        	clientesLista = daoClientes.pesquisarClientes(funcionarios.getId());
				
				
				for (Clientes cliente : clientesLista) {
					clientes = daoClientes.pesquisar(cliente.getId(), Clientes.class);
					
					pedidosTotal += daoPedidos.contarPedidos(clientes.getId());
				}
				pedidosAtendentes.add(pedidosTotal);
				
			}
	        data.setLabels(nomesAtendentes);
	        barDataSet.setData(pedidosAtendentes);
	     
	       
	        
	        List<String> bgColor = new ArrayList<>();
	        bgColor.add("rgba(255, 99, 132, 0.2)");
	        bgColor.add("rgba(255, 159, 64, 0.2)");
	        bgColor.add("rgba(255, 205, 86, 0.2)");
	        bgColor.add("rgba(75, 192, 192, 0.2)");
	        bgColor.add("rgba(54, 162, 235, 0.2)");
	        bgColor.add("rgba(153, 102, 255, 0.2)");
	        bgColor.add("rgba(201, 203, 207, 0.2)");
	        barDataSet.setBackgroundColor(bgColor);
	        
	        List<String> borderColor = new ArrayList<>();
	        borderColor.add("rgb(255, 99, 132)");
	        borderColor.add("rgb(255, 159, 64)");
	        borderColor.add("rgb(255, 205, 86)");
	        borderColor.add("rgb(75, 192, 192)");
	        borderColor.add("rgb(54, 162, 235)");
	        borderColor.add("rgb(153, 102, 255)");
	        borderColor.add("rgb(201, 203, 207)");
	        barDataSet.setBorderColor(borderColor);
	        barDataSet.setBorderWidth(1);
	        
	        data.addChartDataSet(barDataSet);
	        
	       
	        

	        //Data
	        BarChart.setData(data);
	        
	        //Options
	        BarChartOptions options = new BarChartOptions();
	        CartesianScales cScales = new CartesianScales();
	        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
	        CartesianLinearTicks ticks = new CartesianLinearTicks();
	        ticks.setBeginAtZero(true);
	        linearAxes.setTicks(ticks);
	        cScales.addYAxesData(linearAxes);
	        options.setScales(cScales);
	        
	        Title title = new Title();
	        title.setDisplay(true);
	        title.setText("Gráfico");
	        options.setTitle(title);

	        Legend legend = new Legend();
	        legend.setDisplay(true);
	        legend.setPosition("top");
	        LegendLabel legendLabels = new LegendLabel();
	        legendLabels.setFontStyle("bold");
	        legendLabels.setFontColor("#2980B9");
	        legendLabels.setFontSize(24);
	        legend.setLabels(legendLabels);
	        options.setLegend(legend);

	        BarChart.setOptions(options);
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
		
		
		daoFunc.salvar(funcionarios);
		funcionariosLista.list.add(funcionarios);
		funcionarios = new Funcionarios();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação: ", "Funcionário cadastrado com sucesso!"));
		return "";
	}
	
	public Funcionarios editarFunc() {
		
		try {
		FacesContext context = FacesContext.getCurrentInstance();
		String IdFunc = context.getExternalContext().getRequestParameterMap().get("idFunc");
		
		
		context.getExternalContext().redirect("cadFunc.jsf");
		
		
		 funcionarios = daoFunc.pesquisar(Long.valueOf(IdFunc), Funcionarios.class);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return funcionarios;
	}
	
	
	public List<Funcionarios> carregarAtendentes(){
		
		listaAtendentes =  daoFunc.graficoFunc("Atendente");
		
		return listaAtendentes;
				
	}
	
	
	public Integer carregarPaineisAtendente() {
		int clienteTotal = 0;
		int orcamentosTotal = 0;
		int pedidosTotal = 0;
		int pedidosentreguesTotal = 0;
		try {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();

		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		HttpSession session = request.getSession();

		Funcionarios funcionarioUser = (Funcionarios) session.getAttribute("funcionariologado");

		funcionarios = daoFunc.pesquisar(funcionarioUser.getId(), Funcionarios.class);
		 clienteTotal = daoClientes.contarClientes(funcionarios.getId());
		 funcionarios.setClientesCadastrados(clienteTotal);
		 
			clientesLista = daoClientes.pesquisarClientes(funcionarios.getId());
			
			
			for (Clientes cliente : clientesLista) {
				clientes = daoClientes.pesquisar(cliente.getId(), Clientes.class);
				
				orcamentosTotal += daoOrcamentos.contarOrcamentos(clientes.getId());
				pedidosTotal += daoPedidos.contarPedidos(clientes.getId());
				pedidosentreguesTotal += daoPedidos.contarPedidosEntregues(clientes.getId());
			}
	
			 funcionarios.setOrcamentosCotados(orcamentosTotal);
			 funcionarios.setPedidosCadastrados(pedidosTotal);
			 funcionarios.setPedidosEntregues(pedidosentreguesTotal);
			 
			 
			 
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		return clienteTotal;
	}
	
	public Integer qtdOrcamentos() {
		int orcamentosTotal = 0;
		
		try {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();

		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		HttpSession session = request.getSession();

		Funcionarios funcionarioUser = (Funcionarios) session.getAttribute("funcionariologado");

		funcionarios = daoFunc.pesquisar(funcionarioUser.getId(), Funcionarios.class);
		clientesLista = daoClientes.pesquisarClientes(funcionarios.getId());
		
		
		for (Clientes cliente : clientesLista) {
			clientes = daoClientes.pesquisar(cliente.getId(), Clientes.class);
			
			orcamentosTotal += daoOrcamentos.contarOrcamentos(clientes.getId());
		}
		
		
		 funcionarios.setOrcamentosCotados(orcamentosTotal);
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		return orcamentosTotal;
	}
	
	
	public boolean permiteAcesso(String acesso) {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();

		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		HttpSession session = request.getSession();

		Funcionarios funcionarioUser =  (Funcionarios) session.getAttribute("funcionariologado");
		
		return funcionarioUser.getFuncao().equals(acesso);
	}
	
	
	
	
	
	
	public String logar()  {
		
		
		try {
			Funcionarios funcionariosUser = daoFuncionario.consultarFuncionario(funcionarios.getLogin(), funcionarios.getSenha());
			
			
			if (funcionariosUser != null) {

				// Adicionar o usuario na sessao usuariologado
				FacesContext context = FacesContext.getCurrentInstance();
				ExternalContext externalContext = context.getExternalContext();

				HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
				HttpSession session = request.getSession();

				session.setAttribute("funcionariologado", funcionariosUser);
				
				
				carregarPaineisAtendente();
				
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

		externalContext.invalidateSession();

		return "index.jsf";
	}
	
	
	
	
	public String novo() {
		funcionarios = new Funcionarios();
		
		return "";
	}
	
	
	public String removerFunc() throws IOException  {
		
		try {
			daoFunc.removerFuncionarios(funcionarios);
			funcionariosLista.list.remove(funcionarios);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "informação", "Funcionário removido com sucesso!"));
		} catch (Exception e) {
			
			if(e.getCause() instanceof ConstraintViolationException) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro:", "Existem clientes para o funcionário! Por favor recarregue a página"));
			}else {
				e.printStackTrace();
			}
		}
		
		return "";
	}
	
	
	public void upload(FileUploadEvent image) {
		
		String imagem = "data:image/jpg;base64," + DatatypeConverter.printBase64Binary(image.getFile().getContent());
		funcionarios.setImagem(imagem);
	}
	
	
	public LazyDataTable<Funcionarios> getFuncaoFuncionarios() {
		return funcaoFuncionarios;
	}
	
	
	public BarChartModel getBarChart() {
		return BarChart;
	}
	
	public LazyDataTable<Funcionarios> getFuncionariosLista() {
		return funcionariosLista;
	}
	
	
	public void setFuncionarios(Funcionarios funcionarios) {
		this.funcionarios = funcionarios;
	}
	
	public Funcionarios getFuncionarios() {
		return funcionarios;
	}
	
}
