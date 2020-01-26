package mypackage;

import java.io.IOException;
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

	CsvFileReader readFile = new CsvFileReader();
	CsvFileWriter writeFile = new CsvFileWriter();
	public static final String LOG_FILE_PATH = "D:\\MyInventoryLogs\\"; // all user generated reports
	public static final String DB_FILE_PATH = "D:\\MyInventoryDB\\"; //  internally used files
	private static final String TRANS_FILE_PATH = DB_FILE_PATH + "Transaction.csv";
	private static final String PRODUCT_TRANS_FILE_PATH = DB_FILE_PATH + "ProductTransaction.csv";
	private static final String INV_GEN_FILE_NAME = LOG_FILE_PATH;
	private static final String INV_CAL_FILE_NAME = LOG_FILE_PATH;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Materials() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() throws ServletException {
		// Initialization code...
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

		String message = "Hello World";
		request.setAttribute("message", message); // This will be available as ${message}
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

		if (strForm.equals("F1")) {
			// Update Materials
			String strIDPlusDesc = request.getParameter("M_ID");
			String strUpdateMessage = "Material Update";
			if (strIDPlusDesc.isEmpty() != true && strIDPlusDesc.indexOf(COMMA_DELIMITER) != -1) {
				String[] tokens = strIDPlusDesc.split(COMMA_DELIMITER);
				String strID = tokens[0];
				// ToDO, ensure going to +1 character does not fall off the string
				String strDesc = strIDPlusDesc.substring(strIDPlusDesc.indexOf(COMMA_DELIMITER) + 1);
				// String strDesc = request.getParameter("Description");
				String strQty = request.getParameter("M_Qty");
				String strNotes = request.getParameter("M_Notes");
				int nMaterialQty = 0;
				if (strQty.isEmpty() != true) {
					nMaterialQty = Integer.parseInt(strQty);
					;
				}
				if (strID.isEmpty() != true && nMaterialQty != 0) {
					MaterialInfo entry = new MaterialInfo(Integer.parseInt(strQty), strDesc, strNotes);
					writeFile.writeCsvFile(TRANS_FILE_PATH, strID, entry, map);
					strUpdateMessage += "\tMaterial ID = " + strID + entry;
				}
			}

			// Update Product
			String strProductName = request.getParameter("P_NAME");

			String strProductQty = request.getParameter("P_Qty");
			String strProductNotes = request.getParameter("P_Notes");
			int nProdQty = 0;
			if (strProductQty.isEmpty() != true)
				// ToDo: Catch the exception
				nProdQty = Integer.parseInt(strProductQty);

			if (strProductName.isEmpty() != true && nProdQty != 0) {
				ProductInfo entryProd = new ProductInfo(Integer.parseInt(strProductQty), strProductNotes);
				UpdateProduct(strProductName, nProdQty);
				writeFile.writeToProdTransactionCsvFile(PRODUCT_TRANS_FILE_PATH, strProductName, entryProd);
				strUpdateMessage = strUpdateMessage + "\n\tProduct Name =" + strProductName + " Quantity = "
						+ strProductQty;
			}
			request.setAttribute("message", strUpdateMessage); // This will be available as ${message}

			request.getRequestDispatcher("success.jsp").forward(request, response);

//			response.sendRedirect("success.jsp");
		} else if (strForm.equals("F2")) {

			LocalDateTime now = LocalDateTime.now();
			// Format Oct-19-2019 16:39:32 PM, don't use : in the file name
			DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM-dd-yyyy HH mm ss a");
			String formatDateTime = now.format(format);
			if (request.getParameter("radios").equals("radio1")) {
				writeFile.writeCurrrentInventory(INV_GEN_FILE_NAME + formatDateTime + "_FullInventory.csv", map);
			} else {
				CatalogDB obj = new CatalogDB();
				writeFile.writeCurrrentMiniInventory(INV_GEN_FILE_NAME + formatDateTime + "_MiniInventory.csv", map,
						obj.GetMiniInvMap());
			}
			request.getRequestDispatcher("success.jsp").forward(request, response);

		} else if (strForm.equals("F3")) {
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
			writeFile.writeCalculatedInventory(INV_CAL_FILE_NAME + formatDateTime + "_Cal.csv", map, obj);
			request.getRequestDispatcher("success.jsp").forward(request, response);

		}

	}

	protected void UpdateProduct(String strProdName, int nProdQty) {
		CatalogDB objDB = new CatalogDB();
		MaterialList listMaterials = objDB.GetMaterialsList(strProdName);
		if (listMaterials != null && listMaterials.Size() > 0) {
			HashMap<String, MaterialInfo> mapMaterials = listMaterials.GetMaterialListMap();

			for (String i : mapMaterials.keySet()) {

				MaterialInfo entry = new MaterialInfo(mapMaterials.get(i));
				entry.nQty = entry.nQty * nProdQty;
				writeFile.writeCsvFile(TRANS_FILE_PATH, i, entry, map);

			}
		}
	}

}
