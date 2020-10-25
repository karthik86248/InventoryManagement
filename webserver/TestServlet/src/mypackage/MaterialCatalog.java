package mypackage;
public class MaterialCatalog {
	// All static info for a MAterial
	//private String  strID;
	private String  strDesc;
	private String strBinNUmber;
	
	public MaterialCatalog(String strDesc, String strBinNUmber)
	{
		this.strDesc = strDesc;
		this.strBinNUmber = strBinNUmber;
	}

	public String getStrDesc() {
		return strDesc;
	}

	public void setStrDesc(String strDesc) {
		this.strDesc = strDesc;
	}

	
	public String getStrBinNo() {
		return strBinNUmber;
	}

	public void setStrBinNo(String strBinNo) {
		this.strBinNUmber = strBinNUmber;
	}

	public String toString()
	{
		return " Desc = " + this.strDesc   + "Bin NUmber = " + this.getStrBinNo();
	}

}
