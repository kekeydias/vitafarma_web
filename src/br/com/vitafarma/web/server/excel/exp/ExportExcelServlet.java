package br.com.vitafarma.web.server.excel.exp;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import br.com.vitafarma.domain.Cenario;
import br.com.vitafarma.domain.EntradaProdutoGroup;
import br.com.vitafarma.domain.Venda;
import br.com.vitafarma.web.shared.excel.ExcelInformationType;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nConstants;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nMessages;
import br.com.vitafarma.web.shared.util.VitafarmaDate;

public class ExportExcelServlet extends HttpServlet {
	private static final long serialVersionUID = -7987228694777660184L;
	private static VitafarmaI18nMessages i18nMessages = null;
	private static VitafarmaI18nConstants i18nConstants = null;
	private Cenario cenario = null;
	private Venda venda = null;
	private String codigoNotaFiscal = null;
	private String nomeFornecedor = null;
	private Date dataEntrada = null;
	private Boolean gerarNovoCupomVenda = null;

	// Filtros de produtos
	private Long codigoProduto = null;
	private Long medAbc = null;
	private String nomeProduto = null;
	private String nomeLaboratorio = null;
	private Double precoProduto = null;

	// Relatório de balanço do período
	private Long codigoCliente = null;
	private Long codigoFornecedor = null;
	private Date dataInicio = null;
	private Date dataFim = null;

	// Entrada de Produtos
	private EntradaProdutoGroup group = null;

	static {
		if (ExportExcelServlet.i18nConstants == null || ExportExcelServlet.i18nMessages == null) {
			ExportExcelServlet.i18nConstants = new VitafarmaI18nConstants();
			ExportExcelServlet.i18nMessages = new VitafarmaI18nMessages();
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String informationToBeExported = request.getParameter(ExcelInformationType.getInformationParameterName());

		this.carregaParametros(request);
		try {
			this.cenario = Cenario.getCurrentCenario();
		} catch (NoSuchAlgorithmException e) {
			this.cenario = null; // FIXME
		}

		ExportExcelParameters parametros = new ExportExcelParameters();
		parametros.setGroupEntrada(this.group);
		parametros.setCenario(this.cenario);
		parametros.setVenda(this.venda);
		parametros.setCodigoNotaFiscal(this.codigoNotaFiscal);
		parametros.setNomeFornecedor(this.nomeFornecedor);
		parametros.setCodigoProduto(this.codigoProduto);
		parametros.setMedAbc(this.medAbc);
		parametros.setNomeProduto(this.nomeProduto);
		parametros.setNomeProduto(this.nomeLaboratorio);
		parametros.setPrecoProduto(this.precoProduto);
		parametros.setDataEntrada(this.dataEntrada);
		parametros.setGerarNovoCupomVenda(this.gerarNovoCupomVenda);
		parametros.setCodigoCliente(this.codigoCliente);
		parametros.setCodigoFornecedor(this.codigoFornecedor);
		parametros.setDataInicio(this.dataInicio);
		parametros.setDataFim(this.dataFim);

		if (!informationToBeExported.isEmpty()) {
			// Get Excel Data
			IExportExcel exporter = ExportExcelFactory.createExporter(informationToBeExported, parametros,
					ExportExcelServlet.i18nConstants, ExportExcelServlet.i18nMessages);

			HSSFWorkbook workbook;
			try {
				workbook = exporter.export();
			} catch (NoSuchAlgorithmException e) {
				// FIXME
				RuntimeException re = new RuntimeException(e.getMessage());
				re.initCause(e);
				throw re;
			}

			if (exporter.getErrors().isEmpty()) {
				// Write data on response output stream
				writeExcelToHttpResponse(exporter.getFileName(), workbook, response);
			} else {
				response.setContentType("text/html");

				for (String msg : exporter.getWarnings()) {
					response.getWriter().println(ExcelInformationType.prefixWarning() + msg);
				}

				for (String msg : exporter.getErrors()) {
					response.getWriter().println(ExcelInformationType.prefixError() + msg);
				}

				response.getWriter().flush();
			}
		}
	}

	private void carregaParametros(HttpServletRequest request) {
		// Verifica se foi enviado o parâmetro
		// de idVenda (Relatório de ItensVenda)
		Long idVenda = null;
		try {
			idVenda = Long.parseLong(request.getParameter("idVenda"));
		} catch (Exception ex) {
			idVenda = null;
		}

		if (idVenda != null) {
			this.venda = Venda.find(idVenda);
		}

		// Verifica se deve-se gerar um novo cupom
		// da venda, caso já exista um cupom anterior
		Boolean gerarNovoCupomVenda = (request.getParameter("gerarNovoCupomVenda") == null ? false
				: Boolean.parseBoolean(request.getParameter("gerarNovoCupomVenda")));
		this.gerarNovoCupomVenda = gerarNovoCupomVenda;

		// Verifica se foi enviado o parâmetro de NomeFornecedor
		this.nomeFornecedor = (request.getParameter("nomeFornecedor") == null ? null
				: request.getParameter("nomeFornecedor").toString());

		// Verifica se foi enviado o parâmetro de CodigoProduto
		if (request.getParameter("codigoProduto") != null) {
			try {
				this.codigoProduto = Long.parseLong(request.getParameter("codigoProduto").toString());
			} catch (Exception e) {
				this.codigoProduto = null;
			}
		}

		// Verifica se foi enviado o parâmetro de CodigoCliente
		if (request.getParameter("codigoCliente") != null) {
			try {
				this.codigoCliente = Long.parseLong(request.getParameter("codigoCliente").toString());
			} catch (Exception e) {
				this.codigoCliente = null;
			}
		}

		// Verifica se foi enviado o parâmetro de CodigoFornecedor
		if (request.getParameter("codigoFornecedor") != null) {
			try {
				this.codigoFornecedor = Long.parseLong(request.getParameter("codigoFornecedor").toString());
			} catch (Exception e) {
				this.codigoFornecedor = null;
			}
		}

		// Verifica se foi enviado o parâmetro de NomeProduto
		this.nomeProduto = (request.getParameter("nomeProduto") == null ? null
				: request.getParameter("nomeProduto").toString());

		// Verifica se foi enviado o parâmetro de NomeLaboratorio
		this.nomeLaboratorio = (request.getParameter("nomeLaboratorio") == null ? null
				: request.getParameter("nomeLaboratorio").toString());

		// Verifica se foi enviado o parâmetro de PrecoProduto
		if (request.getParameter("precoProduto") != null) {
			try {
				this.precoProduto = Double.parseDouble(request.getParameter("precoProduto").toString());
			} catch (Exception e) {
				this.precoProduto = null;
			}
		}

		// Verifica se foi enviado o parâmetro
		// de DataEntrada (Relatório de EntradaProdutos)
		if (request.getParameter("dataEntrada") != null) {
			try {
				long timeEntrada = Long.parseLong(request.getParameter("dataEntrada").toString());
				this.dataEntrada = new VitafarmaDate(timeEntrada);
			} catch (Exception e) {
				this.dataEntrada = null;
			}
		}

		// Verifica se foi enviado o parâmetro
		// de DataInicio (Relatório de Balanco do Periodo)
		if (request.getParameter("dataInicio") != null) {
			try {
				long timeInicio = Long.parseLong(request.getParameter("dataInicio").toString());
				this.dataInicio = new VitafarmaDate(timeInicio);
			} catch (Exception e) {
				this.dataInicio = null;
			}
		}

		// Verifica se foi enviado o parâmetro
		// de DataFim (Relatório de Balanco do Periodo)
		if (request.getParameter("dataFim") != null) {
			try {
				long timeFim = Long.parseLong(request.getParameter("dataFim").toString());
				this.dataFim = new VitafarmaDate(timeFim);
			} catch (Exception e) {
				this.dataFim = null;
			}
		}

		// Verifica se foi enviado o parâmetro
		// de codigoNotaFiscal (Relatório de EntradaProdutos)
		this.codigoNotaFiscal = (request.getParameter("codigoNotaFiscal") == null ? null
				: request.getParameter("codigoNotaFiscal").toString());

		// Verifica se foi enviado o parâmetro de PrecoProduto
		if (request.getParameter("medAbc") != null) {
			try {
				this.medAbc = Long.parseLong(request.getParameter("medAbc").toString());
			} catch (Exception e) {
				this.medAbc = null;
			}
		}

		// Verifica se foi enviado o parâmetro de idEntradaGroup
		if (request.getParameter("idEntradaGroup") != null) {
			try {
				Long id = Long.parseLong(request.getParameter("idEntradaGroup").toString());

				this.group = EntradaProdutoGroup.find(id);
			} catch (Exception e) {
				this.group = null;
			}
		}
	}

	private void writeExcelToHttpResponse(String excelFileName, HSSFWorkbook excel, HttpServletResponse response)
			throws IOException {
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment; filename=" + excelFileName + ".xls");

		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			excel.write(out);
			out.flush();
		} catch (IOException e) {
			throw e;
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
}
