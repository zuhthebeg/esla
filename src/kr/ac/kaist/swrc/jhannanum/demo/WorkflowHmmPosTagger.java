/*  Copyright 2010, 2011 Semantic Web Research Center, KAIST

This file is part of JHanNanum.

JHanNanum is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

JHanNanum is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with JHanNanum.  If not, see <http://www.gnu.org/licenses/>   */

package kr.ac.kaist.swrc.jhannanum.demo;

import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.esla.model.BoardDao;

import kr.ac.kaist.swrc.jhannanum.comm.Eojeol;
import kr.ac.kaist.swrc.jhannanum.comm.Sentence;
import kr.ac.kaist.swrc.jhannanum.hannanum.Workflow;
import kr.ac.kaist.swrc.jhannanum.hannanum.WorkflowFactory;
import kr.ac.kaist.swrc.jhannanum.plugin.MajorPlugin.MorphAnalyzer.ChartMorphAnalyzer.ChartMorphAnalyzer;
import kr.ac.kaist.swrc.jhannanum.plugin.MajorPlugin.PosTagger.HmmPosTagger.HMMTagger;
import kr.ac.kaist.swrc.jhannanum.plugin.SupplementPlugin.MorphemeProcessor.UnknownMorphProcessor.UnknownProcessor;
import kr.ac.kaist.swrc.jhannanum.plugin.SupplementPlugin.PlainTextProcessor.InformalSentenceFilter.InformalSentenceFilter;
import kr.ac.kaist.swrc.jhannanum.plugin.SupplementPlugin.PlainTextProcessor.SentenceSegmentor.SentenceSegmentor;

/**
 * This is a demo program of HanNanum that helps users to utilize the HanNanum library easily.
 * It uses a predefined work flow for POS tagging, which is good for general use.<br>
 * <br>
 * It performs morphological analysis and POS tagging for a Korean document with the following procedure:<br>
 * 		1. Create a predefined work flow for morphological analysis and POS tagging.<br>
 * 		2. Activate the work flow in multi-thread mode.<br>
 * 		3. Analyze a document that consists of several sentences.<br>
 * 		4. Print the result on the console.<br>
 * 		5. Repeats the procedure 3~4 with activated work flow.<br>
 * 		6. Close the work flow.<br>
 * 
 * @author Sangwon Park (hudoni@world.kaist.ac.kr), CILab, SWRC, KAIST
 */

@Component
public class WorkflowHmmPosTagger {
	@Autowired
	private BoardDao boardDao;
	public static void main( String[] argc) { //HttpServletRequest request

		//String path =request.getRealPath("WEB-INF");

		Workflow workflow = WorkflowFactory.getPredefinedWorkflow("C:\\Users\\cocy\\Desktop\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp2\\wtpwebapps\\esla\\WEB-INF",WorkflowFactory.WORKFLOW_HMM_POS_TAGGER);
		try {
			/* Activate the work flow in the thread mode */
			workflow.activateWorkflow(false);
			
			/* Analysis using the work flow */
			String document = "질량이 같은 물체는 같은 속도로 운동하면 같은 에너지를 갖는다. ";
			
			workflow.analyze(document);
			//System.out.println(workflow.getResultOfDocument());
			
			LinkedList<Sentence> resultList = workflow.getResultOfDocument(new Sentence(0, 0, false));
			for ( int h=0; h< resultList.size(); h++) {
				Sentence s = resultList.get(h);
				//System.out.print("문장번호 : " + (h+1));
				Eojeol[] eojeolArray = s.getEojeols();
				for (int i = 0; i < eojeolArray.length; i++) {
										
					if (eojeolArray[i].length > 0) {
						String[] morphemes = eojeolArray[i].getMorphemes();
						String[] tags = eojeolArray[i].getTags();
						
						String tagString = "";
						int pCount = 1;
						
						for (String string : morphemes) 		System.out.print(string); 
						System.out.print("/");
						for (String string : tags) {	System.out.print(string); tagString += string.charAt(0);  }
						System.out.println( "--------------------------------------------------------------- ");
						
						boolean endCheck = false;
						for (int j = 0; j < morphemes.length; j++) {
							System.out.println( "size : " + morphemes.length + " /			단어 : " +morphemes[j] + " / 태그 : " + tags[j]);
							switch (tags[j].charAt(0) ) {
								case 'J': case 'j':			// 관계언
								case 'S': case 's':		// 기호
									morphemes[j] = "";	// 생략 
								break;
								
								case 'E': case 'e':		// 어미
									if(endCheck){ morphemes[j] = "";	break;}// 단어가 끝이면 생략 
									if(tags[j].equals("ecx")) {// 보조적 연결어미일때
										 if((morphemes.length-1) == j)	// 마지막 보조적 연결어미일경우 
											morphemes[j] = "";	// 생략 
										//else if(tags[j-1].charAt(0) == 'p' && tags[j+1].charAt(0) == 'p')	// 앞뒤로 용언
											//System.out.println("why this case is shit!");//morphemes[j] = "";	// 생략 
									}else	//나머지
										morphemes[j] = "";	// 생략 
									break;
									
								case 'N': case 'n':
									if(tags[j].equals("nbs") || tags[j].equals("nbn")) morphemes[j] = "";	// 비단위성 의존명사 생략 
									break;
									
								case 'P':	case 'p':		// 용언	일 경우		
									if(endCheck){ morphemes[j] = "";	break;}// 단어가 끝이면 생략 
									int pCounter = tagString.split("p").length -1;	// 어절에서 용언의 갯수 
									if(pCounter > 1){	// 용언이 하나 이상이면 
										pCount++;	// 카운트를 더해주고
										if(tagString.split("p").length == pCount) morphemes[j] += "다";	// 마지막 용언일 경우 기본형처리
									}else{
										morphemes[j] += "다";
										endCheck = true;
									}
									break;
									
								case 'X':case 'x':		// 접사일 경우 
									//morphemes[j]  = "";
									morphemes[j] += "다";
									endCheck = true;
									break;
								default:	// 나머지
									break;
							}
						
							 
						}
						System.out.print( "---------------------------------------------------------------");
						for (String string : morphemes) 		System.out.print(string); 
						System.out.print("/");
						for (String string : tags) 	System.out.print(string); 
						System.out.println();
					}
					
					
				}
			}
			
			workflow.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		/* Shutdown the work flow */
		workflow.close();  	
	}
}