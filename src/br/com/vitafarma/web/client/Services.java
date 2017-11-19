package br.com.vitafarma.web.client;

import com.extjs.gxt.ui.client.Registry;
import com.google.gwt.core.client.GWT;

public class Services {
	private static final String GREET = "greet";
	private static final String CLIENTES = "clientes";
	private static final String PRODUTOS = "produtos";
	private static final String FORNECEDORES = "fornecedores";
	private static final String FUNCIONARIOS = "funcionarios";
	private static final String CENARIOS = "cenarios";
	private static final String USUARIOS = "usuarios";
	private static final String VENDAS = "vendas";
	private static final String ENTRADA_PRODUTOS = "entradaProdutos";
	private static final String ESTADOS = "estados";
	private static final String CIDADES = "cidades";
	private static final String VENDEDORES = "vendedores";
	private static final String LABORATORIOS = "laboratorios";
	private static final String UNIQUES = "uniques";

	public static FornecedoresServiceAsync fornecedores() {
		FornecedoresServiceAsync service = (FornecedoresServiceAsync) Registry
				.get(FORNECEDORES);
		if (service == null) {
			service = GWT.create(FornecedoresService.class);
			Registry.register(FORNECEDORES, service);
		}

		return service;
	}

	public static LaboratoriosServiceAsync laboratorios() {
		LaboratoriosServiceAsync service = (LaboratoriosServiceAsync) Registry
				.get(LABORATORIOS);
		if (service == null) {
			service = GWT.create(LaboratoriosService.class);
			Registry.register(LABORATORIOS, service);
		}

		return service;
	}

	public static GreetingServiceAsync greet() {
		GreetingServiceAsync service = (GreetingServiceAsync) Registry
				.get(GREET);
		if (service == null) {
			service = GWT.create(GreetingService.class);
			Registry.register(GREET, service);
		}

		return service;
	}

	public static CenariosServiceAsync cenarios() {
		CenariosServiceAsync service = (CenariosServiceAsync) Registry
				.get(CENARIOS);
		if (service == null) {
			service = GWT.create(CenariosService.class);
			Registry.register(CENARIOS, service);
		}

		return service;
	}

	public static UsuariosServiceAsync usuarios() {
		UsuariosServiceAsync service = (UsuariosServiceAsync) Registry
				.get(USUARIOS);
		if (service == null) {
			service = GWT.create(UsuariosService.class);
			Registry.register(USUARIOS, service);
		}

		return service;
	}

	public static ClientesServiceAsync clientes() {
		ClientesServiceAsync service = (ClientesServiceAsync) Registry
				.get(CLIENTES);
		if (service == null) {
			service = GWT.create(ClientesService.class);
			Registry.register(CLIENTES, service);
		}

		return service;
	}

	public static FuncionariosServiceAsync funcionarios() {
		FuncionariosServiceAsync service = (FuncionariosServiceAsync) Registry
				.get(FUNCIONARIOS);
		if (service == null) {
			service = GWT.create(FuncionariosService.class);
			Registry.register(FUNCIONARIOS, service);
		}

		return service;
	}

	public static VendedoresServiceAsync vendedores() {
		VendedoresServiceAsync service = (VendedoresServiceAsync) Registry
				.get(VENDEDORES);
		if (service == null) {
			service = GWT.create(VendedoresService.class);
			Registry.register(VENDEDORES, service);
		}

		return service;
	}

	public static ProdutosServiceAsync produtos() {
		ProdutosServiceAsync service = (ProdutosServiceAsync) Registry
				.get(PRODUTOS);
		if (service == null) {
			service = GWT.create(ProdutosService.class);
			Registry.register(PRODUTOS, service);
		}

		return service;
	}

	public static VendasServiceAsync vendas() {
		VendasServiceAsync service = (VendasServiceAsync) Registry.get(VENDAS);
		if (service == null) {
			service = GWT.create(VendasService.class);
			Registry.register(VENDAS, service);
		}

		return service;
	}

	public static EntradaProdutosServiceAsync entradaProdutos() {
		EntradaProdutosServiceAsync service = (EntradaProdutosServiceAsync) Registry
				.get(ENTRADA_PRODUTOS);
		if (service == null) {
			service = GWT.create(EntradaProdutosService.class);
			Registry.register(ENTRADA_PRODUTOS, service);
		}

		return service;
	}

	public static EstadosServiceAsync estados() {
		EstadosServiceAsync service = (EstadosServiceAsync) Registry
				.get(ESTADOS);
		if (service == null) {
			service = GWT.create(EstadosService.class);
			Registry.register(ESTADOS, service);
		}

		return service;
	}

	public static CidadesServiceAsync cidades() {
		CidadesServiceAsync service = (CidadesServiceAsync) Registry
				.get(CIDADES);
		if (service == null) {
			service = GWT.create(CidadesService.class);
			Registry.register(CIDADES, service);
		}

		return service;
	}

	public static UniquesServiceAsync uniques() {
		UniquesServiceAsync service = (UniquesServiceAsync) Registry
				.get(UNIQUES);
		if (service == null) {
			service = GWT.create(UniquesService.class);
			Registry.register(UNIQUES, service);
		}

		return service;
	}
}
