package br.com.vitafarma.web.server.excel.imp;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import br.com.vitafarma.domain.Cenario;
import br.com.vitafarma.web.shared.excel.ExcelInformationType;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nConstants;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nMessages;

public class ImportExcelServlet extends HttpServlet {
	private static final long serialVersionUID = 1889121953846517684L;
	private static VitafarmaI18nConstants i18nConstants = new VitafarmaI18nConstants();
	private static VitafarmaI18nMessages i18nMessages = new VitafarmaI18nMessages();
	private Cenario cenario = null;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			this.cenario = Cenario.getCurrentCenario();
		} catch (NoSuchAlgorithmException e1) {
			this.cenario = null; // FIXME
		}

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		InputStream inputStream = null;

		try {
			@SuppressWarnings("unchecked")
			List<FileItem> itens = upload.parseRequest(request);

			String fileName = null;
			String informationToBeImported = null;
			Boolean createNewEntities = false;

			for (FileItem iten : itens) {
				if (iten.getFieldName().equals(ExcelInformationType.getInformationParameterName())) {
					informationToBeImported = iten.getString();
				} else if (iten.getFieldName().equals(ExcelInformationType.getFileParameterName())) {
					fileName = iten.getName();
					inputStream = iten.getInputStream();
				} else if (iten.getFieldName().equals(ExcelInformationType.getCreateNewEntities())) {
					try {
						createNewEntities = false;

						if ("on".equalsIgnoreCase(iten.getString())) {
							createNewEntities = true;
						}
					} catch (Exception ex) {
						createNewEntities = false;
					}
				}
			}

			ImportExcelParameters parameters = new ImportExcelParameters();
			parameters.setInfoToBeImported(informationToBeImported);
			parameters.setCreateNewEntities(createNewEntities);

			if (inputStream != null && informationToBeImported != null) {
				IImportExcel importer = ImportExcelFactory.createImporter(parameters, this.cenario,
						ImportExcelServlet.i18nConstants, ImportExcelServlet.i18nMessages);

				if (importer != null) {
					if (!importer.load(fileName, inputStream)) {
						response.setContentType("text/html");

						for (String msg : importer.getWarnings()) {
							response.getWriter().println(ExcelInformationType.prefixWarning() + msg);
						}
						for (String msg : importer.getErrors()) {
							response.getWriter().println(ExcelInformationType.prefixError() + msg);
						}

						response.getWriter().flush();
					}
				} else {
					response.setContentType("text/html");

					response.getWriter().println(ExcelInformationType.prefixError()
							+ ImportExcelServlet.i18nMessages.excelErroImportadorNulo(informationToBeImported));
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
