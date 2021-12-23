package curso.spring.controller;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Component
public class ReportUtil implements Serializable {

	
	
	public byte[] gerarRelatorio(List Dados, String relatorio, ServletContext servletContext) throws JRException {
		
		JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(Dados);
		
		String caminhoJasper = servletContext.getRealPath("relatorios") + File.separator + relatorio + ".jasper";
		
		JasperPrint print = JasperFillManager.fillReport(caminhoJasper, new HashMap(), source);
		
		return JasperExportManager.exportReportToPdf(print);
	}
}
