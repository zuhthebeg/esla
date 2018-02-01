package spring.esla.view;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
 
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import spring.esla.beans.Speechpart;
import spring.esla.model.DictionaryDao;


public class XmlParser
{
	@Autowired
	DictionaryDao dictionaryDao;
	
      public static void main(String[] args) throws SQLException, ClassNotFoundException
      {
  		try {
			File lFile = new File("d:/품사정보.xml");
			DataInputStream dataInputStream = new DataInputStream(new FileInputStream(lFile));
			Speechpart lTempSpeechpart = null;
			String lTempString = null;
			String lTempContent = null;

			while(true) {
			//for(int i = 0;i < 100;i++) {
				try {
					lTempString = new String(dataInputStream.readLine().getBytes("iso-8859-1"), "utf-8");
					if(lTempString.contains("<WORD_NO>")) {
						lTempSpeechpart = new Speechpart();

						lTempContent = XmlParser.getContent(lTempString);
						lTempSpeechpart.setWord_no(Integer.parseInt(lTempContent));
//						System.out.println("WORD_NO : " + lTempContent);

						lTempString = new String(dataInputStream.readLine().getBytes("iso-8859-1"), "utf-8");
						lTempContent = XmlParser.getContent(lTempString);
						lTempSpeechpart.setSp_no(Integer.parseInt(lTempContent));
//						System.out.println("SP_NO : " + lTempContent);
						
						lTempString = new String(dataInputStream.readLine().getBytes("iso-8859-1"), "utf-8");
						lTempContent = XmlParser.getContent(lTempString);
						lTempSpeechpart.setSp_seq(Integer.parseInt(lTempContent));
//						System.out.println("SP_SEQ : " + lTempContent);

						lTempString = new String(dataInputStream.readLine().getBytes("iso-8859-1"), "utf-8");
						lTempContent = XmlParser.getContent(lTempString);
						lTempSpeechpart.setSp_code(lTempContent);
//						System.out.println("SP_CODE : " + lTempContent);

						lTempString = new String(dataInputStream.readLine().getBytes("iso-8859-1"), "utf-8");
						lTempContent = XmlParser.getContent(lTempString);
						lTempSpeechpart.setWord_grp_code(lTempContent);
//						System.out.println("WORD_GPR_CODE : " + lTempContent);

						lTempString = new String(dataInputStream.readLine().getBytes("iso-8859-1"), "utf-8");
						if(lTempString.contains("</SPELLING_RULE>") == false) {
							lTempString += "</SPELLING_RULE>";
							new String(dataInputStream.readLine().getBytes("iso-8859-1"), "utf-8");
						}
						lTempContent = XmlParser.getContent(lTempString);
						lTempSpeechpart.setSpelling_rule(lTempContent);
//						System.out.println("SPELLING_RULE : " + lTempContent);

						lTempString = new String(dataInputStream.readLine().getBytes("iso-8859-1"), "utf-8");
						lTempContent = XmlParser.getContent(lTempString);
						lTempSpeechpart.setTech_term_code(lTempContent);
//						System.out.println("TECH_TERM_CODE : " + lTempContent);

						lTempString = new String(dataInputStream.readLine().getBytes("iso-8859-1"), "utf-8");
						lTempContent = XmlParser.getContent(lTempString);
						lTempSpeechpart.setPattern(lTempContent);
//						System.out.println("PATTERN : " + lTempContent);

						lTempString = new String(dataInputStream.readLine().getBytes("iso-8859-1"), "utf-8");
						lTempContent = XmlParser.getContent(lTempString);
						lTempSpeechpart.setGrammar(lTempContent);
//						System.out.println("GRAMMER : " + lTempContent);

						lTempString = new String(dataInputStream.readLine().getBytes("iso-8859-1"), "utf-8");
						lTempContent = XmlParser.getContent(lTempString);
						lTempSpeechpart.setPattern_etc(lTempContent);
//						System.out.println("PATTERN_ETC : " + lTempContent);
						
					}
				} catch(NullPointerException e) {
					System.out.println("END OF FILE");
					break;
				}
			}
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
//  			asdf++;
  			try {
//  			if(asdf == 2){	
  				System.err.println("iString : " + iString);
  				Thread.sleep(1000000);
//  			}
//  				//File lFile = new File("c:/error_list.txt");
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

