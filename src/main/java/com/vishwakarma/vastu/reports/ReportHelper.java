package com.vishwakarma.vastu.reports;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vishwakarma.vastu.exception.FileSystemException;
import com.vishwakarma.vastu.exception.ReportException;
import com.vishwakarma.vastu.exception.VastuException;
import com.vishwakarma.vastu.reports.ReportLayout.Column;
import com.vishwakarma.vastu.service.ExporterService;

/**
 * Base representation of a Report
 * @author Vishal Pawale
 *
 */
public class ReportHelper {

	private final static Logger log = LoggerFactory.getLogger(ReportHelper.class);

	//Map<String, Object> reportParams;	
	JasperPrint generatedReport = null;
	JasperReportBuilder reportBuilder = null;
	ReportDataProvider provider;
	
	public ReportHelper(ReportDataProvider provider) {
		this.provider = provider;
	}
		
//	abstract protected List<? extends Object> fetchReportData (Map<String, Object> reportParams);	
	
	public JasperPrint generateReport(Map<String, Object> reportParams) throws ReportException {
		List<? extends Object> data = provider.fetchReportData(reportParams);
		if (null!=generatedReport) {
			return generatedReport;
		}
		
		if (data.isEmpty()) {
			throw new ReportException("No data found for the report");
		}
		
		try {
			
			//FastReportBuilder drb = new FastReportBuilder();
			reportBuilder = report();
			
			ReportLayout layout = provider.getReportLayout(reportParams);
			
	  		StyleBuilder boldStyle         = stl.style().bold();
	  		StyleBuilder boldCenteredStyle = stl.style(boldStyle)
	  		                                    .setHorizontalAlignment(HorizontalAlignment.CENTER);
	  		StyleBuilder columnTitleStyle  = stl.style(boldCenteredStyle)
	  		                                    .setBorder(stl.pen1Point())
	  		                                    .setBackgroundColor(Color.LIGHT_GRAY)
	  		                                    .setBold(false); //represents the column header
	  		
	  		StyleBuilder titleStyle = stl.style(boldCenteredStyle)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setFontSize(15);
	  		
	  		//report header
	  		/*org.springframework.core.io.Resource logo = new ClassPathResource("com/smart/shopping/logo.png");
	  		reportBuilder.title(cmp.image(logo.getInputStream()).setHeight(10).setWidth(30));*/
	  		reportBuilder.addTitle(cmp.text(provider.getTitle(reportParams)).setStyle(titleStyle));
	  		reportBuilder.addTitle(cmp.gap(500, 10));
	  		reportBuilder.addTitle(cmp.text(provider.getSubTitle(reportParams)));
	  		
	  		//report column header
	  		reportBuilder.setColumnTitleStyle(columnTitleStyle)
				   .highlightDetailEvenRows();
	  		if (layout!=null) {
	  			for (Column column : layout.getColumns()) {
	  				reportBuilder.addColumn(col.column(column.getName(), column.getField(), column.getClassName().getClass()));
				}
	  		}
	  		
	  		//report footer
	  		//reportBuilder.pageFooter(cmp.text(provider.getFooter(reportParams)));
	  		//reportBuilder.pageFooter(provider.getFooter(reportParams));
	  		//reportBuilder.add
	  		//provider.setFooter(reportBuilder);
	  		reportBuilder.pageFooter(cmp.text(provider.getFooter(reportParams)).setStretchWithOverflow(true).setFixedRows(7));
	  		
	  		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(data);
	  		reportBuilder.setDataSource(ds);
	  		
	  		reportBuilder.build();
	  		generatedReport = reportBuilder.toJasperPrint();
	  		
	  	} catch (Exception e) {
			e.printStackTrace();
			throw new VastuException("Error generating report. Details: "+e.getMessage(), e);
		}

		return generatedReport;
	}
	
	/**
	 * Export and write data directly to http response
	 * @param response
	 * @param fileName
	 * @param type
	 */
	public void exportReport(HttpServletResponse response, String fileName ,String type) {
		ExporterService.export(fileName, type, generatedReport, response);
	}
	
	public void exportReportToTempFile (String filename, String type) throws FileSystemException, ReportException {
		String tempDir = System.getProperty("java.io.tmpdir",".");
		if (!tempDir.endsWith("/")) {
			tempDir = tempDir+"/";
		}
		
		filename= filename+"."+type;
  		
		OutputStream output = null;
		try {
			output = new FileOutputStream(new File(tempDir + filename));			
		} catch (FileNotFoundException e) {
			log.error("directory("+tempDir+") is not found");
			e.printStackTrace();
			throw new FileSystemException(e);
		}
  		if (type.equals("xls")) {
  			try {
				reportBuilder.toXls(output);
				log.debug("Report exported to file: "+filename);
			} catch (DRException e) {
				e.printStackTrace();
				throw new ReportException(e);
			}
  		}
  		else {
  			try {
  				JasperExportManager.exportReportToPdfStream(generatedReport, output);
  				log.debug("Report exported to file: "+filename);
  		  	} catch (JRException e) {
  				e.printStackTrace();
  				throw new ReportException(e);
  			}
  		}
		

	}
	
}
