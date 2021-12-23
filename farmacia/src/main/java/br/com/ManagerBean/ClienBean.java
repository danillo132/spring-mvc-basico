package br.com.ManagerBean;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.donut.DonutChartDataSet;
import org.primefaces.model.charts.donut.DonutChartModel;
import org.primefaces.model.charts.donut.DonutChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.file.UploadedFile;

import com.google.gson.Gson;

import br.com.DAO.DaoClientes;
import br.com.DAO.DaoFunc;
import br.com.DAO.DaoOrcamentos;
import br.com.DAO.DaoPedidos;
import br.com.Model.Clientes;
import br.com.Model.Fornecedores;
import br.com.Model.Funcionarios;
import br.com.Model.Orcamentos;
import br.com.Model.Pedidos;
import br.com.emails.EnviarEmail;
import br.com.lazyDataTable.LazyClientes;
import service.RelatorioService;

@ManagedBean(name = "ClienBean")
@SessionScoped
public class ClienBean {

	private Clientes clientes = new Clientes();
	private Funcionarios funcionario = new Funcionarios();
	private DaoFunc<Funcionarios> daoFuncionario = new DaoFunc<Funcionarios>();
	private DaoClientes<Clientes> daoCliente = new DaoClientes<Clientes>();
	private LazyClientes<Clientes> clientesLista = new LazyClientes<Clientes>();
	private DaoPedidos<Pedidos> daoPedidos = new DaoPedidos<Pedidos>();
	private DaoOrcamentos<Orcamentos> daoOrcamentos = new DaoOrcamentos<Orcamentos>();
	private BarChartModel BarChart = new BarChartModel();
	private BarChartModel BarChartPedidos = new BarChartModel();
	private List<Clientes> listaClientes = new ArrayList<Clientes>();
	private DonutChartModel donutChart = new DonutChartModel();
	private DonutChartModel donutChartCidades = new DonutChartModel();
	private RelatorioService relatorioService = new RelatorioService();
	
	private UploadedFile arquivofoto;

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
		graficosClientes();
	}

	public String salvar() throws Exception {
		clientes.setFuncionarios(funcionario);

		enviarEmailCliente(clientes.getEmail(), "Cadastro na Fármacia Momentum", clientes.getNome());
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
	
	
	public void graficosClientes() {
		
		ChartData data = new ChartData();
        
        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Orçamentos por cliente");
        List<String> nomesClientes = new ArrayList<String>();
        List<Number> OrcamentosClientes = new ArrayList<Number>();
        
        listaClientes = daoCliente.listar(Clientes.class);
        
        for (Clientes clientesdalista : listaClientes) {
			
        	nomesClientes.add(clientesdalista.getNome());
        	OrcamentosClientes.add(daoOrcamentos.contarOrcamentos(clientesdalista.getId()));
        	
		}
        
       
        data.setLabels(nomesClientes);
        barDataSet.setData(OrcamentosClientes);
     
       
        
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
        
        
        
ChartData dataPedidos = new ChartData();
        
        BarChartDataSet barDataSetPedidos = new BarChartDataSet();
        barDataSetPedidos.setLabel("Pedidos por cliente");
        List<String> nomesClientes1 = new ArrayList<String>();
        List<Number> PedidosClientes = new ArrayList<Number>();
        
        listaClientes = daoCliente.listar(Clientes.class);
        
        for (Clientes clientesdalista : listaClientes) {
			
        	nomesClientes1.add(clientesdalista.getNome());
        	PedidosClientes.add(daoPedidos.contarPedidos(clientesdalista.getId()));
        	
		}
        
       
        dataPedidos.setLabels(nomesClientes1);
        barDataSetPedidos.setData(PedidosClientes);
     
       
        
        List<String> bgColor1 = new ArrayList<>();
        bgColor1.add("rgba(255, 99, 132, 0.2)");
        bgColor1.add("rgba(255, 159, 64, 0.2)");
        bgColor1.add("rgba(255, 205, 86, 0.2)");
        bgColor1.add("rgba(75, 192, 192, 0.2)");
        bgColor1.add("rgba(54, 162, 235, 0.2)");
        bgColor1.add("rgba(153, 102, 255, 0.2)");
        bgColor1.add("rgba(201, 203, 207, 0.2)");
        barDataSetPedidos.setBackgroundColor(bgColor1);
        
        List<String> borderColor1 = new ArrayList<>();
        borderColor1.add("rgb(255, 99, 132)");
        borderColor1.add("rgb(255, 159, 64)");
        borderColor1.add("rgb(255, 205, 86)");
        borderColor1.add("rgb(75, 192, 192)");
        borderColor1.add("rgb(54, 162, 235)");
        borderColor1.add("rgb(153, 102, 255)");
        borderColor1.add("rgb(201, 203, 207)");
        barDataSetPedidos.setBorderColor(borderColor1);
        barDataSetPedidos.setBorderWidth(1);
        
        dataPedidos.addChartDataSet(barDataSetPedidos);
        
       
        

        //Data
        BarChartPedidos.setData(dataPedidos);
        
        //Options
        BarChartOptions options1 = new BarChartOptions();
        CartesianScales cScales1 = new CartesianScales();
        CartesianLinearAxes linearAxes1 = new CartesianLinearAxes();
        CartesianLinearTicks ticks1 = new CartesianLinearTicks();
        ticks1.setBeginAtZero(true);
        linearAxes1.setTicks(ticks);
        cScales1.addYAxesData(linearAxes);
        options1.setScales(cScales);
        
        Title title1 = new Title();
        title1.setDisplay(true);
        title1.setText("Gráfico");
        options1.setTitle(title);

        Legend legend1 = new Legend();
        legend1.setDisplay(true);
        legend1.setPosition("top");
        LegendLabel legendLabels1 = new LegendLabel();
        legendLabels1.setFontStyle("bold");
        legendLabels1.setFontColor("#2980B9");
        legendLabels1.setFontSize(24);
        legend1.setLabels(legendLabels1);
        options1.setLegend(legend1);

        BarChartPedidos.setOptions(options1);
		
        
        ChartData dataClientes = new ChartData();
		 DonutChartOptions optionsClientes = new DonutChartOptions();
	        DonutChartDataSet dataSet = new DonutChartDataSet();
	        List<Number> values = new ArrayList<>();
	        values.add(daoCliente.contarTotalClientes());
	        values.add(daoCliente.contarTotalClientesInativos());
	        dataSet.setData(values);
	        
	        Title titleClientes = new Title();
	        titleClientes.setDisplay(true);
	        titleClientes.setText("Clientes");
	        optionsClientes.setTitle(titleClientes);
	        
	        List<String> bgColors = new ArrayList<>();
	        bgColors.add("rgb(132, 221, 99)");
	        bgColors.add("rgb(255, 99, 132)");
	       
	        dataSet.setBackgroundColor(bgColors);
	        
	        dataClientes.addChartDataSet(dataSet);
	        List<String> labels = new ArrayList<>();
	        labels.add("Ativos");
	        labels.add("Inativos");
	        dataClientes.setLabels(labels);
	       
	        donutChart.setOptions(optionsClientes);
	        donutChart.setData(dataClientes);
	        
	        
	        ChartData dataCidades = new ChartData();
			 DonutChartOptions optionsCidades = new DonutChartOptions();
		        DonutChartDataSet dataSetCidade = new DonutChartDataSet();
		        
		        
		        Title titleClientes1 = new Title();
		        titleClientes1.setDisplay(true);
		        titleClientes1.setText("Clientes por cidade");
		        optionsCidades.setTitle(titleClientes1);
		        
		        List<String> cores = new ArrayList<>();
		        cores.add("rgb(132, 221, 99)");
		        cores.add("rgb(255, 99, 132)");
		        cores.add("rgb(255, 205, 86)");
		        cores.add("rgb(75, 192, 192)");
		        cores.add("rgb(54, 162, 235)");
		        cores.add("rgb(153, 102, 255)");
		        cores.add("rgb(201, 203, 207)");
		       
		        dataSetCidade.setBackgroundColor(cores);
		        
		        dataCidades.addChartDataSet(dataSetCidade);
		        List<String> labels1 = new ArrayList<>();
		        
		        List<Number> values1 = new ArrayList<>();
		     
		        
		        for (Clientes clientesCidade : listaClientes) {
					
		        
		        		labels1.add(clientesCidade.getLocalidade());
		        	
				}
		        
		    
		        
		        Set<String> set = new HashSet<>(labels1);
		        
		        labels1.clear();
		        labels1.addAll(set);
		       
		        
		        for (String string : labels1) {
					
		        	values1.add(daoCliente.contarClientesPorCidade(string));
				}
		       
		        
		        dataCidades.setLabels(labels1);
		        dataSetCidade.setData(values1);
		        donutChartCidades.setOptions(optionsCidades);
		        donutChartCidades.setData(dataCidades);
        
		
	}

	public void removerCliente() {
		if(daoPedidos.contarPedidosEntregues(clientes.getId()) != 0) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro: ", "Existem pedidos para esse cliente! POR FAVOR cancele os pedidos!"));
			return;
		}
		
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
			
			
		
	}
	
	public void enviarEmailCliente(String emailCliente, String assunto, String nomeCliente) throws Exception {
		
StringBuilder stringBuilderTextoEmail = new StringBuilder();
		stringBuilderTextoEmail.append("<html>" +
                "<body style=\"font-family:'Roboto', sans-serif; src: url('https://fonts.googleapis.com/css2?family=Roboto:wght@500;900&display=swap'); color: #E8EDDF\">" +"<div style=\"width: 100%; height: 100vh; display: block;\">");
		stringBuilderTextoEmail.append("<div style=\"width: 100%; height: 20%; background-color: #45DB3E\">"
				+ "<h2 style=\"text-align:center;  color: #E8EDDF; \">Olá,"+nomeCliente+"</h2>"+
				
		"<h4 style=\"text-align:center; color: #E8EDDF\">Nós da Fármacia Momentum Agradecemos Pela Preferência, quando um orçamento/pedido for cadastrado você receberá um email como esse juntamente com um pdf contendo informações referentes ao cadastro </h4> <br><br>");
		stringBuilderTextoEmail.append("</div>");
		stringBuilderTextoEmail.append("<div style=\"margin-left: 300px !important;\">");
			stringBuilderTextoEmail.append("<img src=https://im3.ezgif.com/tmp/ffffff-ezgif-3-e8f958138b3a-gif-jpg/frame_122_delay-0.02s.jpg style=\"margin-left: 50px;\" />");
			stringBuilderTextoEmail.append("</div>");
		stringBuilderTextoEmail.append("</div>");
		stringBuilderTextoEmail.append("<div style=\"width: 100%; height: 40%; background-color: #45DB3E\">"
				+ "<h4 style=\"text-align:center;  color: #E8EDDF; \">Contato: (11) 37841-4012 (11) 98731-9894       farmacia@momentum.com.br\n Endereço: Rua ministro "
				+ "Alves Silva, Osasco-SP CEP: 19432-120 "+"<br></br>"
				+ "Todos os direitos reservados @Momentum</h4>"+
                "</body>" +
                "</html>");
		
		
		EnviarEmail email = new EnviarEmail(emailCliente, "Farmácia Momentum", assunto, 
				stringBuilderTextoEmail.toString());
		
		email.enviaEmail(true);
	}
	
	public void relatorioCliente() {
		try {
			
			List<Clientes> clientes = daoCliente.listar(Clientes.class);
			
			
			
			relatorioService.gerarRelatorio(clientes,"clientes");
			
			
			
		}catch (Exception e) {

			e.printStackTrace();
		}
		
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

	public void upload(FileUploadEvent image) throws Exception {

		arquivofoto = image.getFile();
		
		if(arquivofoto != null) {
			
			byte[] imagemByte = getByte(arquivofoto.getInputStream());
			clientes.setFotoIconbase64original(imagemByte); //Salva a imagem original
			
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
			
			clientes.setFotoIconBase64(miniImagem);
			clientes.setExtensao(extensao);
		}
		
	}
	
public void download() throws IOException {
		
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		String fileDownload = params.get("FiledownloadId");
		
		Clientes clienteDowload = daoCliente.pesquisar(Long.valueOf(fileDownload), Clientes.class);
		
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		response.addHeader("Content-disposition", "attachment; filename="+clienteDowload.getNome()+"."+ clienteDowload.getExtensao());
		response.setContentType("application/octet-stream");
		response.setContentLength(clienteDowload.getFotoIconbase64original().length);
		response.getOutputStream().write(clienteDowload.getFotoIconbase64original());
		response.getOutputStream().flush();
	
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
	
	public void setArquivofoto(UploadedFile arquivofoto) {
		this.arquivofoto = arquivofoto;
	}
	
	public UploadedFile getArquivofoto() {
		return arquivofoto;
	}

	public void setDaoCliente(DaoClientes<Clientes> daoCliente) {
		this.daoCliente = daoCliente;
	}

	public BarChartModel getBarChart() {
		return BarChart;
	}
	
	public BarChartModel getBarChartPedidos() {
		return BarChartPedidos;
	}
	
	public DonutChartModel getDonutChart() {
		return donutChart;
	}
	
	public DonutChartModel getDonutChartCidades() {
		return donutChartCidades;
	}
}
