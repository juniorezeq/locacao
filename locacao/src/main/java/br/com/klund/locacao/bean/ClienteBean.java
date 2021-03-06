package br.com.klund.locacao.bean;

import java.io.Serializable;
import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.klund.locacao.bean.ws.ApiCNPJ;
import br.com.klund.locacao.modelo.dao.ClienteDao;
import br.com.klund.locacao.modelo.negocio.Cliente;
import br.com.klund.locacao.modelo.negocio.TipoUsuario;
import br.com.klund.locacao.modelo.negocio.Usuario;
import br.com.klund.locacao.modelo.webservice.CNPJ_RWS;
import br.com.klund.locacao.tx.Transacional;

import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class ClienteBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private ClienteDao clienteDao;
	@Inject
	private Cliente cliente = new Cliente();
	@Inject
	private ApiCNPJ apiCnpj;
	@Inject
	private Usuario usuario;
	@Inject
	private Usuario usuarioLogado;
	private String localizarPorCnpj;

	@PostConstruct
	public void init() {
		cliente = new Cliente();
		apiCnpj = new ApiCNPJ();
		usuarioLogado = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	}

	@Transacional
	public String iniciarCadastro() {
		if(usuarioLogado.getTipoUsuario()!=TipoUsuario.Administrador) {
			return "/view/naoautorizado.xhtml?faces-redirect=true";
		}else {
			return "/view/cadastro/cadastrocliente.xhtml?faces-redirect=true";
		}	
			
	}

	@Transacional
	public String listaCliente() {
		return "/view/cadastro/listacliente.xhtml?faces-redirect=true";
	}

	@Transacional
	public String editarCliente() {
		if(usuarioLogado.getTipoUsuario()!=TipoUsuario.Administrador) {
			return "/view/naoautorizado.xhtml?faces-redirect=true";
		}else {
			return "/view/cadastro/editarcliente.xhtml?faces-redirect=true";
		}	
		
		
	}

	@Transacional
	public String limpar() {
		cliente = new Cliente();
		localizarPorCnpj = "";
		return null;
	}

	@Transacional
	public String checarCnpj() {
		Cliente clientebuscaDao = clienteDao.buscaCnpj(cliente.getCnpj());
		if (clientebuscaDao == null) {
			mensagemErro("Cliente não foi encontrado verifique o CNPJ digitado");
			return null;
		}
		System.out.println(clientebuscaDao.getCnpj());
		cliente = clientebuscaDao;
		return null;
	}

	@Transacional
	public void buscarReceita() {
		CNPJ_RWS recebido = apiCnpj.test(cliente.getCnpj());
		if (recebido.getCnpj() != null) {
			System.out.print(recebido.getNome());
			cliente.setCnpj(recebido.getCnpj());
			cliente.setNome(recebido.getNome());
			cliente.setLogradouro(recebido.getLogradouro());
			cliente.setNumero(recebido.getNumero());
			cliente.setBairro(recebido.getBairro());
			cliente.setMunicipio(recebido.getMunicipio());
			cliente.setUf(recebido.getUf());
			cliente.setTelefone(recebido.getTelefone());
			cliente.setSituacao(recebido.getSituacao());
			return;
		}
		limpar();
		mensagemErro(" verifique o CNPJ digitado");
	}

	@Transacional
	public String incluir() {
		boolean existe = clienteDao.existeCnpj(cliente);
		if (existe == false) {
			clienteDao.adiciona(cliente);
			mensagemSucesso("Cadastrado com sucesso");
			limpar();
			return null;
		}
		mensagemErro("O CNPJ informado pertence a outra empresa cadastrada");
		cliente = new Cliente();
		return null;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Transacional
	public String atualizaCliente() {
		clienteDao.atualiza(cliente);
		System.out.println("encontrado" + cliente.getNome());
		mensagemSucesso("Atualizada Corretamente!");
		limpar();
		return null;
	}

	@Transacional
	public String excluirCliente() {
		try {
			clienteDao.remove(cliente);
			mensagemSucesso("Excluido corretamente!");
		} catch (Exception e) {
			mensagemErro("Não foi possivel Excluir");
		}
		limpar();
		return null;
	}

	@Transacional
	public List<Cliente> clientessCadastrados() {
		List<Cliente> clientes = new ArrayList<Cliente>();
		clientes = clienteDao.listaTodosPaginada(0, 100);
		return clientes;
	}

	private void mensagemSucesso(String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!!", mensagem));
	}

	private void mensagemErro(String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", mensagem));

	}

	public String getLocalizarPorCnpj() {
		return localizarPorCnpj;
	}

	public void setLocalizarPorCnpj(String localizarPorCnpj) {
		this.localizarPorCnpj = localizarPorCnpj;
	}

	@Transacional
	public void buscaPorCNPJ() {
		try {
			cliente = new Cliente();
			cliente = clienteDao.buscaCnpj(localizarPorCnpj);
			if (cliente.getCnpj().isEmpty()) {
				mensagemErro("Este Cliente não foi localizado no banco de dados");
			}
		} catch (Exception e) {
			mensagemErro("Erro não foi possível localizar");
		}
	}

}