package br.com.thiago.retry.model.enums;

public enum TipoCliente {

	/*
	 * para não ser sucetível ao erro de algum desenvolverdor quebrar a ordem dos
	 * ENUMS, você pode seguir essa implementação de enum fixando o número que será
	 * salvo no banco. colocamos o valor do inteiro, e colocamos um valor legível;
	 * criamos um construtor privado; depois criamos um outro método que ao receber
	 * um código retorna um objeto enum varrendo todas as possibilidades que foram
	 * informadas.
	 */

	PESSOAFISICA(1, "Pessoa Física"), PESSOAJURIDICA(2, "Pessoa Jurídica");

	private int cod;
	private String descricao;

	private TipoCliente(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoCliente toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}

		for (TipoCliente x : TipoCliente.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Id inválido :" + cod);
	}
}
