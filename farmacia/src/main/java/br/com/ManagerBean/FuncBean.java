package br.com.ManagerBean;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.primefaces.model.charts.donut.DonutChartModel;
import org.primefaces.model.charts.donut.DonutChartOptions;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.PrimeFaces;
import org.primefaces.component.donutchart.DonutChart;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.donut.DonutChartDataSet;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.file.UploadedFile;

import com.google.gson.Gson;

import br.com.DAO.DaoClientes;
import br.com.DAO.DaoEmbalagem;
import br.com.DAO.DaoEqui;
import br.com.DAO.DaoFunc;
import br.com.DAO.DaoMateria;
import br.com.DAO.DaoOrcamentos;
import br.com.DAO.DaoPedidos;
import br.com.Model.Clientes;
import br.com.Model.Embalagens;
import br.com.Model.Equipamentos;
import br.com.Model.Fornecedores;
import br.com.Model.Funcionarios;
import br.com.Model.MateriaPrima;
import br.com.Model.Orcamentos;
import br.com.Model.Pedidos;
import br.com.lazyDataTable.LazyDataTable;
import br.com.repository.IDaoFuncionario;
import br.com.repository.IDaoFuncionarioimpl;
import service.RelatorioService;

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
	private List<Funcionarios> listaFunc = new ArrayList<Funcionarios>();
	private List<Clientes> clientesLista = new ArrayList<Clientes>();
	private BarChartModel BarChart = new BarChartModel();
	private BarChartModel BarChartFarmaceutico = new BarChartModel();
	private DaoMateria<MateriaPrima> DaoMateria = new DaoMateria<MateriaPrima>();
	private DaoEmbalagem<Embalagens> daoEmbalagem  = new DaoEmbalagem<Embalagens>();
	private DaoEqui<Equipamentos> daoequi = new DaoEqui<Equipamentos>();
	private LineChartModel lineChart = new LineChartModel();
	private DonutChartModel donutChart = new DonutChartModel();
	private DonutChartModel donutFuncao = new DonutChartModel();
	private RelatorioService relatorioService = new RelatorioService();
	
	private UploadedFile arquivofoto;
	
	
	
	@PostConstruct
	public void carregaFuncionarios() {

		 funcionariosLista.load(0, 6, null, null);
		 
		
		
		 graficoAtendente();
		 graficoFarmaceutico();
		 graficoSalarioFuncionarios();
		 graficosGerente();	
	}
	
	
	public void graficoFarmaceutico() {
		 ChartData data = new ChartData();
	        
	        BarChartDataSet barDataSet = new BarChartDataSet();
	        barDataSet.setLabel("Gráfico do Estoque");
	        
	        int materiasTotal = 0;
	        int embalagensTotal = 0;
	        int equipamentosTotal = 0;
	        int formulasEntregues = 0;
	        List<String> produtosEstoque = new ArrayList<String>();
	        List<Number> totalEstoque = new ArrayList<Number>();
	        
	        produtosEstoque.add("Matérias-primas");
	        produtosEstoque.add("Embalagens");
	        produtosEstoque.add("Equipamentos");
	        produtosEstoque.add("Fórmulas entregues");
	        
	        materiasTotal = DaoMateria.contarMateriaEstoque();
	        totalEstoque.add(materiasTotal);
	        
	        embalagensTotal = daoEmbalagem.contarEmbalagensEstoque();
	        totalEstoque.add(embalagensTotal);
	        
	        equipamentosTotal = daoequi.contarEquipamentosEstoque();
	        totalEstoque.add(equipamentosTotal);
	        
	        formulasEntregues = daoPedidos.contarStatusEntregue();
	        totalEstoque.add(formulasEntregues);
	        
	        data.setLabels(produtosEstoque);
	        barDataSet.setData(totalEstoque);
	     
	       
	        
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
	        BarChartFarmaceutico.setData(data);
	        
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

	        BarChartFarmaceutico.setOptions(options);
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
	
	public void graficoSalarioFuncionarios() {
		
        ChartData data = new ChartData();
        
        LineChartDataSet dataSet = new LineChartDataSet();
        List<Object> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();
      
        dataSet.setFill(false);
        dataSet.setLabel("Salário");
        dataSet.setBorderColor("rgb(75, 192, 192)");
        dataSet.setLineTension(0.1);
        data.addChartDataSet(dataSet);
        
        
        listaFunc = daoFunc.listar(Funcionarios.class);
        
        for (Funcionarios funclista : listaFunc) {
			
        	values.add(funclista.getSalario());
        	labels.add(funclista.getNome());
		}
        
        dataSet.setData(values);
        data.setLabels(labels);
        
        //Options
        LineChartOptions options = new LineChartOptions();        
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Salário de todos funcionários");
        options.setTitle(title);
        
        lineChart.setOptions(options);
        lineChart.setData(data);
	}
	
	
	public void graficosGerente() {
		
		 ChartData data = new ChartData();
		 DonutChartOptions options = new DonutChartOptions();
	        DonutChartDataSet dataSet = new DonutChartDataSet();
	        List<Number> values = new ArrayList<>();
	        values.add(daoFunc.contarTotalFuncionarios());
	        values.add(daoFunc.contarTotalFuncionariosInativos());
	        dataSet.setData(values);
	        
	        Title title = new Title();
	        title.setDisplay(true);
	        title.setText("Funcionários");
	        options.setTitle(title);
	        
	        List<String> bgColors = new ArrayList<>();
	        bgColors.add("rgb(132, 221, 99)");
	        bgColors.add("rgb(255, 99, 132)");
	       
	        dataSet.setBackgroundColor(bgColors);
	        
	        data.addChartDataSet(dataSet);
	        List<String> labels = new ArrayList<>();
	        labels.add("Ativos");
	        labels.add("Inativos");
	        data.setLabels(labels);
	       
	        donutChart.setOptions(options);
	        donutChart.setData(data);
	        
	        
ChartData data1 = new ChartData();
	        
	        DonutChartDataSet dataSet1 = new DonutChartDataSet();
	        DonutChartOptions options1 = new DonutChartOptions();
	        List<Number> Valores = new ArrayList<>();
	        Valores.add(daoFunc.contarTotalFuncionariosFuncao("Atendente"));
	        Valores.add(daoFunc.contarTotalFuncionariosFuncao("Farmacêutico"));
	        Valores.add(daoFunc.contarTotalFuncionariosFuncao("Gerente"));
	        dataSet1.setData(Valores);
	        
	        List<String> coresGrafico = new ArrayList<>();
	        coresGrafico.add("rgb(132, 221, 99)");
	        coresGrafico.add("rgb(255, 99, 132)");
	        coresGrafico.add("rgb(255, 134, 0)");
	        dataSet1.setBackgroundColor(coresGrafico);
	        
	        Title title1 = new Title();
	        title1.setDisplay(true);
	        title1.setText("Funcionários");
	        options1.setTitle(title1);
	        
	        data1.addChartDataSet(dataSet1);
	        List<String> FuncoesNome = new ArrayList<>();
	        FuncoesNome.add("Atendentes");
	        FuncoesNome.add("Farmacêuticos");
	        FuncoesNome.add("Gerentes");
	        data1.setLabels(FuncoesNome);
	        
	        donutFuncao.setOptions(options1);
	        donutFuncao.setData(data1);
		
	}
	
	
	public void painelGerente() {
		int totalAtivos = 0;
		int totalMateria = 0;
		int totalEmbalagem = 0;
		int totalequi = 0;
		int totalEstoque = 0;
		totalAtivos = daoFunc.contarTotalFuncionarios();
		funcionarios.setTotalFuncionariosAtivos(totalAtivos);
		
		totalMateria  = DaoMateria.contarMateriaEstoque();
		totalEmbalagem = daoEmbalagem.contarEmbalagensEstoque();
		totalequi = daoequi.contarEquipamentosEstoque();
		totalEstoque = totalEmbalagem + totalMateria + totalequi;
		funcionarios.setTotalEstoque(totalEstoque);
		
		
		
		
	}
	
	
	
	
public String logar(){
		
		
		
			Funcionarios funcionariosUser = daoFuncionario.consultarFuncionario(funcionarios.getLogin(), funcionarios.getSenha());
			
			
			if (funcionariosUser != null && funcionariosUser.getFuncao().equalsIgnoreCase("Atendente")) {

				// Adicionar o usuario na sessao usuariologado
				FacesContext context = FacesContext.getCurrentInstance();
				ExternalContext externalContext = context.getExternalContext();

				HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
				HttpSession session = request.getSession();

				session.setAttribute("funcionariologado", funcionariosUser);
				
				funcaoFuncionarios.loadFuncaoFuncionarios(0, 5, null, null, "Atendente");
				
				carregarPaineisAtendente();
				
				
				
				return "home.jsf";
			}else if(funcionariosUser != null && funcionariosUser.getFuncao().equalsIgnoreCase("Farmacêutico")) {
				FacesContext context = FacesContext.getCurrentInstance();
				ExternalContext externalContext = context.getExternalContext();

				HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
				HttpSession session = request.getSession();

				session.setAttribute("funcionariologado", funcionariosUser);
				funcionariosUser.getFotoIconBase64();
				
				 funcaoFuncionarios.loadFuncaoFuncionarios(0, 4, null, null, "Farmacêutico");
				
				return "homeFarmaceutico.jsf";
			}else if(funcionariosUser != null && funcionariosUser.getFuncao().equalsIgnoreCase("Gerente")) {
				FacesContext context = FacesContext.getCurrentInstance();
				ExternalContext externalContext = context.getExternalContext();

				HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
				HttpSession session = request.getSession();

				session.setAttribute("funcionariologado", funcionariosUser);
				
				 painelGerente();
				return "homeGerente.jsf";
			}
			else {
				
			
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Login ou senha incorretos"));
			
			return "index.jsf";
		
			}
		
				
		
	}



	public String iconPerfil() {
		
		Funcionarios funcionariosUser = daoFuncionario.consultarFuncionario(funcionarios.getLogin(), funcionarios.getSenha());
		
		
		
		
		return  funcionariosUser.getFotoIconBase64();
	}


public String consultarEmail() throws Exception {
	
	 funcionarios =  daoFuncionario.consultarEmail(funcionarios.getEmail());
	System.out.println(funcionarios);
	
	
	return "RedefinirSenha2.jsf";
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
	

	
	
	
	
	
	

	
	public String deslogar() {
		try {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();


		
		
		externalContext.invalidateSession();
		
		FacesContext.getCurrentInstance().getExternalContext().redirect("index.jsf");
				
	}catch(Exception e) {
		e.printStackTrace();
	}

		return "";
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
	
	
	public void relatorioFunc() {
		try {
			
			String tipoExportar = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tipoExportar");
			List<Funcionarios> funcs = daoFunc.listar(Funcionarios.class);
			
			List dados = new ArrayList();
			dados.add(funcs);
			
			String fileUrl = relatorioService.gerarRelatorio(dados, new HashMap(), "funcionarios", "funcionarios");
			
			File donwloadFIle = new File(fileUrl);
			FileInputStream inputStream = new FileInputStream(donwloadFIle);
			
		
			
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			
		
			response.setContentLength((int) donwloadFIle.length());
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", donwloadFIle.getName());
			response.setHeader(headerKey, headerValue);
			
			OutputStream outputStream = response.getOutputStream();
			
			byte[] buffer = new byte[4096];
			int byteReader = -1;
			
			
			while((byteReader = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer,0,byteReader);
				
				
			}
			
			inputStream.close();
			outputStream.close();
			
		}catch (Exception e) {

			e.printStackTrace();
		}
		
	}
	
	public void download() throws IOException {
		
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		String fileDownload = params.get("FiledownloadId");
		
		Funcionarios funcionarioDownload = daoFunc.pesquisar(Long.valueOf(fileDownload), Funcionarios.class);
		
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		response.addHeader("Content-disposition", "attachment; filename="+funcionarioDownload.getNome()+"."+ funcionarioDownload.getExtensao());
		response.setContentType("application/octet-stream");
		response.setContentLength(funcionarioDownload.getFotoIconbase64original().length);
		response.getOutputStream().write(funcionarioDownload.getFotoIconbase64original());
		response.getOutputStream().flush();
		FacesContext.getCurrentInstance().responseComplete();
	}
	
	
	
	public void upload(FileUploadEvent event) throws IOException {
		
		arquivofoto = event.getFile();
		//Inicio processamento da imagem

			if(arquivofoto != null) {
				
				byte[] imagemByte = getByte(arquivofoto.getInputStream());
				funcionarios.setFotoIconbase64original(imagemByte); //Salva a imagem original
				
				//Transforma em bufferimage (Miniatura)
				
				BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imagemByte));
				
				
				//Pega o tipo de imagem
				
				int type = bufferedImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
				
				int largura = 200;
				int altura  = 200;
				
				//Criando a miniaura
				
				BufferedImage resizedImage = new BufferedImage(largura, altura, type);
				Graphics2D g = resizedImage.createGraphics();
				g.drawImage(bufferedImage, 0, 0, largura, altura, null);
				g.dispose();
				
				
				// Escrever novamente a imagem em tamanho menor 
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				
				String extensao = arquivofoto.getContentType().split("\\/")[1]; // Retorna: image/png
				ImageIO.write(resizedImage, extensao, baos);
				
				String miniImagem = "data:"+ arquivofoto.getContentType() + ";base64,"+ DatatypeConverter.printBase64Binary(baos.toByteArray());
				
				funcionarios.setFotoIconBase64(miniImagem);
				funcionarios.setExtensao(extensao);
			}else {
				
				
				byte[] imagemByte = getByte(arquivofoto.getInputStream());
				funcionarios.setFotoIconbase64original(imagemByte); //Salva a imagem original
				
				//Transforma em bufferimage (Miniatura)
				
				BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imagemByte));
				
				
				//Pega o tipo de imagem
				
				int type = bufferedImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
				
				int largura = 200;
				int altura  = 200;
				
				//Criando a miniaura
				
				BufferedImage resizedImage = new BufferedImage(largura, altura, type);
				Graphics2D g = resizedImage.createGraphics();
				g.drawImage(bufferedImage, 0, 0, largura, altura, null);
				g.dispose();
				
				
				// Escrever novamente a imagem em tamanho menor 
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				
				String extensao = arquivofoto.getContentType().split("\\/")[1]; // Retorna: image/png
				ImageIO.write(resizedImage, extensao, baos);
				
				String miniImagem = "data:"+ arquivofoto.getContentType() + ";base64,"+ DatatypeConverter.printBase64Binary(baos.toByteArray());
				
				funcionarios.setFotoIconBase64(miniImagem);
				funcionarios.setExtensao(extensao);
			}
		
		
		
	}
	

	
	private byte[] getByte(InputStream is) throws IOException {
		
		int len;
		int size = 1024;
		byte[] buf = null;
		
		if(is instanceof ByteArrayInputStream) {
			
			size = is.available();
			buf = new byte[size];
			len = is.read(buf, 0, size);
			
		}else {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			buf = new byte[size];
			
			while((len = is.read(buf, 0, size)) != -1) {
				bos.write(buf, 0, len);
			}
			
			buf = bos.toByteArray();
		}
		
		return buf;
	}
	
	
	
	
	
	public LazyDataTable<Funcionarios> getFuncaoFuncionarios() {
		return funcaoFuncionarios;
	}
	
	
	public BarChartModel getBarChart() {
		return BarChart;
	}
	
	public BarChartModel getBarChartFarmaceutico() {
		return BarChartFarmaceutico;
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
	
	public LineChartModel getLineChart() {
		return lineChart;
	}
	
	public DonutChartModel getDonutChart() {
		return donutChart;
	}
	
	public DonutChartModel getDonutFuncao() {
		return donutFuncao;
	}
	
	public void setArquivofoto(UploadedFile arquivofoto) {
		this.arquivofoto = arquivofoto;
	}
	
	public UploadedFile getArquivofoto() {
		return arquivofoto;
	}
	
	
	
	
}
