package br.gov.rs.parobe.scram.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="dateConverter")
public class DateConverter extends DateTimeConverter {

	public DateConverter() {
        setPattern("dd/MM/yyyy");
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.length() != getPattern().length()) {
            throw new ConverterException("Formato inválido");
        }

        return super.getAsObject(context, component, value);
    }	
	
	
//	
//	@Override
//	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
//		try {
//			if (value == null || value.trim().length() <= 0) {
//				return null;
//			}
//			String id;
//			StringTokenizer st = new StringTokenizer(value, "-");
//			id = st.nextToken();
//			return CidadeDao.getInstance().getById(Integer.valueOf(id).intValue());
//		} catch (Exception e) {
//			Uteis.MensagemAtencao("Ops! Cidade inválida.");
//			return null;
//		}
//
//	}
//
//	@Override
//	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
//		String retorno = null;
//
//		try {
//			if (object != null) {
//				Cidade cidade = (Cidade) object;
//				retorno = (new StringBuilder(String.valueOf(cidade.getId()))).append("-").append(cidade.getNome()).append("-").append(cidade.getUf()).toString();
//			}
//		} catch (Exception e) {
//			Uteis.MensagemAtencao("Ops! Cidade inválida.");
//			return null;
//		}
//		return retorno;
//	}
}