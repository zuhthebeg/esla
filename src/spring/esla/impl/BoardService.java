package spring.esla.impl;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;

import kr.ac.kaist.swrc.jhannanum.comm.Eojeol;
import kr.ac.kaist.swrc.jhannanum.comm.Sentence;
import kr.ac.kaist.swrc.jhannanum.demo.WorkflowHmmPosTagger;
import kr.ac.kaist.swrc.jhannanum.hannanum.Workflow;
import kr.ac.kaist.swrc.jhannanum.hannanum.WorkflowFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import spring.esla.beans.Board;
import spring.esla.beans.Definition;
import spring.esla.beans.KorDictionaryVO;
import spring.esla.beans.Member;
import spring.esla.beans.Speechpart;
import spring.esla.controller.BoardController;
import spring.esla.model.BoardDao;
import spring.esla.model.DictionaryDao;
import spring.esla.model.DocumentDao;
import spring.esla.model.MemberDao;
import spring.esla.model.TagsDao;
import spring.esla.view.XmlParser;
import spring.esla.view.XmlParsing;
@Service
public class BoardService extends BoardController {
	@Autowired
	private BoardDao boardDao;
	@Autowired
	private DocumentDao documentDao;
	@Autowired
	private WorkflowHmmPosTagger workflowHmmPosTagger;
	
	@Autowired
	private DictionaryDao dictionaryDao;
	
	@Autowired
	private MemberDao memberDao;

	@Override
	public ModelAndView mainAction() throws Exception {

		//ArrayList<Board> articleList = boardDao.getArticleList(page);		
		ModelAndView mav = new ModelAndView("main");
		//mav.addObject("articleList", articleList);	
		//mav.addObject("page", page);
		return mav;
	}

	@Override
	public ModelAndView insertAction(@ModelAttribute Board article, HttpServletRequest request)
			throws Exception {
		MultipartFile file = article.getFile();
		if(!(file  == null)){
			if(!file.isEmpty()){		
				String filename = file.getOriginalFilename();		
				File tempfile =new File(request.getRealPath("/upload"), file.getOriginalFilename());	
				if(tempfile.exists() && tempfile.isFile()){	
					filename =System.currentTimeMillis()  +"_"+ file.getOriginalFilename() ;
					tempfile = new File(request.getRealPath("/upload"),filename);
				}
				file.transferTo(tempfile);	
				article.setFilename(filename);
			}
		}
		
		article.setRegip(request.getRemoteAddr());
		article.setCount(0);
		
		int article_idx = boardDao.insertArticle(article);	
		article.setIdx(article_idx);
	//	workflowHmmPosTagger.main();
		nounExtractor(request, article);
		return new ModelAndView("json");	
	}

	public void nounExtractor( HttpServletRequest request, Board article) {
		
		String path = request.getRealPath("WEB-INF");

		Workflow workflow =WorkflowFactory.getPredefinedWorkflow(path, WorkflowFactory.WORKFLOW_HMM_POS_TAGGER);

		try {
			/* Activate the work flow in the thread mode */
			workflow.activateWorkflow(true);
			/* Analysis using the work flow */
			String document = article.getContent();
			/*
			MultipartFile file = article.getFile();
			if(!(file  == null)){
				if(!file.isEmpty()){		
					String filename = file.getOriginalFilename();		
					File tempfile =new File(request.getRealPath("/upload"), file.getOriginalFilename());	
				}
			*/
			
			document = document.replace(System.getProperty("line.separator"), " ");		// 개행문자 제거
			
			workflow.analyze(document);
			
			Board metainfo = new Board();
			metainfo.setIdx(article.getIdx());
			
			LinkedList<Sentence> resultList = workflow.getResultOfDocument(new Sentence(0, 0, false));
			
			for ( int h=0; h< resultList.size(); h++) {
				
				
				Sentence s = resultList.get(h);
				metainfo.setSentence(h+1);	// 문장번호 셋팅
				Eojeol[] eojeolArray = s.getEojeols();
				String[] wordList = s.getPlainEojeols();
				
				for (int i = 0; i < eojeolArray.length; i++) {
					if (eojeolArray[i].length > 0) {
						String[] morphemes = eojeolArray[i].getMorphemes();
						String[] tags = eojeolArray[i].getTags();
						
						
						String tagString = "";
						String wordClass = "";
						int pCount = 1;
						
						for (String string : tags) {	tagString += string.charAt(0);  }
						
						metainfo.setTag(wordList[i]);	// 단어 셋팅
						
						/*
						 * 기본형 추출 모듈
						 */
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
											wordClass = "대명사";
										else 
											wordClass = "명사";
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
												wordClass = "형용사";
											else if (tags[j].charAt(1) == 'v')	// pv == 동사
												wordClass = "동사";
										}
									}else{
										morphemes[j] += "다";
										endCheck = true;
										
										if(tags[j].charAt(1) == 'a')	// pa = 형용사
											wordClass = "형용사";
										else if (tags[j].charAt(1) == 'v')	// pv == 동사
											wordClass = "동사";
									}
									break;
									
								case 'X':case 'x':		// 접사일 경우 
									morphemes[j] += "다";
									endCheck = true;
									break;
									
								case 'M':case'm': // 수식언
									if(tags[j].charAt(1) == 'a')	// 부사
										wordClass = "부사";
									break;
								case 'I':case'i': // 감탄사
										wordClass = "감탄사";
									break;
								default:	// 나머지
									break;
							}
						
						}
						
						String baseWord = "";
						for (String string : morphemes) 		baseWord += string;
	
							if(!metainfo.getTag().equals(".") && baseWord.length() > 0){
							metainfo.setBase(baseWord);	// 기본형 셋팅; 
							/*
							 * 기본형 추출 모듈 끝
							 */						
							//사전별 검색 결과 카운트 셋팅 
							int phy_count=0 , che_count=0, bio_count=0, ear_count=0;
							phy_count = documentDao.getDictionaryCountByBaseCount(baseWord, "DIC_PHYSICS");
							che_count = documentDao.getDictionaryCountByBaseCount(baseWord, "DIC_CHEMISTRY");
							bio_count = documentDao.getDictionaryCountByBaseCount(baseWord, "DIC_BIOLOGY" );
							ear_count = documentDao.getDictionaryCountByBaseCount(baseWord, "DIC_EARTH");
							
							//표준 국어 대사전 카운트(일반)
							Integer result_std_count =  dictionaryDao.getKorDicWordCountIsNull(baseWord);
							if(result_std_count != null)	 metainfo.setResult_std_count(result_std_count);
							//표준 국어 대사전 카운트(전문)
							Integer result_count = dictionaryDao.getKorDicWordCount(baseWord);
							if(result_count != null)		metainfo.setResult_count(result_count);
							/*
							 * 표준 국어 대사전 검색결과
							 */		
							KorDictionaryVO dic_word = dictionaryDao.getKorDicWordNominee(baseWord);
							if(dic_word != null){
								metainfo.setWord_no(dic_word.getWord_no());
								metainfo.setTech_term_name(documentDao.getTechTermNamesByWordNo(dic_word.getWord_no()));
								metainfo.setWord_class(dic_word.getSp_name());	
								
							
								if(dic_word.getTech_term_name() != null){	// 전문어 분류가 null이 아니라면
									//사전 결과 카운트 가산 셋팅
									if(dic_word.getTech_term_name().equals("물리") || dic_word.getTech_term_name().equals("전기")){
										phy_count++;
									}else if(dic_word.getTech_term_name().equals("화학")){
										che_count++;
									}else if(dic_word.getTech_term_name().equals("생물") || dic_word.getTech_term_name().equals("식물")  || dic_word.getTech_term_name().equals("동물")){
										bio_count++;
									}else if(dic_word.getTech_term_name().equals("천문") || dic_word.getTech_term_name().equals("해양")|| dic_word.getTech_term_name().equals("지리")|| dic_word.getTech_term_name().equals("광업")){
										ear_count++;
									}
								}
								
								int max[]  = {phy_count, che_count, bio_count, ear_count};	// 가장 유력한 분류를 토대로 전문어 분류
								 int maxCount = 0;
								
								 int maxValue = max[0];
								for(int k=1;k<4; k++){
									if(maxValue <= max[k]){
										maxValue = max[k];
										maxCount = k;
									}
								}
								if(maxCount == 0)	metainfo.setDic_physics_cnt(1);
								if(maxCount == 1) metainfo.setDic_chemistry_cnt(1);
								if(maxCount == 2) metainfo.setDic_biology_cnt(1);
								if(maxCount == 3) metainfo.setDic_earth_cnt(1);
								
							}else{
								metainfo.setDic_physics_cnt(phy_count);
								metainfo.setDic_chemistry_cnt(che_count);
								metainfo.setDic_biology_cnt(bio_count);
								metainfo.setDic_earth_cnt(ear_count);
							}
							metainfo.setPart("대단원");		// 구분 기본 '대단원' 셋팅
							metainfo.setWord_class(wordClass);		// 태그 셋팅
							metainfo.setWord((i+1));	// 단어번호 셋팅
							boardDao.insertTags(metainfo );
							metainfo.setClearTagMetainfo();
						}
					}
				}
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			//System.exit(0);
		}
		workflow.close();
		/* Shutdown the work flow */
	}
	@Override
	public ModelAndView ajaxListAction(Integer page) throws Exception {
		if(page == null){				
			page = 0;		
		}			 
			ArrayList<Board> articleList =boardDao.getArticleList(page);	
			return new ModelAndView("ajaxList", "articleList", articleList);
		}
	@Override
	public ModelAndView pageListAction(Integer idx) throws Exception {
			ArrayList<Board> pageList =documentDao.getPageListByDocumentIdx(idx);	
			return new ModelAndView("list", "articleList", pageList);
		}
	@Override
	public ModelAndView writeAction(Integer doc_idx) throws Exception {
		if(doc_idx == null) return new ModelAndView("write");
		
		HashMap<String, Object > metaMap = new HashMap<String, Object>();
		metaMap.put("chapter", documentDao.getMetainfoByDocumentIdx(doc_idx).getVal());	//단원정보
		metaMap.put("title", documentDao.getTitleByDocumentIdx(doc_idx).getVal());	//단원정보
		metaMap.put("publisher", documentDao.getPublisherByDocumentIdx(doc_idx).getVal());	//출판사정보
		metaMap.put("grade", documentDao.getGradeByDocumentIdx(doc_idx).getVal());	//학년정보
		metaMap.put("process", documentDao.getProcessByDocumentIdx(doc_idx).getVal());	//교육과정정보

		ModelAndView mav = new ModelAndView("write");
		mav.addAllObjects(metaMap);
		mav.addObject("articleCount", documentDao.getPageCountByDocumentIdx(doc_idx));
		mav.addObject("doc_idx", doc_idx);
		return mav;
	}

	@Override
	public ModelAndView countAction(Integer idx, HttpServletRequest request) throws Exception {
		Board article =boardDao.getArticle(idx);		
		String regip = request.getRemoteAddr();	

		if(!regip.equals(article.getRegip())){			
			int count = article.getCount();				
			article.setCount(++count);					
			boardDao.setArticleCount(article);	
		}
		return new ModelAndView("redirect:content","idx", idx);
	}

	@Override
	public ModelAndView contentAction(Integer idx) throws Exception {
		ModelAndView mav = new ModelAndView("content");
		mav.addObject("tags", boardDao.getTagsByIdx(idx));
		mav.addObject("article", boardDao.getArticle(idx));
		
		return mav;
	}

	@Override
	public ModelAndView tagsAction(Integer idx) throws Exception {
		ModelAndView mav = new ModelAndView("tagList");
		mav.addObject("tags", boardDao.getTagsByIdx(idx));
		mav.addObject("article", boardDao.getArticle(idx));
		return mav;
	}
	
	@Override
	public ModelAndView pageExcelAction(@PathVariable Integer idx) throws Exception {
		ModelAndView mav = new ModelAndView("excel");
		mav.addObject("tags", boardDao.getTagsByIdx(idx));
		mav.addObject("article", boardDao.getArticle(idx));
		return mav;
	}
	@Override
	public ModelAndView chapterExcelAction(@PathVariable Integer doc_idx) throws Exception {
		
		ModelAndView mav = new ModelAndView("doc_excel");
		ArrayList<Board> tagList = boardDao.getTagsByDocumentIdxWithMetainfo(doc_idx);
		mav.addObject("tags", tagList);
		return mav;
	}
		
	@Override
	public ModelAndView deleteAction(Integer idx, HttpServletRequest request) throws Exception {
		Board article = boardDao.getArticle(idx);
		String filename = article.getFilename();
		String uploadFileName = request.getRealPath("/upload") +"/"+ filename;

		File uploadfile = new File (uploadFileName);
		if ( uploadfile.exists()&& uploadfile.isFile() )
		{
			 uploadfile.delete();		
		}
		boardDao.deleteArticle(idx);
		return new ModelAndView("json", "status", "success");
	}

	@Override
	public ModelAndView downloadAction(@PathVariable Integer idx, HttpServletRequest request)
			throws Exception {
		Board article = boardDao.getArticle(idx);
		String filename = article.getFilename();
		
		String uploadFileName = request.getRealPath("/upload") +"/"+ filename;
		
		File downFile = new File(uploadFileName);
		return new ModelAndView("downloadView", "file", downFile);
	}

	@Override
	public ModelAndView documentAction(Integer num, HttpServletRequest request)
			throws Exception {
		String uploadFileName = request.getRealPath("/document") +"/"+ "document.pptx";
		
		File downFile = new File(uploadFileName);
		return new ModelAndView("downloadView", "file", downFile);
	}

	@Override
	public ModelAndView loginAction(Member user, HttpServletRequest request) throws Exception {
		String status = "";
		
		Member mem = memberDao.getUserByAccount(user);
		
		if(mem != null){
			status = "success";
			request.getSession().setAttribute("user", mem);
		}	else
			status = "fail";
		
		return new ModelAndView("json", "status", status);
	}

	@Override
	public ModelAndView exceptionAction() throws Exception {
		return new ModelAndView("exception", "msg", "권한이 없습니다.");
	}

	@Override
	public ModelAndView logoutAction(HttpServletRequest request)
			throws Exception {
		request.getSession().invalidate();
		return new ModelAndView("redirect:main");
	}

}
