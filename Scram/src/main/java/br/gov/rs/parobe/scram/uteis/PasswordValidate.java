package br.gov.rs.parobe.scram.uteis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidate {

	private static Pattern pswNamePtrn = Pattern.compile("[^\\s]{6,}");

	public static boolean validatePassword(String userName) {

		Matcher mtch = pswNamePtrn.matcher(userName);
		if (mtch.matches()) {
			return true;
		}
		return false;
	}

}