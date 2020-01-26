package mypackage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.time.LocalDateTime; // import the LocalDate class

/**
 * @author ashraf
 * 
 */
public class CsvFileWriter {

	// Delimiter used in CSV file
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";

	// CSV file header
	private static final String FILE_HEADER = "PRODUCT ID,PRODUCT DESCRIPTION,Quantity";

	/*
	 * Write MaterialInfo contents to the specified Transaction log fileName and the map
	 */

	public void writeCsvFile(String fileName, String strID, MaterialInfo entry, HashMap<String, MaterialInfo> map) {

		MaterialInfo entry1 = map.get(strID);
		if (entry1 != null) {
			entry1.nQty = entry.nQty + entry1.nQty;
		} else {
			entry1 = entry;
		}
		System.out.println("Adding to transactionmap ID=" + strID + entry1.toString());

		map.put(strID, entry1);

		FileWriter fileWriter = null;

		try {
			fileWriter = new FileWriter(fileName, true);

			// Write the CSV file header
			// fileWriter.append(FILE_HEADER.toString());

			// Add a new line separator after the header
			fileWriter.append(NEW_LINE_SEPARATOR);

			// Write a new student object list to the CSV file
			// for (Student student : students) {
			fileWriter.append(strID);
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(entry.getStrDesc());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(entry.getnQty()));
			fileWriter.append(COMMA_DELIMITER);
			LocalDateTime myObj = LocalDateTime.now();
			fileWriter.append(myObj.toString());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(entry.getstrNotes());
			// fileWriter.append(COMMA_DELIMITER);
			// }

			// System.out.println("Transaction log CSV file was updated successfully !!!");

		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {

			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}

		}
	}

	/*
	 * Write MaterialInfo contents to the specified Transaction log fileName and the map
	 */

	public void writeToProdTransactionCsvFile(String fileName, String strName, 
			ProductInfo entry) {

		FileWriter fileWriter = null;

		try {
			fileWriter = new FileWriter(fileName, true);

			// Write the CSV file header
			// fileWriter.append(FILE_HEADER.toString());

			// Add a new line separator after the header
			fileWriter.append(NEW_LINE_SEPARATOR);

			// Write a new student object list to the CSV file
			// for (Student student : students) {
			fileWriter.append(strName);
			fileWriter.append(COMMA_DELIMITER);
			//fileWriter.append(entry.getStrDesc());
			//fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(entry.getnQty()));
			fileWriter.append(COMMA_DELIMITER);
			LocalDateTime myObj = LocalDateTime.now();
			fileWriter.append(myObj.toString());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(entry.getstrNotes());
			// fileWriter.append(COMMA_DELIMITER);
			// }

			// System.out.println("Transaction log CSV file was updated successfully !!!");

		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {

			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}

		}
	}
	
	
	
	
	/*
	 * Write HashMap contents to the specified fileName
	 */
	public void writeCurrrentInventory(String fileName, HashMap<String, MaterialInfo> map) {

		FileWriter fileWriter = null;

		try {
			fileWriter = new FileWriter(fileName);

			// Write the CSV file header
			// fileWriter.append(FILE_HEADER.toString());

			// Add a new line separator after the header
			fileWriter.append(NEW_LINE_SEPARATOR);

			// Write a new student object list to the CSV file
			for (String i : map.keySet()) {
				fileWriter.append(i);
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(map.get(i).getStrDesc());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(map.get(i).getnQty()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(NEW_LINE_SEPARATOR);
			}

			System.out.println("Generate-Inventory CSV file was created successfully !!!");

		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {

			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}

		}

	}

	/*
	 * Write HashMap contents to the specified fileName
	 */
	public void writeCurrrentMiniInventory(String fileName, HashMap<String, MaterialInfo> map, HashMap<String, String> mapMiniInv) {

		FileWriter fileWriter = null;

		try {
			fileWriter = new FileWriter(fileName);

			// Write the CSV file header
			// fileWriter.append(FILE_HEADER.toString());

			// Add a new line separator after the header
			fileWriter.append(NEW_LINE_SEPARATOR);

			// Write a new student object list to the CSV file
			for (String i : map.keySet()) {
				if (null != mapMiniInv.get(i))
				{
				fileWriter.append(i);
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(map.get(i).getStrDesc());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(map.get(i).getnQty()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(NEW_LINE_SEPARATOR);
				}
			}

			System.out.println("Generate-Inventory CSV file was created successfully !!!");

		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {

			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}

		}

	}
	
	public void writeCalculatedInventory(String fileName, final HashMap<String, MaterialInfo> map,
			ProductPriority prodPrio) {

		FileWriter fileWriter = null;
		CatalogDB objDB = new CatalogDB();
		HashMap<String, MaterialInfo> mapMaterials1 = null;
		HashMap<String, MaterialInfo> mapMaterials2 = null;
		HashMap<String, MaterialInfo> mapMaterials3 = null;
		int nProduct1Qty_Calc = prodPrio.m_nProduct1Qty;
		int nProduct2Qty_Calc = prodPrio.m_nProduct2Qty;
		int nProduct3Qty_Calc = prodPrio.m_nProduct3Qty;
		int nTemp = 0;
		HashMap<String, MaterialInfo> mapInventoryCopy = new HashMap<String, MaterialInfo>(map);

		// make a deep copy of map into mapInventoryCopy
		for (String i : mapInventoryCopy.keySet()) {
			// using MaterialInfo copy constructor
			MaterialInfo entry = new MaterialInfo(map.get(i));
			mapInventoryCopy.put(i, entry);
		}

		MaterialList listMaterials1 = objDB.GetMaterialsList(prodPrio.m_strProduct1Name);
		System.out.println("ProductBOMmap Name=" + prodPrio.m_strProduct1Name +  "size=" + 
		listMaterials1.Size());
		if (listMaterials1 != null && listMaterials1.Size() > 0) {
			mapMaterials1 = listMaterials1.GetMaterialListMap();
		}

		MaterialList listMaterials2 = objDB.GetMaterialsList(prodPrio.m_strProduct2Name);
		System.out.println("ProductBOMmap Name=" + prodPrio.m_strProduct1Name +  "size=" + 
		listMaterials2.Size());
		if (listMaterials2 != null && listMaterials2.Size() > 0) {
			mapMaterials2 = listMaterials2.GetMaterialListMap();
		}

		MaterialList listMaterials3 = objDB.GetMaterialsList(prodPrio.m_strProduct3Name);
		System.out.println("ProductBOMmap Name=" + prodPrio.m_strProduct1Name +  "size=" + 
		listMaterials3.Size());
		if (listMaterials3 != null && listMaterials3.Size() > 0) {
			mapMaterials3 = listMaterials3.GetMaterialListMap();
		}

		// estimate the product quantities that can be accommodated with available
		// matierials
		// for product 1
		if (mapMaterials1 != null) {
			// I) compute nProduct1Qty_Calc
			for (String i : mapMaterials1.keySet()) {
				MaterialInfo entry1 = mapInventoryCopy.get(i);
				if (entry1 != null) {
					// Qty achievable for current material
					nTemp = (entry1.getnQty()) / mapMaterials1.get(i).getnQty();
					// min of Qty computed for materials till now
					nProduct1Qty_Calc = nProduct1Qty_Calc <= nTemp ? nProduct1Qty_Calc : nTemp;
					System.out.println("->ID:" + i + "Product1Qty_Calc=" + nProduct1Qty_Calc + "="
							+ mapMaterials1.get(i).getnQty() + "/" + entry1.getnQty());
				} else {
					nProduct1Qty_Calc = 0;
				}
			}

			// II) reduce the inventory by nProduct1Qty_Calc
			for (String i : mapMaterials1.keySet()) {
				MaterialInfo entry1 = mapInventoryCopy.get(i);
				if (entry1 != null) {
					entry1.setnQty(entry1.getnQty() - nProduct1Qty_Calc * mapMaterials1.get(i).getnQty());
				}
				else
				{
					MaterialInfo entryNew = new MaterialInfo(nProduct1Qty_Calc,mapMaterials1.get(i).getStrDesc(),"");
					// nProduct1Qty_Calc will be 0 anyways
					map.put(i, entryNew);
				}
			}
		} // if (mapMaterials1 != null)

		// for product 2
		if (mapMaterials2 != null) {
			for (String i : mapMaterials2.keySet()) {
				MaterialInfo entry2 = mapInventoryCopy.get(i);
				if (entry2 != null) {
					nTemp = (entry2.getnQty()) / mapMaterials2.get(i).getnQty();
					nProduct2Qty_Calc = nProduct2Qty_Calc <= nTemp ? nProduct2Qty_Calc : nTemp;
																		
					System.out.println("->ID:" + i + "Product2Qty_Calc=" + nProduct2Qty_Calc + "="
							+ mapMaterials2.get(i).getnQty() + "/" + entry2.getnQty());
				} else {
					nProduct2Qty_Calc = 0;
				}
			}

			// nProduct2Qty_Calc = prodPrio.m_nProduct2Qty <= nProduct2Qty_Calc ?
			// prodPrio.m_nProduct2Qty : nProduct2Qty_Calc;
			for (String i : mapMaterials2.keySet()) {
				MaterialInfo entry2 = mapInventoryCopy.get(i);
				if (entry2 != null) {
					entry2.setnQty(entry2.getnQty() - nProduct2Qty_Calc * mapMaterials2.get(i).getnQty());
				}
				else
				{
					MaterialInfo entryNew = new MaterialInfo(nProduct2Qty_Calc,mapMaterials2.get(i).getStrDesc(),"");
					map.put(i, entryNew);
				}
				
			}
		}//if (mapMaterials2 != null)

		// for product 3
		if (mapMaterials3 != null) {
			for (String i : mapMaterials3.keySet()) {
				MaterialInfo entry3 = mapInventoryCopy.get(i);
				if (entry3 != null) {
					nTemp = (entry3.getnQty()) / mapMaterials3.get(i).getnQty();
					nProduct3Qty_Calc = nProduct3Qty_Calc <= nTemp ? nProduct3Qty_Calc : nTemp;
				} else {
					nProduct3Qty_Calc = 0;
				}
			}

			// nProduct3Qty_Calc = prodPrio.m_nProduct3Qty <= nProduct3Qty_Calc ?
			// prodPrio.m_nProduct3Qty : nProduct3Qty_Calc;
			for (String i : mapMaterials3.keySet()) {
				MaterialInfo entry3 = mapInventoryCopy.get(i);
				if (entry3 != null) {
					entry3.setnQty(entry3.getnQty() - nProduct3Qty_Calc * mapMaterials3.get(i).getnQty());
				}
				else
				{
					MaterialInfo entryNew = new MaterialInfo(nProduct3Qty_Calc,mapMaterials3.get(i).getStrDesc(),"");
					map.put(i, entryNew);
				}
				
			}
		} // if (mapMaterials3 != null)

		try {
			fileWriter = new FileWriter(fileName);
			int nMateriaReqdForProd = 0;
			// int nMateriaReqdForProd2=0;
			// int nMateriaReqdForProd3=0;

			// Write the CSV file header
			// fileWriter.append(FILE_HEADER.toString());

			// Add a new line separator after the header
			fileWriter.append(NEW_LINE_SEPARATOR);

			fileWriter.append("Material ID");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("Desc");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("Current Qty");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(prodPrio.m_strProduct1Name + "(" + prodPrio.m_nProduct1Qty + ")");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(prodPrio.m_strProduct2Name + "(" + prodPrio.m_nProduct2Qty + ")");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(prodPrio.m_strProduct3Name + "(" + prodPrio.m_nProduct3Qty + ")");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("Shortage/excess to meet target");
			fileWriter.append(NEW_LINE_SEPARATOR);

			// Write a new student object list to the CSV file
			for (String i : map.keySet()) {

				nMateriaReqdForProd = 0;

				fileWriter.append(i);
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(map.get(i).getStrDesc());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(map.get(i).getnQty()));
				fileWriter.append(COMMA_DELIMITER);

				if (mapMaterials1 != null) {
					MaterialInfo entry1 = mapMaterials1.get(i);
					if (entry1 != null) {
						fileWriter.append(String.valueOf(nProduct1Qty_Calc * entry1.getnQty()));
						nMateriaReqdForProd += prodPrio.m_nProduct1Qty * entry1.getnQty();
					} else {
						fileWriter.append("-");
					}
					fileWriter.append(COMMA_DELIMITER);

					MaterialInfo entry2 = mapMaterials2.get(i);
					if (entry2 != null) {
						fileWriter.append(String.valueOf(nProduct2Qty_Calc * entry2.getnQty()));
						nMateriaReqdForProd += prodPrio.m_nProduct2Qty * entry2.getnQty();
					} else {
						fileWriter.append("-");
					}
					fileWriter.append(COMMA_DELIMITER);

					MaterialInfo entry3 = mapMaterials3.get(i);
					if (entry3 != null) {
						fileWriter.append(String.valueOf(nProduct3Qty_Calc * entry3.getnQty()));
						nMateriaReqdForProd += prodPrio.m_nProduct3Qty * entry3.getnQty();
					} else {
						fileWriter.append("-");
					}
					fileWriter.append(COMMA_DELIMITER);
				}
				// Report excess or shortage for requested targets
				fileWriter.append(String.valueOf(map.get(i).getnQty() - nMateriaReqdForProd));
				fileWriter.append(NEW_LINE_SEPARATOR);
			} // for (String i : map.keySet()) {

			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("Requested product targets in current inventory ->");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(prodPrio.m_nProduct1Qty));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(prodPrio.m_nProduct2Qty ));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(prodPrio.m_nProduct3Qty ));
			fileWriter.append(NEW_LINE_SEPARATOR);
			
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("Achievable product targets in current inventory ->");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(nProduct1Qty_Calc));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(nProduct2Qty_Calc));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(nProduct3Qty_Calc));
			fileWriter.append(NEW_LINE_SEPARATOR);
			
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("Material (type/ID) counts -> ");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(map.size()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(mapMaterials1.size()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(mapMaterials2.size()));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(mapMaterials3.size()));
			fileWriter.append(NEW_LINE_SEPARATOR);
			
			System.out.println("Calculate-Inventory CSV file was created successfully !!!");

		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {

			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}

		}

	}
}