package br.gov.rs.parobe.scram.uteis;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.component.export.ExcelOptions;
import org.primefaces.component.export.PDFOptions;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;

@Named(value = "exportaDocumentoCustom")
@ViewScoped
public class ExportaDocumentoCustom implements Serializable {

	private static final long serialVersionUID = 1L;

	private ExcelOptions excelOpt;
	private PDFOptions pdfOpt;

	public ExportaDocumentoCustom() {
		excelOpt = new ExcelOptions();
		excelOpt.setFacetBgColor("#f3f3f3");
		excelOpt.setFacetFontSize("10");
		excelOpt.setFacetFontStyle("BOLD");
		excelOpt.setCellFontSize("8");

		pdfOpt = new PDFOptions();
		pdfOpt.setFacetBgColor("#f3f3f3");
		pdfOpt.setFacetFontStyle("BOLD");
		pdfOpt.setFacetFontSize("8");
		pdfOpt.setCellFontSize("8");
	}

	public ExcelOptions getExcelOpt() {
		return excelOpt;
	}

	public void setExcelOpt(ExcelOptions excelOpt) {
		this.excelOpt = excelOpt;
	}

	public PDFOptions getPdfOpt() {
		return pdfOpt;
	}

	public void setPdfOpt(PDFOptions pdfOpt) {
		this.pdfOpt = pdfOpt;
	}
	
	public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);
      
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String logo = externalContext.getRealPath("") + File.separator + "resources" +  File.separator + "imagens" + File.separator + "header_pdf.jpg";
        pdf.add(Image.getInstance(logo));
    }
	
	
	public void preProcessPDFUsuarias(Object document) throws IOException, BadElementException, DocumentException {
		Document pdf = (Document) document;
		pdf.open();
		pdf.setPageSize(PageSize.A4);
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		String logo = externalContext.getRealPath("") + File.separator + "resources" +  File.separator + "imagens" + File.separator + "header_pdf.jpg";
		pdf.add(Image.getInstance(logo));
	}
	
	public void preProcessPDFUsuarios(Object document) throws IOException, BadElementException, DocumentException {
		Document pdf = (Document) document;
		pdf.open();
		pdf.setPageSize(PageSize.A4);
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		String logo = externalContext.getRealPath("") + File.separator + "resources" +  File.separator + "imagens" + File.separator + "header_usuarios.jpg";
		pdf.add(Image.getInstance(logo));
	}
	
}
