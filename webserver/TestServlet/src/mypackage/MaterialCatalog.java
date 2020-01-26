package mypackage;
public class MaterialCatalog {
	
	//private String  strID;
	private String  strDesc;
	
	public MaterialCatalog(String strDesc)
	{
		this.strDesc = strDesc;
	}

	public String getStrDesc() {
		return strDesc;
	}

	public void setStrDesc(String strDesc) {
		this.strDesc = strDesc;
	}
	
	public String toString()
	{
		return " Desc = " + this.strDesc  ;
	}

}
