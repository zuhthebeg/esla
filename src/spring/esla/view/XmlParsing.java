package spring.esla.view;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.esla.beans.Definition;
import spring.esla.model.DictionaryDao;
import spring.esla.model.DocumentDao;

@Component
public class XmlParsing {

	@Autowired
	DictionaryDao dictionaryDao;
	
	public void xmlParsing() throws SQLException {
		try {
			File lFile = new File("c:/뜻풀이.xml");
			DataInputStream dataInputStream = new DataInputStream(new FileInputStream(lFile));
			Definition lTempDefinition = null;
			String lTempString = null;
			String lTempContent = null;

			while(true) {
			//for(int i = 0;i < 100;i++) {
				try {
					lTempString = new String(dataInputStream.readLine().getBytes("iso-8859-1"), "utf-8");
					
					if(lTempString.contains("<WORD_NO>")) {
						lTempDefinition = new Definition();

						lTempContent = XmlParsing.getContent(lTempString);
						lTempDefinition.setWord_no(Integer.parseInt(lTempContent));
//						System.out.println("WORD_NO : " + lTempContent);

						lTempString = new String(dataInputStream.readLine().getBytes("iso-8859-1"), "utf-8");
						lTempContent = XmlParsing.getContent(lTempString);
						lTempDefinition.setSp_no(Integer.parseInt(lTempContent));
//						System.out.println("SP_NO : " + lTempContent);

						lTempString = new String(dataInputStream.readLine().getBytes("iso-8859-1"), "utf-8");
						lTempContent = XmlParsing.getContent(lTempString);
						lTempDefinition.setPtrn_no(Integer.parseInt(lTempContent));
//						System.out.println("PTRN_NO : " + lTempContent);

						lTempString = new String(dataInputStream.readLine().getBytes("iso-8859-1"), "utf-8");
						lTempContent = XmlParsing.getContent(lTempString);
						lTempDefinition.setDfn_no(Integer.parseInt(lTempContent));
//						System.out.println("DNF_NO : " + lTempContent);

						lTempString = new String(dataInputStream.readLine().getBytes("iso-8859-1"), "utf-8");
						lTempContent = XmlParsing.getContent(lTempString);
						lTempDefinition.setDfn_seq(Integer.parseInt(lTempContent));
//						System.out.println("DNF_SEQ : " + lTempContent);

						lTempString = new String(dataInputStream.readLine().getBytes("iso-8859-1"), "utf-8");
						if(lTempString.contains("</PATTERN>") == false) {
							lTempString += "</PATTERN>";
							new String(dataInputStream.readLine().getBytes("iso-8859-1"), "utf-8");
						}
						lTempContent = XmlParsing.getContent(lTempString);
						lTempDefinition.setPattern(lTempContent);
//						System.out.println("PATTERN : " + lTempContent);

						lTempString = new String(dataInputStream.readLine().getBytes("iso-8859-1"), "utf-8");
						if(lTempString.contains("</GRAMMAR>") == false) {
							lTempString += "</GRAMMAR>";
							new String(dataInputStream.readLine().getBytes("iso-8859-1"), "utf-8");
						}
						lTempContent = XmlParsing.getContent(lTempString);
						lTempDefinition.setGrammar(lTempContent);
//						System.out.println("GRAMMAR : " + lTempContent);

						lTempString = new String(dataInputStream.readLine().getBytes("iso-8859-1"), "utf-8");
						lTempContent = XmlParsing.getContent(lTempString);
						lTempDefinition.setWord_grp_code(lTempContent);
//						System.out.println("WORD_GRP_CODE : " + lTempContent);

						lTempString = new String(dataInputStream.readLine().getBytes("iso-8859-1"), "utf-8");
						lTempContent = XmlParsing.getContent(lTempString);
						lTempDefinition.setTech_term_code(lTempContent);
//						System.out.println("TECH_TERM_CODE : " + lTempContent);

						lTempString = new String(dataInputStream.readLine().getBytes("iso-8859-1"), "utf-8");
						if(lTempString.contains("</DEFINITION>") == false) {
							lTempString += "</DEFINITION>";
							new String(dataInputStream.readLine().getBytes("iso-8859-1"), "utf-8");
						}
						lTempContent = XmlParsing.getContent(lTempString);
						lTempDefinition.setDefinition(lTempContent);
//						System.out.println("DEFINITION : " + lTempContent);

						lTempString = new String(dataInputStream.readLine().getBytes("iso-8859-1"), "utf-8");
						if(lTempString.contains("</EXAMPLE>") == false) {
							lTempString += "</EXAMPLE>";
							new String(dataInputStream.readLine().getBytes("iso-8859-1"), "utf-8");
						}
						lTempContent = XmlParsing.getContent(lTempString);
						lTempDefinition.setExample(lTempContent);
//						System.out.println("EXAMPLE : " + lTempContent);
						
						lTempString = new String(dataInputStream.readLine().getBytes("iso-8859-1"), "utf-8");
						if(lTempString.contains("</SPELLING_RULE>") == false) {
							lTempString += "</SPELLING_RULE>";
							new String(dataInputStream.readLine().getBytes("iso-8859-1"), "utf-8");
						}
						lTempContent = XmlParsing.getContent(lTempString);
						lTempDefinition.setSpelling_rule(lTempContent);
//						System.out.println("SPELLING_RULE : " + lTempContent);
						
						lTempString = new String(dataInputStream.readLine().getBytes("iso-8859-1"), "utf-8");
						if(lTempString.contains("</ADDITION>") == false) {
							lTempString += "</ADDITION>";
							new String(dataInputStream.readLine().getBytes("iso-8859-1"), "utf-8");
						}
						lTempContent = XmlParsing.getContent(lTempString);
						lTempDefinition.setAddition(lTempContent);
//						System.out.println("ADDITION : " + lTempContent);
						
						dictionaryDao.insertDefinitionDataByXml(lTempDefinition);
						
						//lDefinitionList.add(lTempDefinition);     // 여기에 모든 Definition 이 다 들어 있어요.
					}
//					if(lTempString.compareTo("</PATTERN>") == 0)
//						System.out.println(lTempString);
				} catch(NullPointerException e) {
					System.err.println(e.getStackTrace());
					System.out.println("END OF FILE");
					break;
				}
			}
			System.out.println("done");
		} catch (IOException e) {
			System.out.println("IOException Error!");
			e.printStackTrace();
		}
	}

	public static int asdf = 0;
	public static String getContent(String iString) {
		int lSubLeft = 0;
		int lSubRight = 0;
		
		//System.out.println(iString);
		
		lSubLeft = iString.indexOf(">");
		lSubRight = iString.lastIndexOf("<");

		String  test = null;
		try {
			test =  iString.substring(lSubLeft + 1, lSubRight);
		} catch (Exception e) {
//			asdf++;
			try {
//			if(asdf == 2){	
				System.err.println("iString : " + iString);
				Thread.sleep(1000000);
//			}
//				//File lFile = new File("c:/error_list.txt");
//				
//				
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO: handle exception
		}
		return test;
		//return  iString.substring(lSubLeft + 1, lSubRight);
	}
}
