package br.gov.rs.parobe.scram.listener;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named(value = "documentoEncaminhamentoListener")
@SessionScoped
public class DocumentoEncaminhamentoListener implements Serializable {

	private static final long serialVersionUID = 1L;
	private String documentoEncaminhamento;
	private Map<String, String> documentosEncaminhamento;

	public DocumentoEncaminhamentoListener() {
		documentosEncaminhamento = new TreeMap<String, String>();
		documentosEncaminhamento.put("Boletim de Ocorrência", "Boletim de Ocorrência");
		documentosEncaminhamento.put("Ofício DDM", "Ofício DDM");
		documentosEncaminhamento.put("Relatório Informativo", "Relatório Informativo");
		documentosEncaminhamento.put("Termo de Aplicação de Medida Protetiva",
				"Termo de Aplicação de Medida Protetiva");
		documentosEncaminhamento.put("PAF", "PAF");
		documentosEncaminhamento.put("PDI", "PDI");
		documentosEncaminhamento.put("PIA", "PIA");
		documentosEncaminhamento.put("Outros", "Outros");
	}

	public String getDocumentoEncaminhamento() {
		return documentoEncaminhamento;
	}

	public void setDocumentoEncaminhamento(String documentoEncaminhamento) {
		this.documentoEncaminhamento = documentoEncaminhamento;
	}

	public Map<String, String> getDocumentosEncaminhamento() {
		return documentosEncaminhamento;
	}

	public void setDocumentosEncaminhamento(Map<String, String> documentosEncaminhamento) {
		this.documentosEncaminhamento = documentosEncaminhamento;
	}

}