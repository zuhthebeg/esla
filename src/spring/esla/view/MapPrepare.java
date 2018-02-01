package spring.esla.view;


import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.context.TilesRequestContext;
import org.apache.tiles.preparer.ViewPreparer;

import spring.esla.model.DocumentDao;

@Component
public class MapPrepare implements ViewPreparer {
	
	@Override
	public void execute(TilesRequestContext context, AttributeContext arg1) {
		HashMap<String, Object> metaMap = new HashMap<String, Object> ();
		metaMap.put("top","-");
		metaMap.put("process","교육과정");
		metaMap.put("grade","학년");
		metaMap.put("publisher","출판사");
		metaMap.put("title","과목");
		metaMap.put("chapter","단원");
		context.getRequestScope().put("metaMap", metaMap);
		
		/*		
		//헤더 날씨에따른 이미지 변경
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			//String menu_style_color = mainDAO.getMenuStyleColor();	// 메뉴 색상정보
			context.getRequestScope().put("MenuColor", "black");
			
			//String company_location = mainDAO.getCompanyLocation();	// 회사 위치정보
			
			context.getRequestScope().put("CompanyLocation", "chuncheon");
			
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse("http://www.google.com/ig/api?weather="+"chuncheon");	// 위치
			NodeList condition = doc.getDocumentElement().getElementsByTagName("condition"); //low : 최저온도, high : 최고온도, icon : 아이콘, temp_c : 현재온도
			String con_str = condition.item(0).getAttributes().getNamedItem("data").getNodeValue();

			String weather_img = mainDAO.getTopImgByWeather(con_str);
			if(weather_img == null  || weather_img == ""){
				mainDAO.insertTopImgIntoWeather(con_str);
				weather_img = "header_sky.gif";
		}
			context.getRequestScope().put("weather_img", "header_sky.gif");
			context.getRequestScope().put("todaty_weather", con_str);
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
	}

}
