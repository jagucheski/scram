package br.gov.rs.parobe.scram.listener;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named(value = "religiaoListener")
@SessionScoped
public class ReligiaoListener implements Serializable {

	private static final long serialVersionUID = 1L;
	private String religiao;
	private Map<String, String> religioes;

	public ReligiaoListener() {
		religioes = new TreeMap<String, String>();
		religioes.put("Católica", "Católica");
		religioes.put("Evangélica", "Evangélica");
//		religioes.put("Igreja de Jesus Cristo dos Santos dos Últimos Dias",
//				"Igreja de Jesus Cristo dos Santos dos Últimos Dias");
		religioes.put("Testemunhas de Jeová", "Testemunhas de Jeová");
		religioes.put("Espiritualista", "Espiritualista");
		religioes.put("Espírita", "Espírita");
		religioes.put("Umbanda", "Umbanda");
		religioes.put("Candomblé", "Candomblé");
		religioes.put("Judaismo", "Judaismo");
		religioes.put("Hinduismo", "Hinduismo");
		religioes.put("Budismo", "Budismo");
		religioes.put("Islamismo", "Islamismo");
		religioes.put("Tradições Esotéricas", "Tradições Esotéricas");
		religioes.put("Tradições Indígenas", "Tradições Indígenas");
		religioes.put("Outra", "Outra");
		religioes.put("Sem religião", "Sem religião");
		religioes.put("Ateu", "Ateu");
		religioes.put("Agnóstico", "Agnóstico");
	}

	public String getReligiao() {
		return religiao;
	}

	public void setReligiao(String religiao) {
		this.religiao = religiao;
	}

	public Map<String, String> getReligioes() {
		return religioes;
	}

	public void setReligioes(Map<String, String> religioes) {
		this.religioes = religioes;
	}

}
