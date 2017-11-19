package br.com.vitafarma.web.shared.dtos;

public class UsuarioDTO extends AbstractDTO<String> implements
		Comparable<UsuarioDTO> {
	private static final long serialVersionUID = 5815525344760896272L;

	// Propriedades
	public static final String PROPERTY_USUARIO_VERSION = "usuarioVersion";
	public static final String PROPERTY_USUARIO_NOME = "usuarioNome";
	public static final String PROPERTY_USUARIO_EMAIL = "usuarioEmail";
	public static final String PROPERTY_USUARIO_USERNAME = "usuarioUsername";
	public static final String PROPERTY_USUARIO_PASSWORD = "usuarioMaxPassword";
	public static final String PROPERTY_USUARIO_ENABLED = "usuarioEnabled";
	public static final String PROPERTY_USUARIO_PROFESSOR_ID = "usuarioProfessorId";
	public static final String PROPERTY_USUARIO_PROFESSOR_STRING = "usuarioProfessorString";
	public static final String PROPERTY_USUARIO_AUTHORITY_USERNAME = "usuarioAuthorityUsername";

	public UsuarioDTO() {
	}

	public void setVersion(Integer value) {
		this.set(PROPERTY_USUARIO_VERSION, value);
	}

	public Integer getVersion() {
		return this.get(PROPERTY_USUARIO_VERSION);
	}

	public void setNome(String value) {
		this.set(PROPERTY_USUARIO_NOME, value);
	}

	public String getNome() {
		return this.get(PROPERTY_USUARIO_NOME);
	}

	public void setEmail(String value) {
		this.set(PROPERTY_USUARIO_EMAIL, value);
	}

	public String getEmail() {
		return this.get(PROPERTY_USUARIO_EMAIL);
	}

	public void setUsername(String value) {
		this.set(PROPERTY_USUARIO_USERNAME, value);
	}

	public String getUsername() {
		return this.get(PROPERTY_USUARIO_USERNAME);
	}

	public void setPassword(String value) {
		this.set(PROPERTY_USUARIO_PASSWORD, value);
	}

	public String getPassword() {
		return this.get(PROPERTY_USUARIO_PASSWORD);
	}

	public void setEnabled(Boolean value) {
		this.set(PROPERTY_USUARIO_ENABLED, value);
	}

	public Boolean getEnabled() {
		return this.get(PROPERTY_USUARIO_ENABLED);
	}

	public void setProfessorId(Long value) {
		this.set(PROPERTY_USUARIO_PROFESSOR_ID, value);
	}

	public Long getProfessorId() {
		return this.get(PROPERTY_USUARIO_PROFESSOR_ID);
	}

	public void setProfessorString(String value) {
		this.set(PROPERTY_USUARIO_PROFESSOR_STRING, value);
	}

	public String getProfessorString() {
		return this.get(PROPERTY_USUARIO_PROFESSOR_STRING);
	}

	public void setAuthorityUsername(String value) {
		this.set(PROPERTY_USUARIO_AUTHORITY_USERNAME, value);
	}

	public String getAuthorityUsername() {
		return this.get(PROPERTY_USUARIO_AUTHORITY_USERNAME);
	}

	public boolean isProfessor() {
		return (this.getProfessorId() != null && this.getProfessorId() > 0);
	}

	public boolean isAdministrador() {
		return !this.isProfessor();
	}

	@Override
	public String getNaturalKey() {
		return this.getUsername();
	}

	@Override
	public int compareTo(UsuarioDTO o) {
		return this.getNome().compareTo(o.getNome());
	}
}
