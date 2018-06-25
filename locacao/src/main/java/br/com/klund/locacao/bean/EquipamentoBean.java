package br.com.klund.locacao.bean;

import java.io.Serializable;
import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.UploadedFile;

import br.com.klund.locacao.modelo.dao.EquipamentoDao;
import br.com.klund.locacao.modelo.negocio.Equipamento;
import br.com.klund.locacao.tx.Transacional;
import br.com.klund.locacao.validador.EquipamentoValidador;

import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class EquipamentoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EquipamentoDao equipamentoDao = new EquipamentoDao();
	@Inject
	private Equipamento equipamento = new Equipamento();
	@Inject
	private Equipamento selecionado = new Equipamento();
	private String buscar;
	@Inject
	private EquipamentoValidador equipamentoValidador;
	private UploadedFile file;
	private List<UploadedFile> arquivos = new ArrayList<UploadedFile>();


	@PostConstruct
	public void init() {
		equipamento = new Equipamento();
	}

	@Transacional
	public String iniciarCadastro() {
		return "/view/cadastro/cadastrarequipamento.xhtml?faces-redirect=true";
	}

	@Transacional
	public String alterarCadastro() {
		return "/view/cadastro/editarequipamento.xhtml?faces-redirect=true";
	}

	@Transacional
	public String listarCadastro() {
		return "/view/cadastro/listarequipamento.xhtml?faces-redirect=true";
	}

	@Transacional
	public List<Equipamento> listarTodos() {
		List<Equipamento> lista = new ArrayList<Equipamento>();
		lista = equipamentoDao.listarTodos();
		return lista;
	}

	
	@Transacional
	public List<Equipamento> listarDisponiveis() {
		List<Equipamento> lista = new ArrayList<Equipamento>();
		lista = equipamentoDao.equipamentosDisponiveis();
		return lista;
	}

	@Transacional
	public void incluir() {	
				if (equipamentoValidador.naoPodeIncluir(equipamento)) {
				mensagemErro(equipamentoValidador.getMensagem());
				return;
			} else {
				equipamentoDao.adiciona(equipamento);
				equipamento = new Equipamento();
				mensagemSucesso("cadastrado com sucesso.");
			}			
	}

	@Transacional
	public List<Equipamento> equipamentosCadastrados() {
		List<Equipamento> equipamentos = new ArrayList<Equipamento>();
		equipamentos = equipamentoDao.listaTodosPaginada(0, 100);
		return equipamentos;
	}

	@Transacional
	public void buscaPorTag() {
		try {
			equipamento = new Equipamento();
			equipamento = equipamentoDao.buscaTag(buscar);
			if (equipamento.getTag().isEmpty()) {
				mensagemErro("Este usuário não foi localizado");
			}
		} catch (Exception e) {
			mensagemErro("Erro não foi possível localizar");
		}
	}

	@Transacional
	public void atualizaEquipamento() {
		try {
			equipamentoDao.atualiza(equipamento);
			mensagemSucesso("Alterado com sucesso");
			equipamento = new Equipamento();
			buscar = "";
			equipamento = new Equipamento();
		} catch (Exception e) {
			mensagemErro("Erro não foi possivel atualizar");
		}
	}

	@Transacional
	public void apagarEquipamento() {
		try {
			equipamentoDao.remove(equipamento);
			mensagemSucesso("apagado com sucesso");
			equipamento = new Equipamento();
			buscar = "";

		} catch (Exception e) {
			mensagemErro("Erro não foi possivel apagar");
		}
	}

	private void mensagemSucesso(String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!!", mensagem));
	}

	private void mensagemErro(String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", mensagem));

	}

	public String getBuscar() {
		return buscar;
	}

	public void setBuscar(String buscar) {
		this.buscar = buscar;
	}

	public Equipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}
	
	

	public Equipamento getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(Equipamento selecionado) {
		this.selecionado = selecionado;
	}

	public void onSelect(Equipamento equipamento) {
		selecionado = new Equipamento();
		selecionado = equipamento;
	System.out.println(selecionado.getTag());
	}
	 
	  public void onDeselect(Equipamento equipamento) {
	    equipamento = new Equipamento();
	  }
		
	  public UploadedFile getFile() {
	        return file;
	    }
	 
	    public void setFile(UploadedFile file) {
	        this.file = file;
	    }
	     
	    
	    public void upload() {    		
	    	arquivos.add(file);
	      mensagemSucesso("Sucesso");
	    }

		public List<UploadedFile> getArquivos() {
			return arquivos;
		}

		public void setArquivos(List<UploadedFile> arquivos) {
			this.arquivos = arquivos;
		}

	    
	}
