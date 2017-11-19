package br.com.vitafarma.web.server.util;

import br.com.vitafarma.domain.Cenario;

public class CenarioUtil {
	private Cenario cenario;

	public void criarCenario(Cenario cenario) {
		if (cenario == null) {
			System.out.println("Erro ao criar um novo cenario:");
			System.out.println("---> cenario invalido.");

			return;
		}

		new Criar(cenario);
	}

	public void clonarCenario(Cenario cenario) {
		if (cenario == null) {
			System.out.println("Erro ao copiar um cenario:");
			System.out.println("---> cenario invalido.");

			return;
		}

		new Clonar(cenario);
	}

	private class Criar {
		public Criar(Cenario cen) {
			cenario = cen;

			detachList();
			saveList();
		}
	}

	private class Clonar {
		public Clonar(Cenario cen) {
			cenario = cen;

			detachList();
			saveList();
		}
	}

	private void detachList() {
		this.cenario.setId(null);
		this.cenario.detach();
	}

	private void saveList() {
		this.cenario.save();
	}
}
