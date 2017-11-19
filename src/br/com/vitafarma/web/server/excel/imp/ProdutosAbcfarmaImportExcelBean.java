package br.com.vitafarma.web.server.excel.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProdutosAbcfarmaImportExcelBean extends AbstractImportExcelBean
		implements Comparable<ProdutosAbcfarmaImportExcelBean> {
	private String medAbcStr;
	private String medCtrStr;
	private String medLabStr;
	private String labNomStr;
	private String medDesStr;
	private String medAprStr;
	private String medPla1Str;
	private String medPco1Str;
	private String medFra1Str;
	private String medUniStr;
	private String medIpiStr;
	private String medDtVigStr;
	private String exp13Str;
	private String medBarraStr;
	private String medGeneStr;
	private String medNegPosStr;
	private String medPrinciStr;
	private String medPla0Str;
	private String medPco0Str;
	private String medFra0Str;
	private String medRegiMsStr;
	private String medVarPreStr;

	private Double medPla1;
	private Double medPco1;
	private Double medFra1;
	private Double medUni;
	private Double medIpi;
	private Double medPla0;
	private Double medPco0;
	private Double medFra0;

	private Long medAbc;
	private Long medLab;
	private Long medBarra;
	private Date medDtVig;
	private Boolean exp13;

	public ProdutosAbcfarmaImportExcelBean(int row) {
		super(row);
	}

	public List<ImportExcelError> checkSyntacticErrors() {
		List<ImportExcelError> erros = new ArrayList<ImportExcelError>();

		if (!this.tudoVazio()) {
			this.medAbc = this.checkNonNegativeDoubleField(this.medAbcStr,
					ImportExcelError.ABCFARMA_MED_ABC_VAZIO,
					ImportExcelError.ABCFARMA_MED_ABC_FORMATO_INVALIDO, erros)
					.longValue();

			// this.checkMandatoryField(this.medCtrStr,
			// ImportExcelError.ABCFARMA_MED_CTR_VAZIO, erros);

			this.medLab = this.checkNonNegativeDoubleField(this.medLabStr,
					ImportExcelError.ABCFARMA_MED_LAB_VAZIO,
					ImportExcelError.ABCFARMA_MED_LAB_FORMATO_INVALIDO, erros)
					.longValue();

			this.checkMandatoryField(this.labNomStr,
					ImportExcelError.ABCFARMA_LAB_NOM_VAZIO, erros);

			this.checkMandatoryField(this.medDesStr,
					ImportExcelError.ABCFARMA_MED_DES_VAZIO, erros);

			this.checkMandatoryField(this.medAprStr,
					ImportExcelError.ABCFARMA_MED_APR_VAZIO, erros);

			this.medPla1 = this.checkNonNegativeDoubleField(this.medPla1Str,
					ImportExcelError.ABCFARMA_MED_PLA1_VAZIO,
					ImportExcelError.ABCFARMA_MED_PLA1_FORMATO_INVALIDO, erros)
					.doubleValue();

			this.medPco1 = this.checkNonNegativeDoubleField(this.medPco1Str,
					ImportExcelError.ABCFARMA_MED_PCO1_VAZIO,
					ImportExcelError.ABCFARMA_MED_PCO1_FORMATO_INVALIDO, erros)
					.doubleValue();

			this.medFra1 = this.checkNonNegativeDoubleField(this.medFra1Str,
					ImportExcelError.ABCFARMA_MED_FRA1_VAZIO,
					ImportExcelError.ABCFARMA_MED_FRA1_FORMATO_INVALIDO, erros)
					.doubleValue();

			this.medUni = this.checkNonNegativeDoubleField(this.medUniStr,
					ImportExcelError.ABCFARMA_MED_UNI_VAZIO,
					ImportExcelError.ABCFARMA_MED_UNI_FORMATO_INVALIDO, erros)
					.doubleValue();

			this.medIpi = this.checkNonNegativeDoubleField(this.medIpiStr,
					ImportExcelError.ABCFARMA_MED_IPI_VAZIO,
					ImportExcelError.ABCFARMA_MED_IPI_FORMATO_INVALIDO, erros)
					.doubleValue();

			// this.medDtVig = this.checkDateTimeField(this.medDtVigStr,
			// ImportExcelError.ABCFARMA_DT_VIG_VAZIO, erros);

			// this.exp13 = this.checkBooleanField(this.exp13Str,
			// ImportExcelError.ABCFARMA_EXP_13_VAZIO, erros);

			this.medBarra = this
					.checkNonNegativeDoubleField(
							this.medBarraStr,
							ImportExcelError.ABCFARMA_MED_BARRA_VAZIO,
							ImportExcelError.ABCFARMA_MED_BARRA_FORMATO_INVALIDO,
							erros).longValue();

			// this.checkMandatoryField(this.medGeneStr,
			// ImportExcelError.ABCFARMA_MED_GENE_VAZIO, erros);

			this.checkMandatoryField(this.medNegPosStr,
					ImportExcelError.ABCFARMA_MED_NEG_POS_VAZIO, erros);

			// this.checkMandatoryField(this.medPrinciStr,
			// ImportExcelError.ABCFARMA_MED_PRINCI_VAZIO, erros);

			this.medPla0 = this.checkNonNegativeDoubleField(this.medPla0Str,
					ImportExcelError.ABCFARMA_MED_PLA0_VAZIO,
					ImportExcelError.ABCFARMA_MED_PLA0_FORMATO_INVALIDO, erros)
					.doubleValue();

			this.medPco0 = this.checkNonNegativeDoubleField(this.medPco0Str,
					ImportExcelError.ABCFARMA_MED_PCO0_VAZIO,
					ImportExcelError.ABCFARMA_MED_PCO0_FORMATO_INVALIDO, erros)
					.doubleValue();

			this.medFra0 = this.checkNonNegativeDoubleField(this.medFra0Str,
					ImportExcelError.ABCFARMA_MED_FRA0_VAZIO,
					ImportExcelError.ABCFARMA_MED_FRA0_FORMATO_INVALIDO, erros)
					.doubleValue();

			// this.checkMandatoryField(this.medRegiMsStr,
			// ImportExcelError.ABCFARMA_MED_REGIMS_VAZIO, erros);

			// this.checkMandatoryField(this.medVarPreStr,
			// ImportExcelError.ABCFARMA_MED_VAR_PRE_VAZIO, erros);
		} else {
			erros.add(ImportExcelError.TUDO_VAZIO);
		}

		return erros;
	}

	protected boolean tudoVazio() {
		return this.isEmptyField(this.medAbcStr)
				&& this.isEmptyField(this.medCtrStr)
				&& this.isEmptyField(this.medLabStr)
				&& this.isEmptyField(this.labNomStr)
				&& this.isEmptyField(this.medDesStr)
				&& this.isEmptyField(this.medAprStr)
				&& this.isEmptyField(this.medPla1Str)
				&& this.isEmptyField(this.medPco1Str)
				&& this.isEmptyField(this.medFra1Str)
				&& this.isEmptyField(this.medUniStr)
				&& this.isEmptyField(this.medIpiStr)
				&& this.isEmptyField(this.medDtVigStr)
				&& this.isEmptyField(this.exp13Str)
				&& this.isEmptyField(this.medBarraStr)
				&& this.isEmptyField(this.medGeneStr)
				&& this.isEmptyField(this.medNegPosStr)
				&& this.isEmptyField(this.medPrinciStr)
				&& this.isEmptyField(this.medPla0Str)
				&& this.isEmptyField(this.medPco0Str)
				&& this.isEmptyField(this.medFra0Str)
				&& this.isEmptyField(this.medRegiMsStr)
				&& this.isEmptyField(this.medVarPreStr);
	}

	public String getMedAbcStr() {
		return this.medAbcStr;
	}

	public void setMedAbcStr(String medAbcStr) {
		this.medAbcStr = medAbcStr;
	}

	public String getLabNomStr() {
		return this.labNomStr;
	}

	public void setLabNomStr(String labNomStr) {
		this.labNomStr = labNomStr;
	}

	public Long getMedAbc() {
		return this.medAbc;
	}

	public void setMedAbc(Long medAbc) {
		this.medAbc = medAbc;
	}

	public Long getMedLab() {
		return this.medLab;
	}

	public void setMedLab(Long medLab) {
		this.medLab = medLab;
	}

	public String getMedCtrStr() {
		return this.medCtrStr;
	}

	public void setMedCtrStr(String medCtrStr) {
		this.medCtrStr = medCtrStr;
	}

	public String getMedLabStr() {
		return this.medLabStr;
	}

	public void setMedLabStr(String medLabStr) {
		this.medLabStr = medLabStr;
	}

	public String getMedDesStr() {
		return this.medDesStr;
	}

	public void setMedDesStr(String medDesStr) {
		this.medDesStr = medDesStr;
	}

	public String getMedAprStr() {
		return this.medAprStr;
	}

	public void setMedAprStr(String medAprStr) {
		this.medAprStr = medAprStr;
	}

	public String getMedPla1Str() {
		return this.medPla1Str;
	}

	public void setMedPla1Str(String medPla1Str) {
		this.medPla1Str = medPla1Str;
	}

	public String getMedPco1Str() {
		return this.medPco1Str;
	}

	public void setMedPco1Str(String medPco1Str) {
		this.medPco1Str = medPco1Str;
	}

	public String getMedFra1Str() {
		return this.medFra1Str;
	}

	public void setMedFra1Str(String medFra1Str) {
		this.medFra1Str = medFra1Str;
	}

	public String getMedUniStr() {
		return this.medUniStr;
	}

	public void setMedUniStr(String medUniStr) {
		this.medUniStr = medUniStr;
	}

	public String getMedIpiStr() {
		return this.medIpiStr;
	}

	public void setMedIpiStr(String medIpiStr) {
		this.medIpiStr = medIpiStr;
	}

	public String getMedDtVigStr() {
		return this.medDtVigStr;
	}

	public void setMedDtVigStr(String medDtVigStr) {
		this.medDtVigStr = medDtVigStr;
	}

	public String getExp13Str() {
		return this.exp13Str;
	}

	public void setExp13Str(String exp13Str) {
		this.exp13Str = exp13Str;
	}

	public String getMedBarraStr() {
		return this.medBarraStr;
	}

	public void setMedBarraStr(String medBarraStr) {
		this.medBarraStr = medBarraStr;
	}

	public String getMedGeneStr() {
		return this.medGeneStr;
	}

	public void setMedGeneStr(String medGeneStr) {
		this.medGeneStr = medGeneStr;
	}

	public String getMedNegPosStr() {
		return this.medNegPosStr;
	}

	public void setMedNegPosStr(String medNegPosStr) {
		this.medNegPosStr = medNegPosStr;
	}

	public String getMedPrinciStr() {
		return this.medPrinciStr;
	}

	public void setMedPrinciStr(String medPrinciStr) {
		this.medPrinciStr = medPrinciStr;
	}

	public String getMedPla0Str() {
		return this.medPla0Str;
	}

	public void setMedPla0Str(String medPla0Str) {
		this.medPla0Str = medPla0Str;
	}

	public String getMedPco0Str() {
		return this.medPco0Str;
	}

	public void setMedPco0Str(String medPco0Str) {
		this.medPco0Str = medPco0Str;
	}

	public String getMedFra0Str() {
		return this.medFra0Str;
	}

	public void setMedFra0Str(String medFra0Str) {
		this.medFra0Str = medFra0Str;
	}

	public String getMedRegiMsStr() {
		return this.medRegiMsStr;
	}

	public void setMedRegiMsStr(String medRegiMsStr) {
		this.medRegiMsStr = medRegiMsStr;
	}

	public String getMedVarPreStr() {
		return this.medVarPreStr;
	}

	public void setMedVarPreStr(String medVarPreStr) {
		this.medVarPreStr = medVarPreStr;
	}

	public Double getMedPla1() {
		return this.medPla1;
	}

	public void setMedPla1(Double medPla1) {
		this.medPla1 = medPla1;
	}

	public Double getMedPco1() {
		return this.medPco1;
	}

	public void setMedPco1(Double medPco1) {
		this.medPco1 = medPco1;
	}

	public Double getMedFra1() {
		return this.medFra1;
	}

	public void setMedFra1(Double medFra1) {
		this.medFra1 = medFra1;
	}

	public Double getMedUni() {
		return this.medUni;
	}

	public void setMedUni(Double medUni) {
		this.medUni = medUni;
	}

	public Double getMedIpi() {
		return this.medIpi;
	}

	public void setMedIpi(Double medIpi) {
		this.medIpi = medIpi;
	}

	public Double getMedPla0() {
		return this.medPla0;
	}

	public void setMedPla0(Double medPla0) {
		this.medPla0 = medPla0;
	}

	public Double getMedPco0() {
		return this.medPco0;
	}

	public void setMedPco0(Double medPco0) {
		this.medPco0 = medPco0;
	}

	public Double getMedFra0() {
		return this.medFra0;
	}

	public void setMedFra0(Double medFra0) {
		this.medFra0 = medFra0;
	}

	public Long getMedBarra() {
		return this.medBarra;
	}

	public void setMedBarra(Long medBarra) {
		this.medBarra = medBarra;
	}

	public Date getMedDtVig() {
		return this.medDtVig;
	}

	public void setMedDtVig(Date medDtVig) {
		this.medDtVig = medDtVig;
	}

	public Boolean getExp13() {
		return this.exp13;
	}

	public void setExp13(Boolean exp13) {
		this.exp13 = exp13;
	}

	@Override
	public int compareTo(ProdutosAbcfarmaImportExcelBean o) {
		return this.getMedAbcStr().compareTo(o.getMedAbcStr());
	}
}
