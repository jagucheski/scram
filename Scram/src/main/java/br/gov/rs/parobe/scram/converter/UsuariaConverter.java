package br.gov.rs.parobe.scram.converter;

import java.util.StringTokenizer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.log4j.Logger;

import br.gov.rs.parobe.scram.dao.UsuariaDao;
import br.gov.rs.parobe.scram.model.Usuaria;
import br.gov.rs.parobe.scram.uteis.Uteis;

@FacesConverter(value="usuariaConverter")
public class UsuariaConverter implements Converter {

	static final Logger logger = Logger.getLogger("br.gov.rs.parobe.scram.controller.DenunciadoConverter");
	
	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		try {
			if (value == null || value.trim().length() <= 0) {
				return null;
			}
			String id;
			StringTokenizer st = new StringTokenizer(value, "-");
			id = st.nextToken();
			return UsuariaDao.getInstance().getById(Integer.valueOf(id).intValue());
		} catch (Exception e) {
			Uteis.MensagemAtencao("Ops! Usuária inválida.");
			logger.error(e.toString(), e);
			return null;
		}

	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		String retorno = null;

		try {
			if (object != null) {
				Usuaria usuaria = (Usuaria) object;
				retorno = (new StringBuilder(String.valueOf(usuaria.getId()))).append("-").append(usuaria.getNome()).append("-").append(usuaria.getCpf()).toString();
			}
		} catch (Exception e) {
			Uteis.MensagemAtencao("Ops! Usuária inválida.");
			logger.error(e.toString(), e);
			return null;
		}
		return retorno;
	}
}