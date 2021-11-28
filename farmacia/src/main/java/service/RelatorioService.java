package service;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

public class RelatorioService implements Serializable {


	private static final long serialVersionUID = 1L;
	
	
	private static final String FOLDER_RELATORIOS = "/br.com.Relatorios";
	private static final String SUBREPORT_DIR = "SUBREPORT_DIR";
	private  String SEPARATOR = File.separator;
	private  String caminhoArquivoRelatorio = null;
	private JRExporter exporter = null;
	private String caminhoSubReport_Dir = "";
	private File arquivoGerado = null;
	
	
	public String gerarRelatorio(List<?> listaDataBeanCollection, HashMap parametrosRelatorio,
			String nomeRelatorioJasper,String nomeRelatorioSaida) throws Exception {
		
		//Cria a lista de collection dataSource de beans QUE CARREGAM OS DADOS PARA O RELATORIO
		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listaDataBeanCollection, false);
		
		String caminhoRelatorio = "C:\\Users\\danil\\git\\repositorioPadrao\\farmacia\\src\\main\\java\\br\\com\\Relatorios";
		
		File file = new File(caminhoRelatorio + SEPARATOR + nomeRelatorioJasper + ".jasper");
		
		if(caminhoRelatorio == null || (caminhoRelatorio != null && caminhoRelatorio.isEmpty())
				|| !file.exists()) {
			
			caminhoRelatorio = this.getClass().getResource(FOLDER_RELATORIOS).getPath();
			SEPARATOR = "";
			
			
		}
		//caminho para imagens
		parametrosRelatorio.put("REPORT_PARAMETERS_IMG", caminhoRelatorio);
		
		
		//caminho completo até o relatório compilado indicado
		String caminhoArquivosJasper =  caminhoRelatorio + SEPARATOR + nomeRelatorioJasper + ".jasper";
		
		//faz o carregamento do relatorio
		
		JasperReport relatorioJasper  = (JasperReport) JRLoader.loadObjectFromFile(caminhoArquivosJasper);
		
		caminhoSubReport_Dir = caminhoRelatorio + SEPARATOR;
		parametrosRelatorio.put(SUBREPORT_DIR, caminhoSubReport_Dir);
		
		
		//carrega o arquivo
		
		JasperPrint impressoraJasper = JasperFillManager.fillReport(relatorioJasper, parametrosRelatorio, jrbcds);
		
		
		exporter = new JRPdfExporter();
				
		//caminho relatorio exportado
		
		caminhoArquivoRelatorio = caminhoRelatorio + SEPARATOR + nomeRelatorioSaida + ".pdf";
		
		
		//criar novo arquivo exportado
		
		arquivoGerado = new File(caminhoArquivoRelatorio);
		
		
		//prepara a impressão
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, impressoraJasper);
		
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE, arquivoGerado);
		
		//executa a exportação
		exporter.exportReport();
		
		arquivoGerado.deleteOnExit();
		
		return caminhoArquivoRelatorio;
	}
	
}
