package mypackage;

public class ProductPriority {
	
	String m_strProduct1Name;
	String m_strProduct2Name;
	String m_strProduct3Name;
	//String m_strProduct4Name;
	
	int m_nProduct1Qty;
	int m_nProduct2Qty;
	int m_nProduct3Qty;
	//int m_nProduct4Qty;

	
	ProductPriority (String str1,
			String str2,
			String str3,
		//	String str4,
			int nQty1,
			int nQty2,
			int nQty3
		//	int nQty4,
			)
	{
		m_strProduct1Name = str1;
		m_strProduct2Name = str2;
		m_strProduct3Name = str3;
		//m_strProduct4Name = str4;
		
		m_nProduct1Qty = nQty1;
		m_nProduct2Qty = nQty2;
		m_nProduct3Qty = nQty3;
	}
}
