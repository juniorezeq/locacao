package br.com.klund.locacao.modelo.negocio;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_locacao")
public class Locacao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_locacao")
	private Long id;
	@Column(name = "codigo", length = 80, nullable = false, unique = true)
	private String codigo;
	@Column(nullable = false, insertable = true, updatable = false)
	private Date dataInicio;
	@Column(insertable = true, updatable = true)
	private Date dataFim;
	@Column(name = "valorLocacao")
	private Double valorLocacao;
	@ManyToOne
	@JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
	private Cliente cliente;
	@Column (name = "tipoLocacao")
	private TipoLocacao tipoLocacao;
	private StatusLocacao statusLocacao;
	@ManyToMany
	@JoinColumn(name = "id_locacao", referencedColumnName = "id_locacao")
	private List<Equipamento> equipamentos;
	
		
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public TipoLocacao getTipoLocacao() {
		return tipoLocacao;
	}

	public void setTipoLocacao(TipoLocacao tipoLocacao) {
		this.tipoLocacao = tipoLocacao;
	}

	public Double getValorLocacao() {
		return valorLocacao;
	}

	public void setValorLocacao(Double valorLocacao) {
		this.valorLocacao = valorLocacao;
	}

	

	public List<Equipamento> getEquipamentos() {
		return equipamentos;
	}

	public void setEquipamentos(List<Equipamento> equipamentos) {
		this.equipamentos = equipamentos;
	}
	
	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	public String dataInicioFormatada() {
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
	     String dataFormatada = formatador.format(this.getDataInicio());
	     return dataFormatada;
	}
	
	public String dataFimFormatada() {
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
	     String dataFormatada = formatador.format(this.getDataFim());
	     return dataFormatada;
	}

	public StatusLocacao getStatusLocacao() {
		return statusLocacao;
	}

	public void setStatusLocacao(StatusLocacao statusLocacao) {
		this.statusLocacao = statusLocacao;
	}
		
		
	public void addEquipamento(Equipamento equipamentoAdicionar) {
		if(equipamentos.isEmpty()) {
			equipamentos = new ArrayList<Equipamento>();
		}
		
		equipamentos.add(equipamentoAdicionar);
	}


	}