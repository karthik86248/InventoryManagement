package mypackage;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import mypackage.Materials;

/*
 * This class models the materials and product details
 * Material Catalog - Material ID and its corresponding description
 * Build Of Materials - What materials are needed and in what qty to build a specific Product
 * High Value Materials - List of High value Material IDs
 */


public class CatalogDB {
	//private static boolean s_bIsInitialised = false;
	// in memory data structures
	// material ID to description map MaterialCatalogmap
	private static HashMap<String, MaterialCatalog> mapCatalog = new HashMap<String, MaterialCatalog>();
	// ProductName to Material info map ProductBOMmap
	private static HashMap<String, MaterialList> mapProductBOM = new HashMap<String, MaterialList>();
	// material ID to price (or anything) map MiniInvMap
	private static HashMap<String, String> mapMiniInv = new HashMap<String, String>();

	private static boolean bInitialised  = false;

	private static List<String> ProductList =new ArrayList<String>();

	public static void InsertIntoProductList(String strName)
	{
		ProductList.add(strName);
	}

	public static List<String> GetProductList()
	{
		return ProductList;
	}
	/*public static final String DB_FILE_PATH = "D:\\MyInventoryDB\\";

	static String CATALOG_FILE_PATH = Materials.DB_FILE_PATH + "MaterialDesc.csv";
	static String PRODUCTBOM_FILE_PATH = Materials.DB_FILE_PATH + "ProductBOM.csv";
	static String MINIINV_FILE_PATH = Materials.DB_FILE_PATH + "MaterialMiniInventory.csv";
	*/

	String CATALOG_FILE_PATH ;
	String PRODUCTBOM_FILE_PATH;
	String MINIINV_FILE_PATH;

	public CatalogDB()
	{
		// recompute the file paths
		CATALOG_FILE_PATH = Materials.DB_FILE_PATH + "MaterialDesc.csv";
		PRODUCTBOM_FILE_PATH = Materials.DB_FILE_PATH + "ProductBOM.csv";
		MINIINV_FILE_PATH = Materials.DB_FILE_PATH + "MaterialMiniInventory.csv";

	}


	public void  InitiliseMaps()

	{
		if (false == bInitialised) {
			CsvFileReader readFile = new CsvFileReader();
			readFile.readCatalogCsvFile(CATALOG_FILE_PATH ,mapCatalog);
			readFile.readProdBOMCsvFile(PRODUCTBOM_FILE_PATH ,mapProductBOM);
			readFile.readMiniInvCsvFile(MINIINV_FILE_PATH,mapMiniInv);
			System.out.println("Size of ProdCatalogmap=" +  String.valueOf(mapCatalog.size()));
			System.out.println("Size of ProductBOMmap=" + String.valueOf(mapProductBOM.size()));
			System.out.println("Size of MiniInvMap=" + String.valueOf(mapMiniInv.size()));

			bInitialised = true;
		}
	}


	public List<String>  DoQuery(String query)
	{
		InitiliseMaps();
		String strID = null;
		String strEntry = null;
		List<String> matched = new ArrayList<String>();
		query = query.toLowerCase();
		for (String strKey : mapCatalog.keySet()) {
			// We insert the material ID in lowercase as the map keys are
			// case-sensitive. so send keys in lower case to auto-complete

			strID = strKey.toLowerCase();
			if(strID.startsWith(query)) {
				if (mapCatalog.containsKey(strKey))
				{
					strEntry = strID + "," + mapCatalog.get(strKey).getStrDesc();
					matched.add(strEntry);
				}
			}

		}
		return matched;
	}

	public MaterialList GetMaterialsList(String strProdName )
	{
		return mapProductBOM.get(strProdName);
	}

	public HashMap<String, String> GetMiniInvMap()
	{
		return mapMiniInv;
	}

	public String GetBinNUmber (String strMaterialID)
	{
		MaterialCatalog objCatalog = mapCatalog.get(strMaterialID);
		if (null == objCatalog)
			return null;

		return objCatalog.getStrBinNo();
	}

}
