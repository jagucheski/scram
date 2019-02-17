package br.gov.rs.parobe.scram.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-01-30T21:17:19.561-0200")
@StaticMetamodel(Usuario.class)
public class Usuario_ {
	public static volatile SingularAttribute<Usuario, Integer> id;
	public static volatile SingularAttribute<Usuario, String> nome;
	public static volatile SingularAttribute<Usuario, String> cpf;
	public static volatile SingularAttribute<Usuario, String> email;
	public static volatile SingularAttribute<Usuario, String> tel_residencial;
	public static volatile SingularAttribute<Usuario, String> tel_celular;
	public static volatile SingularAttribute<Usuario, String> endereco;
	public static volatile SingularAttribute<Usuario, Boolean> status;
	public static volatile SingularAttribute<Usuario, String> perfil;
	public static volatile SingularAttribute<Usuario, String> senha;
	public static volatile SingularAttribute<Usuario, Cidade> cidade;
}
