package mypackage;
import java.io.IOException;
import java.util.List;
import java.util.Date;
public class MaterialInfo {
	// Transactional Data (dynamic)
	//String  strID;
	String  strDesc; // Description of the meterial
	int nQty;
	String strNotes;
	//String strLotNo;// Delivery challan
	//String strDate; // Date+time
	
	
	/*
	 * public String getStrID() { return strID; }
	 * 
	 * public void setStrID(String strID) { this.strID = strID; }
	 */

	public MaterialInfo(int nQty, String strDesc, String strNotes)
	{
		this.nQty = nQty;
		this.strDesc = strDesc;
		this.strNotes = strNotes;
	}
	//using MaterialInfo copy constructor
	public MaterialInfo(MaterialInfo Obj)
	{
		this.nQty = Obj.nQty;
		this.strDesc = Obj.strDesc;
		this.strNotes = Obj.strNotes;
	}
	
	public String getStrDesc() {
		return strDesc;
	}

	public void setStrDesc(String strDesc) {
		this.strDesc = strDesc;
	}

	public int getnQty() {
		return nQty;
	}

	public void setnQty(int nQty) {
		this.nQty = nQty;
	}
	
	public String getstrNotes() {
		return strNotes;
	}

	public void setstrNotes(String strNotes) {
		this.strNotes = strNotes;
	}	
	
	public String toString()
	{
		return " Desc = " + this.strDesc + " Qty = " + Integer.toString(nQty) + 
				" Notes = " + this.strNotes;
	}

	/*
	 * public String getStrLotNo() { return strLotNo; }
	 * 
	 * public void setStrLotNo(String strLotNo) { this.strLotNo = strLotNo; }
	 * 
	 * public String getStrDate() { return strDate; }
	 * 
	 * public void setStrDate(String strDate) { this.strDate = strDate; }
	 */
	

}
