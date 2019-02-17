package br.gov.rs.parobe.scram.listener;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named(value = "referenciadaServicoListener")
@SessionScoped
public class ReferenciadaServicoListener implements Serializable {

	private static final long serialVersionUID = 1L;
	private String referenciadaServico;
	private Map<String, String> referenciadaServicos;

	public ReferenciadaServicoListener() {
		referenciadaServicos = new TreeMap<String, String>();
		referenciadaServicos.put("Procura Espontânea", "Procura Espontânea");
		referenciadaServicos.put("Busca Ativa", "Busca Ativa");
		referenciadaServicos.put("CRAS", "CRAS");
		referenciadaServicos.put("CREAS", "CREAS");
		referenciadaServicos.put("NAPIA", "NAPIA");
		referenciadaServicos.put("CAPS", "CAPS");
		referenciadaServicos.put("Conselhos de Direitos", "Conselhos de Direitos");
		referenciadaServicos.put("Conselho Tutelar", "Conselho Tutelar");
		referenciadaServicos.put("Delegacia", "Delegacia");
		referenciadaServicos.put("Defensoria Pública", "Defensoria Pública");
		referenciadaServicos.put("Ministério Público", "Ministério Público");
		referenciadaServicos.put("Vara da Infância e Juventura", "Vara da Infância e Juventura");
		referenciadaServicos.put("Saúde", "Saúde");
		referenciadaServicos.put("Educação", "Educação");
		referenciadaServicos.put("Entidades da Rede Socioassistencial", "Entidades da Rede Socioassistencial");
		referenciadaServicos.put("Outras Políticas Públicas", "Outras Políticas Públicas");
	}

	public String getReferenciadaServico() {
		return referenciadaServico;
	}

	public void setReferenciadaServico(String referenciadaServico) {
		this.referenciadaServico = referenciadaServico;
	}

	public Map<String, String> getReferenciadaServicos() {
		return referenciadaServicos;
	}

	public void setReferenciadaServicos(Map<String, String> referenciadaServicos) {
		this.referenciadaServicos = referenciadaServicos;
	}

}
