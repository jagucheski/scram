package br.gov.rs.parobe.scram.listener;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named(value = "etniaListener")
@SessionScoped
public class EtniaListener implements Serializable {

	private static final long serialVersionUID = 1L;
	private String etnia;
	private Map<String, String> etnias;

	public EtniaListener() {
		etnias = new TreeMap<String, String>();
		etnias.put("Branca", "Branca");
		etnias.put("Negro", "Negro");
		etnias.put("Ocidental", "Ocidental");
		etnias.put("Indígena", "Indígena");
		etnias.put("Pardo", "Pardo");
		etnias.put("Não Declarante", "Não Declarante");
	}

	public String getEtnia() {
		return etnia;
	}

	public void setEtnia(String etnia) {
		this.etnia = etnia;
	}

	public Map<String, String> getEtnias() {
		return etnias;
	}

	public void setEtinias(Map<String, String> etnias) {
		this.etnias = etnias;
	}

}
