package mypackage;

import java.util.HashMap;

public class MaterialList {
		
	//material ID to Quantity map for a given ProductName
	private HashMap<String, MaterialInfo> m_mapMaterials = new HashMap<String, MaterialInfo>();

	public void Insert(String strID, String strDesc, int nQty) {
		MaterialInfo entry = new MaterialInfo(nQty,strDesc,"");
		m_mapMaterials.put(strID, entry );
		System.out.println("\t Adding Material Info:"+ strID + entry );
	}

	int Size()
	{
		return m_mapMaterials.size();
	}
	
	HashMap<String, MaterialInfo> GetMaterialListMap()
	{
		return m_mapMaterials;
	}
	
}
