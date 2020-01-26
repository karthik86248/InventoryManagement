package mypackage;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class CatalogDB {
	//private static boolean s_bIsInitialised = false;
	// in memory data structures
	// material ID to description map ProdCatalogmap
	private static HashMap<String, MaterialCatalog> mapCatalog = new HashMap<String, MaterialCatalog>();
	// ProductName to Material info map ProductBOMmap
	private static HashMap<String, MaterialList> mapProductBOM = new HashMap<String, MaterialList>();
	// material ID to price (or anything) map MiniInvMap
	private static HashMap<String, String> mapMiniInv = new HashMap<String, String>();

	private static boolean bInitialised  = false;
	// map <MaterialID, materials> mapProducts

	public static final String DB_FILE_PATH = "D:\\MyInventoryDB\\";
	private static final String CATALOG_FILE_PATH = DB_FILE_PATH + "MaterialDesc.csv";
	private static final String PRODUCTBOM_FILE_PATH = DB_FILE_PATH + "ProductBOM.csv";
	private static final String MINIINV_FILE_PATH = DB_FILE_PATH + "MaterialMiniInventory.csv";

	public CatalogDB()
	{

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
			strID = strKey.toLowerCase();
			if(strID.startsWith(query)) {
				strEntry = strID + "," + mapCatalog.get(strKey).getStrDesc();
				matched.add(strEntry);
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

}
