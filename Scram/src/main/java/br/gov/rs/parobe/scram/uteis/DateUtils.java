package br.gov.rs.parobe.scram.uteis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public DateUtils() {
	}

	public static String getToday() {
		String retorno = null;
		try {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			retorno = sdf.format(date);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return retorno;
	}

	public static boolean isBefore(Date data) {
		boolean retorno = true;
		if (data.before(new Date())) {
			retorno = false;
		}
		return retorno;
	}

	public static boolean isBefore(String dataTemp) {
		boolean retorno = true;
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date data = format.parse(dataTemp);
			if (data.before(new Date())) {
				retorno = false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return retorno;
	}

	public static int getPeriodo(String dataTemp) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar data1 = Calendar.getInstance();
		Calendar data2 = Calendar.getInstance();
		try {
			data1.setTime(sdf.parse(dataTemp));
			data2.setTime(new Date());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int dias = data2.get(6) - data1.get(6);
		return dias;
	}

	public static String getProximaData(String dataTemp, int periodo) {
		String retorno = null;
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		try {
			Date suaData = format.parse(dataTemp);
			c.setTime(suaData);
			c.set(5, c.get(5) + periodo);
			Date date = c.getTime();
			retorno = format.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return retorno;
	}

	public static String getInicioPeriodoForm(Date date) {
		String retorno = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			retorno = (new StringBuilder(String.valueOf(retorno))).append(sdf.format(date)).toString();
			retorno = (new StringBuilder(String.valueOf(retorno))).append(" 00:00").toString();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return retorno;
	}

	public static String getFimPeriodoForm(Date date) {
		String retorno = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			retorno = (new StringBuilder(String.valueOf(retorno))).append(sdf.format(date)).toString();
			retorno = (new StringBuilder(String.valueOf(retorno))).append(" 23:59").toString();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return retorno;
	}
}