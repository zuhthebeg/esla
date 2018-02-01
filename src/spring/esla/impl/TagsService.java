package spring.esla.impl;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;

import kr.ac.kaist.swrc.jhannanum.comm.Eojeol;
import kr.ac.kaist.swrc.jhannanum.comm.Sentence;
import kr.ac.kaist.swrc.jhannanum.demo.WorkflowHmmPosTagger;
import kr.ac.kaist.swrc.jhannanum.hannanum.Workflow;
import kr.ac.kaist.swrc.jhannanum.hannanum.WorkflowFactory;
import kr.ac.kaist.swrc.jhannanum.plugin.MajorPlugin.MorphAnalyzer.ChartMorphAnalyzer.ChartMorphAnalyzer;
import kr.ac.kaist.swrc.jhannanum.plugin.MajorPlugin.PosTagger.HmmPosTagger.HMMTagger;
import kr.ac.kaist.swrc.jhannanum.plugin.SupplementPlugin.MorphemeProcessor.UnknownMorphProcessor.UnknownProcessor;
import kr.ac.kaist.swrc.jhannanum.plugin.SupplementPlugin.PlainTextProcessor.InformalSentenceFilter.InformalSentenceFilter;
import kr.ac.kaist.swrc.jhannanum.plugin.SupplementPlugin.PlainTextProcessor.SentenceSegmentor.SentenceSegmentor;
import kr.ac.kaist.swrc.jhannanum.plugin.SupplementPlugin.PosProcessor.NounExtractor.NounExtractor;
import kr.ac.kaist.swrc.jhannanum.plugin.SupplementPlugin.PosProcessor.SimplePOSResult09.SimplePOSResult09;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;



import spring.esla.beans.Board;
import spring.esla.beans.KorDictionaryVO;
import spring.esla.beans.Tree;
import spring.esla.controller.*;
import spring.esla.model.BoardDao;
import spring.esla.model.DictionaryDao;
import spring.esla.model.DocumentDao;
import spring.esla.model.TagsDao;
@Service
public class TagsService extends TagsController {
	@Autowired
	private TagsDao tagsDao;

	@Autowired
	private DictionaryDao dictionaryDao;
	
	@Autowired
	private BoardDao boardDao;
	
	@Override
	public ModelAndView deleteAction(Integer idx)
			throws Exception {
		tagsDao.deleteTagInfo(idx);
		return new ModelAndView("json", "status", "success");
	}

	@Override
	public ModelAndView saveAction(Board tag) throws Exception {
		tagsDao.updateTagInfo(tag);
		return new ModelAndView("json", "status", "success");
	}

	@Override
	public ModelAndView extractAction(String text,  HttpServletRequest request) throws Exception {
		String path = request.getRealPath("WEB-INF");

		Workflow workflow =WorkflowFactory.getPredefinedWorkflow(path, WorkflowFactory.WORKFLOW_HMM_POS_TAGGER);
		String basePart = "";
		String baseWord = "";
		try {
			/* Activate the work flow in the thread mode */
			workflow.activateWorkflow(true);
			/* Analysis using the work flow */
			String document = text;
			workflow.analyze(document);
			
			LinkedList<Sentence> resultList = workflow.getResultOfDocument(new Sentence(0, 0, false));
			for ( int h=0; h< resultList.size(); h++) {
				Sentence s = resultList.get(h);
				Eojeol[] eojeolArray = s.getEojeols();
				
				for (int i = 0; i < eojeolArray.length; i++) {
					if (eojeolArray[i].length > 0) {
						String[] morphemes = eojeolArray[i].getMorphemes();
						String[] tags = eojeolArray[i].getTags();
						
						String tagString = "";
						
						int pCount = 1;
						
						for (String string : tags) {	tagString += string.charAt(0);  }
						
						boolean endCheck = false;
						for (int j = 0; j < morphemes.length; j++) {
							
							switch (tags[j].charAt(0) ) {
								case 'J': case 'j':			// 관계언
								case 'S': case 's':		// 기호
									morphemes[j] = "";	// 생략 
								break;
								
								case 'E': case 'e':		// 어미
									if(endCheck){ morphemes[j] = "";	break;}// 단어가 끝이면 생략 
									if(tags[j].equals("ecx")) {// 보조적 연결어미일때
										 if((morphemes.length-1) == j) {	// 마지막 보조적 연결어미일경우 
											morphemes[j] = "";	// 생략 
										 }
									}else	{//나머지
										morphemes[j] = "";	// 생략 
									}
									break;
									
								case 'N': case 'n':
									if(tags[j].equals("nbs") || tags[j].equals("nbn")){
										morphemes[j] = "";	// 비단위성 의존명사 생략
									}else	{				
										if(tags[j].charAt(1) == 'p')	// 대명사
											basePart = "대명사";
										else 
											basePart = "명사";
									}
									break;
									
								case 'P':	case 'p':		// 용언	일 경우		
									if(endCheck){ morphemes[j] = "";	break;}// 단어가 끝이면 생략 
									int pCounter = tagString.split("p").length -1;	// 어절에서 용언의 갯수 
									if(pCounter > 1){	// 용언이 하나 이상이면 
										pCount++;	// 카운트를 더해주고
										if(tagString.split("p").length == pCount) {
											morphemes[j] += "다";	// 마지막 용언일 경우 기본형처리
											
											if(tags[j].charAt(1) == 'a')	// pa = 형용사
												basePart = "형용사";
											else if (tags[j].charAt(1) == 'v')	// pv == 동사
												basePart = "동사";
										}
									}else{
										morphemes[j] += "다";
										endCheck = true;
										
										if(tags[j].charAt(1) == 'a')	// pa = 형용사
											basePart = "형용사";
										else if (tags[j].charAt(1) == 'v')	// pv == 동사
											basePart = "동사";
									}
									break;
									
								case 'X':case 'x':		// 접사일 경우 
									morphemes[j] += "다";
									endCheck = true;
									break;
									
								case 'M':case'm': // 수식언
									if(tags[j].charAt(1) == 'a')	// 부사
										basePart = "부사";
									break;
								case 'I':case'i': // 감탄사
										basePart = "감탄사";
									break;
								default:	// 나머지
									break;
							}
						}
						
						for (String string : morphemes) 		baseWord += string;
					}
				}
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		workflow.close();
		/* Shutdown the work flow */
		 ModelAndView mav = new ModelAndView("extractResult");
		 mav.addObject("baseWord",baseWord);
		 mav.addObject("basePart",basePart);
		return mav;
	}
	@Override
	public ModelAndView extractService(@PathVariable String text,  HttpServletRequest request) throws Exception {
		String path = request.getRealPath("WEB-INF");

		Workflow workflow =WorkflowFactory.getPredefinedWorkflow(path, WorkflowFactory.WORKFLOW_HMM_POS_TAGGER);
		String basePart = "";
		String baseWord = "";
		try {
			/* Activate the work flow in the thread mode */
			workflow.activateWorkflow(true);
			/* Analysis using the work flow */
			String document = text;
			workflow.analyze(document);
			
			LinkedList<Sentence> resultList = workflow.getResultOfDocument(new Sentence(0, 0, false));
			for ( int h=0; h< resultList.size(); h++) {
				Sentence s = resultList.get(h);
				Eojeol[] eojeolArray = s.getEojeols();
				
				for (int i = 0; i < eojeolArray.length; i++) {
					if (eojeolArray[i].length > 0) {
						String[] morphemes = eojeolArray[i].getMorphemes();
						String[] tags = eojeolArray[i].getTags();
						
						String tagString = "";
						
						int pCount = 1;
						
						for (String string : tags) {	tagString += string.charAt(0);  }
						
						boolean endCheck = false;
						for (int j = 0; j < morphemes.length; j++) {
							
							switch (tags[j].charAt(0) ) {
								case 'J': case 'j':			// 관계언
								case 'S': case 's':		// 기호
									morphemes[j] = "";	// 생략 
								break;
								
								case 'E': case 'e':		// 어미
									if(endCheck){ morphemes[j] = "";	break;}// 단어가 끝이면 생략 
									if(tags[j].equals("ecx")) {// 보조적 연결어미일때
										 if((morphemes.length-1) == j) {	// 마지막 보조적 연결어미일경우 
											morphemes[j] = "";	// 생략 
										 }
									}else	{//나머지
										morphemes[j] = "";	// 생략 
									}
									break;
									
								case 'N': case 'n':
									if(tags[j].equals("nbs") || tags[j].equals("nbn")){
										morphemes[j] = "";	// 비단위성 의존명사 생략
									}else	{				
										if(tags[j].charAt(1) == 'p')	// 대명사
											basePart = "대명사";
										else 
											basePart = "명사";
									}
									break;
									
								case 'P':	case 'p':		// 용언	일 경우		
									if(endCheck){ morphemes[j] = "";	break;}// 단어가 끝이면 생략 
									int pCounter = tagString.split("p").length -1;	// 어절에서 용언의 갯수 
									if(pCounter > 1){	// 용언이 하나 이상이면 
										pCount++;	// 카운트를 더해주고
										if(tagString.split("p").length == pCount) {
											morphemes[j] += "다";	// 마지막 용언일 경우 기본형처리
											
											if(tags[j].charAt(1) == 'a')	// pa = 형용사
												basePart = "형용사";
											else if (tags[j].charAt(1) == 'v')	// pv == 동사
												basePart = "동사";
										}
									}else{
										morphemes[j] += "다";
										endCheck = true;
										
										if(tags[j].charAt(1) == 'a')	// pa = 형용사
											basePart = "형용사";
										else if (tags[j].charAt(1) == 'v')	// pv == 동사
											basePart = "동사";
									}
									break;
									
								case 'X':case 'x':		// 접사일 경우 
									morphemes[j] += "다";
									endCheck = true;
									break;
									
								case 'M':case'm': // 수식언
									if(tags[j].charAt(1) == 'a')	// 부사
										basePart = "부사";
									break;
								case 'I':case'i': // 감탄사
										basePart = "감탄사";
									break;
								default:	// 나머지
									break;
							}
						}
						
						for (String string : morphemes) 		baseWord += string;
					}
				}
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		workflow.close();
		/* Shutdown the work flow */
		 ModelAndView mav = new ModelAndView("extractResult");
		 mav.addObject("baseWord",baseWord);
		 mav.addObject("basePart",basePart);
		return mav;
	}
	
	@Override
	public ModelAndView searchAction(String text,  boolean bigram) throws Exception {
		ArrayList <String> phySearchList = null;
		ArrayList <String> cheSearchList = null;
		ArrayList <String> bioSearchList = null;
		ArrayList <String> earSearchList = null;
		if(bigram){
			ArrayList <String> bigramArray  = bigramOperation(text);
			 phySearchList = tagsDao.searchPhyDicByBigramText(bigramArray);
			 cheSearchList = tagsDao.searchCheDicByBigramText(bigramArray);
			 bioSearchList = tagsDao.searchBioDicByBigramText(bigramArray);
			 earSearchList = tagsDao.searchEarDicByBigramText(bigramArray);
		}else{
			phySearchList = tagsDao.searchPhyDicByText(text);
			cheSearchList = tagsDao.searchCheDicByText(text);
			bioSearchList = tagsDao.searchBioDicByText(text);
			earSearchList = tagsDao.searchEarDicByText(text);
		}
		
		ModelAndView mav = new ModelAndView("searchResult");
		mav.addObject("phySearchList", phySearchList);
		mav.addObject("cheSearchList", cheSearchList);
		mav.addObject("bioSearchList", bioSearchList);
		mav.addObject("earSearchList", earSearchList);
		return mav;
	}
	@Override
	public ModelAndView searchService(@PathVariable String text) throws Exception {
		ArrayList <String> bigramArray  = bigramOperation(text);
		
		ArrayList <String> phySearchList = tagsDao.searchPhyDicByBigramText(bigramArray);
		ArrayList <String> cheSearchList = tagsDao.searchCheDicByBigramText(bigramArray);
		ArrayList <String> bioSearchList = tagsDao.searchBioDicByBigramText(bigramArray);
		ArrayList <String> earSearchList = tagsDao.searchEarDicByBigramText(bigramArray);
		
		ModelAndView mav = new ModelAndView("searchResult");
		mav.addObject("phySearchList", phySearchList);
		mav.addObject("cheSearchList", cheSearchList);
		mav.addObject("bioSearchList", bioSearchList);
		mav.addObject("earSearchList", earSearchList);
		return mav;
	}
	
	@Override
	public ModelAndView bigramAction(String text) throws Exception {
		
		ArrayList <String> bigramArray = null;
		
		bigramArray = bigramOperation(text);
		
		 ModelAndView mav = new ModelAndView("bigramResult");
		 mav.addObject("bigramArray",bigramArray);
		return mav;
	}
	@Override
	public ModelAndView bigramService(@PathVariable String text) throws Exception {
		
		ArrayList <String> bigramArray = null;
		
		bigramArray = bigramOperation(text);
		
		 ModelAndView mav = new ModelAndView("bigramResult");
		 mav.addObject("bigramArray",bigramArray);
		return mav;
	}

	public ArrayList<String> bigramOperation(String text){
		ArrayList <String> bigramArray = null;
		
		if(text.length() < 3){// 2글자 미만일경우 그냥 리턴
			bigramArray = new ArrayList<String>();
			bigramArray.add(text);
			return bigramArray;
		}
		
		bigramArray = new ArrayList<String>();
		for(int i=0; i<text.length()-1; i++){
			String biText = text.substring(i, i+2);
			bigramArray.add(biText);
		}
		return bigramArray;
	}

	@Override
	public ModelAndView korDicResultAction(String baseWord, Integer idx) throws Exception {
		ArrayList<KorDictionaryVO> kdList = dictionaryDao.getDictionaryResult(baseWord);
		
		KorDictionaryVO dic_word = dictionaryDao.getKorDicWordNominee(baseWord);
		ModelAndView mav = new ModelAndView("kordicResult");
		mav.addObject("baseWord",baseWord);
		mav.addObject("kdList",kdList);
		mav.addObject("dic_word",dic_word);
		mav.addObject("idx", idx);
		return mav;
	}

	@Override
	public ModelAndView korDicResultAction(Board tag)
			throws Exception {
		tagsDao.updateWordNoByTagIdx(tag);
		return new ModelAndView("json", "status", "success");
	}

	@Override
	public ModelAndView insertAction(Board tag) throws Exception {
		tagsDao.updateTagsWordNumByAddition(tag);
		
		tag.setTag("추가됨");
		boardDao.insertTags(tag);
		return new ModelAndView("json", "status", "success");
	}
}
