package br.gov.rs.parobe.scram.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-01-30T21:17:19.551-0200")
@StaticMetamodel(ContatoUsuaria.class)
public class ContatoUsuaria_ {
	public static volatile SingularAttribute<ContatoUsuaria, Integer> id;
	public static volatile SingularAttribute<ContatoUsuaria, String> email;
	public static volatile SingularAttribute<ContatoUsuaria, String> telefoneResidencial;
	public static volatile SingularAttribute<ContatoUsuaria, String> telefoneComercial;
	public static volatile SingularAttribute<ContatoUsuaria, String> telefoneCelular;
}
