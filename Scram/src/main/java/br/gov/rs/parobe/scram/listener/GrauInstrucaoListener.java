package br.gov.rs.parobe.scram.listener;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named(value = "grauInstrucaoListener")
@SessionScoped
public class GrauInstrucaoListener implements Serializable {

	private static final long serialVersionUID = 1L;
	private String grauInstrucao;
	private Map<String, String> grausInstrucao;

	public GrauInstrucaoListener() {
		grausInstrucao = new TreeMap<String, String>();
		grausInstrucao.put("Sem instrução e menos de 1 ano de estudo", "Sem instrução e menos de 1 ano de estudo");
		grausInstrucao.put("Ensino fundamental incompleto", "Ensino fundamental incompleto");
		grausInstrucao.put("Ensino fundamental completo", "Ensino fundamental completo");
		grausInstrucao.put("Ensino médio incompleto", "Ensino médio incompleto");
		grausInstrucao.put("Ensino médio completo", "Ensino médio completo");
		grausInstrucao.put("Ensino superior incompleto", "Ensino superior incompleto");
		grausInstrucao.put("Ensino superior completo", "Ensino superior completo");
		grausInstrucao.put("Não determinado", "Não determinado");
	}
	
	public String getGrauInstrucao() {
		return grauInstrucao;
	}

	public void setGrauInstrucao(String grauInstrucao) {
		this.grauInstrucao = grauInstrucao;
	}

	public Map<String, String> getGrausInstrucao() {
		return grausInstrucao;
	}

	public void setGrausInstrucao(Map<String, String> grausInstrucao) {
		this.grausInstrucao = grausInstrucao;
	}

	
}
