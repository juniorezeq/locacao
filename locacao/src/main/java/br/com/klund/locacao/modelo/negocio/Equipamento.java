package br.com.klund.locacao.modelo.negocio;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "tb_equipamento")
public class Equipamento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "Modelo", length = 90, nullable = false)
	private String modelo;
	@Column(name = "descricao", length = 90, nullable = false)
	private String descricao;
	@Column(name = "swl", length = 20, nullable = false)
	private String swl;
	@Column(name = "tag", length = 20, nullable = false)
	private String tag;
	@Column(name = "valorNota")
	private float valorNota;
	@Column(name = "precoDiariaMensal")
	private float precoDiariaMensal;
	@Column(name = "precoDiariaQuinzenal")
	private float precoDiariaQuinzenal;
	@Column (name = "status")
	private StatusEquipamento status;
	@Column (name = "validadeCertificacao")
	private LocalDate validadeCertificacao;
	@Column(name = "elevacao", length = 20)
	private String elevacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Equipamento other = (Equipamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSwl() {
		return swl;
	}

	public void setSwl(String swl) {
		this.swl = swl;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public float getValorNota() {
		return valorNota;
	}

	public void setValorNota(float valorNota) {
		this.valorNota = valorNota;
	}

	public float getPrecoDiariaMensal() {
		return precoDiariaMensal;
	}

	public void setPrecoDiariaMensal(float precoDiariaMensal) {
		this.precoDiariaMensal = precoDiariaMensal;
	}

	public float getPrecoDiariaQuinzenal() {
		return precoDiariaQuinzenal;
	}

	public void setPrecoDiariaQuinzenal(float precoDiariaQuinzenal) {
		this.precoDiariaQuinzenal = precoDiariaQuinzenal;
	}

	public StatusEquipamento getStatus() {
		return status;
	}

	public void setStatus(StatusEquipamento status) {
		this.status = status;
	}

	public LocalDate getValidadeCertificacao() {
		return validadeCertificacao;
	}

	public void setValidadeCertificacao(LocalDate validadeCertificacao) {
		this.validadeCertificacao = validadeCertificacao;
	}

	public String getElevacao() {
		return elevacao;
	}

	public void setElevacao(String elevacao) {
		this.elevacao = elevacao;
	}


	
	



}