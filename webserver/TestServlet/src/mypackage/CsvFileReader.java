package mypackage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ashraf_sarhan
 *
 */
public class CsvFileReader {

	// Delimiter used in CSV file
	private static final String COMMA_DELIMITER = ",";

	// Transaction log attributes index
	// ID,Description,QTY,TimeStamp
	private static final int MATERIAL_ID_IDX = 0;
	private static final int MATERIAL_DESC_IDX = 1;
	// private static final int MATERIAL_QTY_IDX = 2;
	private static final int CATALOG_QTY_IDX = 0;
	// private static final int CATALOG_DESC_IDX = 1;

	// Reads the Transaction log
	public void readCsvFile(String fileName, HashMap<String, MaterialInfo> map) {

		BufferedReader fileReader = null;

		try {
			String line = "";

			// Create the file reader
			fileReader = new BufferedReader(new FileReader(fileName));

			// Read the CSV file header to skip it
			fileReader.readLine();

			// Read the file line by line starting from the second line
			// ID,Desc,Qty,Time,notes
			// Note: Desc could contain commas
			while ((line = fileReader.readLine()) != null) {
				// Get all tokens available in line
				String[] tokens = line.split(COMMA_DELIMITER);
				String strConnatenatedDesc = "";
				// handle case when the Desc has commas
				if (tokens.length > 5) {
					for (int nIter = 1; nIter < tokens.length - 3; nIter++)
						strConnatenatedDesc += tokens[nIter];
				}

				if (tokens.length >= 5) {

					MaterialInfo entry = new MaterialInfo(Integer.parseInt(tokens[tokens.length - 3]),
							tokens.length > 5 ? strConnatenatedDesc : tokens[MATERIAL_DESC_IDX],tokens[tokens.length - 1]);

					MaterialInfo entry1 = map.get(tokens[MATERIAL_ID_IDX]);
					System.out.println("Trying to read from transactionmap" + tokens[MATERIAL_ID_IDX] + entry1);
					if (entry1 != null) {
						entry1.nQty = entry.nQty + entry1.nQty;
					} else {
						entry1 = entry;
					}
					map.put(tokens[MATERIAL_ID_IDX], entry1);
					System.out.println("Adding/updating to transactionmap, ID=" + tokens[MATERIAL_ID_IDX] + entry1);
				}
			}
		} catch (Exception e) {
			System.out.println("Error in CsvFileReader !!!");
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				System.out.println("Error while closing fileReader !!!");
				e.printStackTrace();
			}
		}

	}

	// Reads the Catalog file
	public void readCatalogCsvFile(String fileName, HashMap<String, MaterialCatalog> mapCatalog) {

		BufferedReader fileReader = null;

		try {
			String line = "";

			// Create the file reader
			fileReader = new BufferedReader(new FileReader(fileName));

			// Read the CSV file header to skip it
			fileReader.readLine();

			// Read the file line by line starting from the second line
			while ((line = fileReader.readLine()) != null) {
				// Get all tokens available in line
				String[] tokens = line.split(COMMA_DELIMITER);
				if (tokens.length > 0) {
					// Create a new student object and fill his data
					// ToDO, ensure going to +1 character does not fall off the string
					MaterialCatalog entry = new MaterialCatalog(line.substring(line.indexOf(COMMA_DELIMITER)+1));
					System.out.println("Adding to ProdCatalogmap ID=" + tokens[MATERIAL_ID_IDX] + entry);
					mapCatalog.put(tokens[MATERIAL_ID_IDX], entry);

				}
			}
		} catch (Exception e) {
			System.out.println("Error in CsvFileReader !!!");
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				System.out.println("Error while closing fileReader !!!");
				e.printStackTrace();
			}
		}

	}

	// Reads the mini inventory file
	public void readMiniInvCsvFile(String fileName, HashMap<String, String> mapMiniInv) {

		BufferedReader fileReader = null;

		try {
			String line = "";

			// Create the file reader
			fileReader = new BufferedReader(new FileReader(fileName));

			// Read the CSV file header to skip it
			fileReader.readLine();

			// Read the file line by line starting from the second line
			while ((line = fileReader.readLine()) != null) {
				// Get all tokens available in line
				String[] tokens = line.split(COMMA_DELIMITER);
				if (tokens.length > 0) {
					System.out.println("Adding to MiniInvMap ID=" + tokens[MATERIAL_ID_IDX] );
					mapMiniInv.put(tokens[MATERIAL_ID_IDX], line.substring(line.indexOf(COMMA_DELIMITER)+1));

				}
			}
		} catch (Exception e) {
			System.out.println("Error in CsvFileReader !!!");
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				System.out.println("Error while closing fileReader !!!");
				e.printStackTrace();
			}
		}

	}

	// Reads the Catalog file
	public void readProdBOMCsvFile(String fileName, HashMap<String, MaterialList> mapProdBOM) {

		BufferedReader fileReader = null;
		String strProdName = "";
		MaterialList listMaterials = new MaterialList();

		try {
			String line = "";

			// Create the file reader
			fileReader = new BufferedReader(new FileReader(fileName));

			// Read the CSV file header to skip it
			fileReader.readLine();

			// Read the file line by line starting from the second line
			while ((line = fileReader.readLine()) != null) {
				String strConnatenatedDesc = "";
				// Get all tokens available in line
				String[] tokens = line.split(COMMA_DELIMITER);
				if (tokens.length == 1) {

					if (listMaterials.Size() > 0 && strProdName.isEmpty() != true) {
						System.out.println("Inserting into ProductBOMmap, Product Name:" + strProdName);
						mapProdBOM.put(strProdName, listMaterials);
						listMaterials = new MaterialList();
						strProdName = tokens[0];
					} else {
						strProdName = tokens[0];
					}
				}

				if (tokens.length > 3) {
					for (int nIter = 1; nIter < tokens.length - 1; nIter++)
						strConnatenatedDesc += tokens[nIter];

					listMaterials.Insert(tokens[0], strConnatenatedDesc, Integer.parseInt(tokens[tokens.length - 1]));
				}
				if (tokens.length == 3) {
					listMaterials.Insert(tokens[0], tokens[1], Integer.parseInt(tokens[tokens.length - 1]));
				}
			}
		} catch (Exception e) {
			System.out.println("Error in CsvFileReader !!!");
			e.printStackTrace();
		} finally {
			try {
				if (listMaterials.Size() > 0 && strProdName.isEmpty() != true) {
					mapProdBOM.put(strProdName, listMaterials);
					System.out.println("Inserting into ProductBOMmap, Product Name:" + strProdName);
					fileReader.close();
				}
			} catch (IOException e) {
				System.out.println("Error while closing fileReader !!!");
				e.printStackTrace();
			}
		}

	}

}
