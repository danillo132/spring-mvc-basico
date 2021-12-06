package service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import br.com.Model.Funcionarios;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

public class RelatorioService implements Serializable {


	private static final long serialVersionUID = 1L;
	
	private HttpServletResponse response;
	private FacesContext context;
	private ByteArrayOutputStream baos;
	private InputStream stream;
	
	public RelatorioService() {
		this.context = FacesContext.getCurrentInstance();
		this.response = (HttpServletResponse) context.getExternalContext().getResponse();
		
	}
	
	public void gerarRelatorio(List<?> funcionarios,String classe) throws IOException {
		stream = this.getClass().getResourceAsStream("/br/com/Relatorios/"+classe+".jasper");
		Map<String, Object> params = new HashMap<String, Object>();
		baos = new ByteArrayOutputStream();
		ServletOutputStream servletOutputStream = response.getOutputStream();
		try {
			JasperReport report = (JasperReport) JRLoader.loadObject(stream);
			JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, new JRBeanCollectionDataSource(funcionarios));
			
			
			JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
			
		
			response.reset();
			response.setContentType("application/pdf");
			response.setContentLength(baos.size());
			response.setHeader("Content-disposition", "inline; filename = relatorio.pdf");
			servletOutputStream.write(baos.toByteArray());
			servletOutputStream.flush();
			servletOutputStream.close();
			
			context.responseComplete();
		} catch (JRException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
