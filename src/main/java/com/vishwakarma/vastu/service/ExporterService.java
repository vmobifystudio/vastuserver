package com.vishwakarma.vastu.service;

import java.io.ByteArrayOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.vishwakarma.vastu.exception.VastuException;

@Service
public class ExporterService {

	public static final String MEDIA_TYPE_EXCEL = "application/vnd.ms-excel";
	public static final String MEDIA_TYPE_PDF = "application/pdf";
	public static final String EXTENSION_TYPE_EXCEL = "xls";
	public static final String EXTENSION_TYPE_PDF = "pdf";
	
	private final static Logger log = LoggerFactory.getLogger(ExporterService.class);

	public static void export(String fileName,
			String type,
			JasperPrint jp, 
			HttpServletResponse response
			) {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		if (type.equalsIgnoreCase(EXTENSION_TYPE_EXCEL)) {
			// Export to output stream
			exportXls(jp, baos);
			 
			// Set our response properties
			// Here you can declare a custom filename
			String formattedFileName = fileName + ".xls";
			response.setHeader("Content-Disposition", "inline; filename=" + formattedFileName);
			
			// Set content type
			response.setContentType(MEDIA_TYPE_EXCEL);
			response.setContentLength(baos.size());
			
			
		}else if (type.equalsIgnoreCase(EXTENSION_TYPE_PDF)) {
			// Export to output stream
			exportPdf(jp, baos);
			 
			// Set our response properties
			// Here you can declare a custom filename
			String formattedFileName = fileName + ".pdf";
			response.setHeader("Content-Disposition", "inline; filename=" + formattedFileName);
			
			// Set content type
			response.setContentType(MEDIA_TYPE_PDF);
			response.setContentLength(baos.size());
			
			
			
		} else {
				throw new VastuException("No type set for type " + type);
		}
		
		write(response, baos);
		
	}
	
	private static void exportXls(JasperPrint jp, ByteArrayOutputStream baos) {
		// Create a JRXlsExporter instance
		JRXlsExporter exporter = new JRXlsExporter();
		 
		// Here we assign the parameters jp and baos to the exporter
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
		 
		// Excel specific parameters
		exporter.setParameter(JRXlsAbstractExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRXlsAbstractExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		 
		try {
			exporter.exportReport();
			
		} catch (JRException e) {
			throw new VastuException(e);
		}
	}
	
	private static void exportPdf(JasperPrint jp, ByteArrayOutputStream baos) {
		// Create a JRXlsExporter instance
		JRPdfExporter exporter = new JRPdfExporter();
		 
		// Here we assign the parameters jp and baos to the exporter
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
		 
		try {
			exporter.exportReport();
			
		} catch (JRException e) {
			throw new VastuException(e);
		}
	}
	/**
	* Writes the report to the output stream
	*/
	private static void write(HttpServletResponse response,
			ByteArrayOutputStream baos) {
		 
		try {
			log.debug(baos.size()+"");
			
			// Retrieve output stream
			ServletOutputStream outputStream = response.getOutputStream();
			// Write to output stream
			baos.writeTo(outputStream);
			// Flush the stream
			outputStream.flush();
			
		} catch (Exception e) {
			log.error("Unable to write report to the output stream");
			throw new RuntimeException(e);
		}
	}
}
