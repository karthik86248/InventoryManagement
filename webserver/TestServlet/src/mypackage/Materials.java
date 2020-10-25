package mypackage;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.FileInputStream;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.time.LocalDateTime; // import the LocalDate class
import java.time.format.DateTimeFormatter;

/**
 * Servlet implementation class Materials
 */
@WebServlet("/Materials")
public class Materials extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// in-memory data structures
	// material ID to Quantity map aka Transactionmap
	HashMap<String, MaterialInfo> map = new HashMap<String, MaterialInfo>();

	private static final String COMMA_DELIMITER = ",";
	public String test;
	CsvFileReader readFile = new CsvFileReader();
	CsvFileWriter writeFile = new CsvFileWriter();
	public static String REPORTS_FILE_PATH = "C:\\MyInventoryReports\\"; // all user generated reports
	public static String DB_FILE_PATH = "C:\\MyInventoryDB\\"; //  internally used files
	private static String TRANS_FILE_PATH = DB_FILE_PATH + "Transaction.csv";
	private static String PRODUCT_TRANS_FILE_PATH = DB_FILE_PATH + "ProductTransaction.csv";
	private static String INV_GEN_FILE_NAME = REPORTS_FILE_PATH;
	private static String INV_CAL_FILE_NAME = REPORTS_FILE_PATH;

	boolean ReadPropertiesFile() {
		
		Properties prop = new Properties();
		// This is the path of the properties file that will be used 
		// while running directly as a WebApp under Tomcat
		String strROOT = System.getenv("CATALINA_HOME");  
		String fileName = strROOT +  "/webapps/MyInvenotoryMgmt.config";
		System.out.println("Properties File = " + fileName);
		InputStream is = null;
		try {
		  String currentDirectory = System.getProperty("user.dir");
	      System.out.println("The current working directory is " + currentDirectory);

		    is = new FileInputStream(fileName);
		} catch (FileNotFoundException ex) {
			System.out.println("Error in opening Properties file !!!");
			ex.printStackTrace();
			return false;
		}
		try {
		    prop.load(is);
		} catch (IOException ex) {
			System.out.println("Error in loading Properties file !!!");
			ex.printStackTrace();
			return false;
		}
		System.out.println("DBLocation="+prop.getProperty("DBLocation"));
		System.out.println("ReportsLocation"+prop.getProperty("ReportsLocation"));
		//re-compute all the file paths
		DB_FILE_PATH=prop.getProperty("DBLocation") + "\\";
		REPORTS_FILE_PATH = prop.getProperty("ReportsLocation")+"\\";
		TRANS_FILE_PATH = DB_FILE_PATH + "Transaction.csv";
		PRODUCT_TRANS_FILE_PATH = DB_FILE_PATH + "ProductTransaction.csv";
		INV_GEN_FILE_NAME = REPORTS_FILE_PATH;
		INV_CAL_FILE_NAME = REPORTS_FILE_PATH;
		
		return true;
		// ToDo: Verify if the files we need are indeed present under 
		//those paths
	
	}
	
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Materials() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() throws ServletException {
		// Initialization code...
		if (false == ReadPropertiesFile())
			throw new ServletException("Reading Config file failed");
		readFile.readCsvFile(TRANS_FILE_PATH, map);
		System.out.println("Size of Transactionmap=" + String.valueOf(map.size()));
		CatalogDB obj = new CatalogDB();
		obj.InitiliseMaps();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		
		request.setAttribute("ProductList", CatalogDB.GetProductList()); // This will be available as ${message}
		request.getRequestDispatcher("Materials.jsp").forward(request, response);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String strForm = request.getParameter("FormName");
		String strUpdateMessage = "-";
		
		if (strForm.equals("F1")) {
			// Update Materials
			String strIDPlusDesc = request.getParameter("M_ID");
			strUpdateMessage = "Material Update:";
			strUpdateMessage += "<br>";

			if (strIDPlusDesc.isEmpty() != true && strIDPlusDesc.indexOf(COMMA_DELIMITER) != -1) {
				String[] tokens = strIDPlusDesc.split(COMMA_DELIMITER);
				String strID = tokens[0];
				// ToDO, ensure going to +1 character does not fall off the string
				String strDesc = strIDPlusDesc.substring(strIDPlusDesc.indexOf(COMMA_DELIMITER) + 1);
				// String strDesc = request.getParameter("Description");
				String strQty = request.getParameter("M_Qty");
				String strNotes = request.getParameter("M_Notes");
				if (strNotes.isEmpty() == true)
					strNotes = "-";
				int nMaterialQty = 0;
				if (strQty.isEmpty() != true) {
					nMaterialQty = Integer.parseInt(strQty);
					;
				}
				if (strID.isEmpty() != true && nMaterialQty != 0) {
					MaterialInfo entry = new MaterialInfo(Integer.parseInt(strQty), strDesc, strNotes);
					writeFile.writeCsvFile(TRANS_FILE_PATH, strID, entry, map);
				
					CatalogDB obj = new CatalogDB();
					
					strUpdateMessage += "\r\n";
					strUpdateMessage += "Material ID = " + strID;
					strUpdateMessage += entry ;
					strUpdateMessage += "<br>";
					strUpdateMessage = strUpdateMessage + "BinNumber = " + obj.GetBinNUmber(strID);
				}
			}

			// Update Product
			String strProductName = request.getParameter("P_NAME");

			String strProductQty = request.getParameter("P_Qty");
			String strProductNotes = request.getParameter("P_Notes");
			if (strProductNotes.isEmpty() == true)
				strProductNotes = "-";

			int nProdQty = 0;
			if (strProductQty.isEmpty() != true)
				// ToDo: Catch the exception
				nProdQty = Integer.parseInt(strProductQty);

			if (strProductName.isEmpty() != true && nProdQty != 0) {
				ProductInfo entryProd = new ProductInfo(Integer.parseInt(strProductQty), strProductNotes);
				UpdateProduct(strProductName, nProdQty,strProductNotes);
				writeFile.writeToProdTransactionCsvFile(PRODUCT_TRANS_FILE_PATH, strProductName, entryProd);
				strUpdateMessage = strUpdateMessage + "\r\n\tProduct Name =" + strProductName + " Quantity = "
						+ strProductQty;
			}
			
			request.setAttribute("message", strUpdateMessage); // This will be available as ${message} in the JSP

			request.getRequestDispatcher("success.jsp").forward(request, response);

//			response.sendRedirect("success.jsp");
		} else if (strForm.equals("F2")) {
			strUpdateMessage = "Material Reporting:";
			LocalDateTime now = LocalDateTime.now();
			// Format Oct-19-2019 16:39:32 PM, don't use : in the file name
			DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM-dd-yyyy HH mm ss a");
			String formatDateTime = now.format(format);
			String strFileName;
			if (request.getParameter("radios").equals("radio1")) {
				strFileName = INV_GEN_FILE_NAME + formatDateTime + "_FullInventory.csv";
				writeFile.writeCurrrentInventory(strFileName, map);
			} else {
				CatalogDB obj = new CatalogDB();
				strFileName = INV_GEN_FILE_NAME + formatDateTime + "_MiniInventory.csv";
				writeFile.writeCurrrentMiniInventory(strFileName, map,
						obj.GetMiniInvMap());
			}
			strUpdateMessage += "\r\n";
			strUpdateMessage += strFileName;
			request.setAttribute("message", strUpdateMessage); // This will be available as ${message} in the JSP
			request.getRequestDispatcher("success.jsp").forward(request, response);

		} else if (strForm.equals("F3")) {
			strUpdateMessage = "Material Estimating:";
			String strFileName;
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM-dd-yyyy HH mm ss a");
			String formatDateTime = now.format(format);

			String strProduct1Name = request.getParameter("P1_NAME");
			String strProduct1Qty = request.getParameter("P1_Qty");
			int nProd1Qty = 0;
			if (strProduct1Qty.isEmpty() != true)
				nProd1Qty = Integer.parseInt(strProduct1Qty);

			String strProduct2Name = request.getParameter("P2_NAME");
			String strProduct2Qty = request.getParameter("P2_Qty");
			int nProd2Qty = 0;
			if (strProduct2Qty.isEmpty() != true)
				nProd2Qty = Integer.parseInt(strProduct2Qty);

			String strProduct3Name = request.getParameter("P3_NAME");
			String strProduct3Qty = request.getParameter("P3_Qty");
			int nProd3Qty = 0;
			if (strProduct3Qty.isEmpty() != true)
				nProd3Qty = Integer.parseInt(strProduct3Qty);

			ProductPriority obj = new ProductPriority(strProduct1Name, strProduct2Name, strProduct3Name, nProd1Qty,
					nProd2Qty, nProd3Qty);
			strFileName = INV_CAL_FILE_NAME + formatDateTime + "_Cal.csv";
			writeFile.writeCalculatedInventory(strFileName , map, obj);
			
			strUpdateMessage += "\r\n";
			strUpdateMessage += strFileName;
			request.setAttribute("message", strUpdateMessage); // This will be available as ${message} in the JSP
			request.getRequestDispatcher("success.jsp").forward(request, response);

		}

	}

	protected void UpdateProduct(String strProdName, int nProdQty, String strNotes) {
		/*
		 * Get the Material list corresponding to this Product from the BOM
		 * Iterate the Material infos in that list 
		 * 	multiply the Qty with and update the notes
		 *  
		 */
		CatalogDB objDB = new CatalogDB();
		MaterialList listMaterials = objDB.GetMaterialsList(strProdName);
		if (listMaterials != null && listMaterials.Size() > 0) {
			HashMap<String, MaterialInfo> mapMaterials = listMaterials.GetMaterialListMap();

			for (String i : mapMaterials.keySet()) {

				MaterialInfo entry = new MaterialInfo(mapMaterials.get(i));
				entry.nQty = entry.nQty * nProdQty;
				entry.strNotes = strNotes;
				// We insert the material ID in lowercase as the map keys are
				// case-sensitive
				String strID = i.toLowerCase();
				writeFile.writeCsvFile(TRANS_FILE_PATH, strID, entry, map);

			}
		}
	}

}
