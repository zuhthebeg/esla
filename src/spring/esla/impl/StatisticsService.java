package spring.esla.impl;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;



import spring.esla.beans.Board;
import spring.esla.beans.Part;
import spring.esla.beans.Statistics;
import spring.esla.beans.Tree;
import spring.esla.controller.*;
import spring.esla.model.DocumentDao;
import spring.esla.model.StatisticsDao;
import spring.esla.model.TagsDao;
@Service
public class StatisticsService extends StatisticsController {
	@Autowired
	private StatisticsDao statisticsDao;
	@Autowired
	private DocumentDao documentDao;
	@Override
	public ModelAndView barChartAction(Integer doc_idx, String isAllWord, Part part) throws Exception {
		
		HashMap<String , Object> pMap = new HashMap<String, Object>();
		pMap.put("doc_idx", doc_idx);
		pMap.put("isAllWord", isAllWord);
		part.toMap(pMap);
		ArrayList<Statistics> statList = statisticsDao.getStatistics(pMap);
		
		String tagSize = (statList.size() * 20 ) +"px";
		
		StringBuffer tagList = new StringBuffer();
		/*
		StringBuffer bioList = new StringBuffer();
		StringBuffer phyList = new StringBuffer();
		StringBuffer earthList = new StringBuffer();
		StringBuffer cheList = new StringBuffer();
		*/
		StringBuffer totalList = new StringBuffer();
		Integer totalWordCount = 0;
		
		//전문용어집 개별어휘수
		Integer bioWordCount = 0;
		Integer phyWordCount = 0;
		Integer earWordCount = 0;
		Integer cheWordCount = 0;
		
		//전문용어집 연어휘수
		Integer bioWordCountAll = 0;
		Integer phyWordCountAll = 0;
		Integer earWordCountAll = 0;
		Integer cheWordCountAll = 0;
		
		//표국대 전문용어 개별어휘수
		Integer bioStandardWordCount = 0;
		Integer phyStandardWordCount = 0;
		Integer earStandardWordCount = 0;
		Integer cheStandardWordCount = 0;
		//표국대 전문용어 연어휘수	
		Integer bioStandardWordCountAll = 0;
		Integer phyStandardWordCountAll = 0;
		Integer earStandardWordCountAll = 0;
		Integer cheStandardWordCountAll = 0;		
		
		for(int i=0; i<statList.size(); i++){
			tagList.append("'");
			
			tagList.append(statList.get(i).getTag());
			
			totalList.append(statList.get(i).getCnt());
			
			totalWordCount += statList.get(i).getCnt();	// 연 어휘수
			
			if(statList.get(i).getWord_no() > 0){ // 표국대 검색 단어이면
				// 과학 용어수(표준 국어 대사전)
				if(statList.get(i).getDic_biology_cnt()>0){
					bioStandardWordCount++;
					bioStandardWordCountAll += statList.get(i).getDic_biology_cnt();
				}else if(statList.get(i).getDic_physics_cnt()>0){
					phyStandardWordCount++;
					phyStandardWordCountAll +=statList.get(i).getDic_physics_cnt();
				}else if(statList.get(i).getDic_earth_cnt()>0){
					earStandardWordCount++;
					earStandardWordCountAll += statList.get(i).getDic_earth_cnt();
				}else if(statList.get(i).getDic_chemistry_cnt()>0){
					cheStandardWordCount++;
					cheStandardWordCountAll += statList.get(i).getDic_chemistry_cnt();
				}
			}else{
				// 과학 용어수(전문용어집) 연어휘수
				if(statList.get(i).getDic_biology_cnt()>0){
					bioWordCount ++;
				}else if(statList.get(i).getDic_physics_cnt()>0){
					phyWordCount ++;
				}else if(statList.get(i).getDic_earth_cnt()>0){
					earWordCount ++;
				}else if(statList.get(i).getDic_chemistry_cnt()>0){
					cheWordCount ++;
				}
				// 과학 용어수(전문용어집) 개별어휘수
				bioWordCountAll += statList.get(i).getDic_biology_cnt();
				phyWordCountAll += statList.get(i).getDic_physics_cnt();
				earWordCountAll += statList.get(i).getDic_earth_cnt();
				cheWordCountAll += statList.get(i).getDic_chemistry_cnt();
			}
			
			
			tagList.append("',");
			/*
			bioList.append(",");
			phyList.append(",");
			earthList.append(",");
			cheList.append(",");			
			*/
			totalList.append(",");			
			
		}
		ArrayList<Tree> processList = documentDao.getProcessNodes();
		
		ModelAndView mav = new ModelAndView("barChart");
		mav.addObject("processList",processList);
		mav.addObject("isAllWord", isAllWord);
		mav.addObject("part", part);
		mav.addAllObjects(pMap);
		mav.addObject("tagList", tagList);
		mav.addObject("tagSize" ,tagSize);
		/*
		mav.addObject("bioList", bioList);
		mav.addObject("phyList", phyList);
		mav.addObject("earthList", earthList);
		mav.addObject("cheList", cheList);
		*/
		mav.addObject("totalList",totalList);
		mav.addObject("distinctWordCount",statList.size());
		mav.addObject("totalWordCount",totalWordCount);
		
		mav.addObject("bioWordCount",bioWordCount );
		mav.addObject("phyWordCount", phyWordCount);
		mav.addObject("earWordCount",earWordCount );
		mav.addObject("cheWordCount", cheWordCount);
		
		mav.addObject("bioWordCountAll",bioWordCountAll );
		mav.addObject("phyWordCountAll", phyWordCountAll);
		mav.addObject("earWordCountAll",earWordCountAll );
		mav.addObject("cheWordCountAll", cheWordCountAll);
		
		mav.addObject("bioStandardWordCount", bioStandardWordCount);
		mav.addObject("phyStandardWordCount", phyStandardWordCount);
		mav.addObject("earStandardWordCount", earStandardWordCount);
		mav.addObject("cheStandardWordCount",cheStandardWordCount );
		
		mav.addObject("bioStandardWordCountAll", bioStandardWordCountAll);
		mav.addObject("phyStandardWordCountAll", phyStandardWordCountAll);
		mav.addObject("earStandardWordCountAll", earStandardWordCountAll);
		mav.addObject("cheStandardWordCountAll",cheStandardWordCountAll );		
		return  mav;
	}

	@Override
	public ModelAndView searchAction(Integer doc_idx) throws Exception {
		return new ModelAndView("wordSearch", "doc_idx","doc_idx");
	}

	@Override
	public ModelAndView saveAction(Statistics stat) throws Exception {
		stat.partCheck();
		ModelAndView mav = new ModelAndView("doc_excel");
		ArrayList<Board> tagList =statisticsDao.getWordDataToExcel(stat);
		mav.addObject("tags", tagList);
		return mav;
	}

	@Override
	public ModelAndView wordSearchAction(Statistics stat)
			throws Exception {
		ModelAndView mav = new ModelAndView("wordSearchReslut");
		ArrayList<Board> tagList =statisticsDao.getWordBySearchWord(stat);
		mav.addObject("tags", tagList);
		return mav;
	}

	@Override
	public ModelAndView tagInfomationAction(@PathVariable Integer tag_idx) throws Exception {
		
		ModelAndView mav = new ModelAndView("wordInfomation");
		ArrayList <String>sentence=statisticsDao.getSentenceByTagIdx(tag_idx);
		ArrayList <String> neighbor =statisticsDao.getNeighborListByTagIdx(tag_idx);
		
		ArrayList<Statistics> statResult = statisticsDao.getStaticLevelFourByTagIdx(tag_idx);
		
		mav.addObject("sentence", sentence);
		mav.addObject("neighbor", neighbor);
		mav.addObject("statResult", statResult);
		return mav;
	}

/*	@Override
	public ModelAndView pieChartAction(Integer doc_idx,   String part) throws Exception {
		HashMap<String , Object> pMap = new HashMap<String, Object>();
		pMap.put("doc_idx", doc_idx);
		pMap.put("part", part);
		ArrayList<Statistics> statList = statisticsDao.getStatistics(pMap);
		
		ModelAndView mav = new ModelAndView("pieChart");
		mav.addObject("totalList",statList);
		return  mav;
	}*/

}
