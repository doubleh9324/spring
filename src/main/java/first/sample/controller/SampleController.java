package first.sample.controller;

import first.common.common.CommandMap;
import first.common.controller.CommonController;
import first.sample.service.SampleService;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
/*
@Controller
public class SampleController {
    Logger log = Logger.getLogger(this.getClass());
     
    @RequestMapping(value="/sample/openSampleList.do")
    public ModelAndView openSampleList(Map<String,Object> commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("");
        log.debug("인터셉터 테스트");
         
        return mv;
    }
}
*/

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;


//객체 선언
@Controller
public class SampleController {
    Logger log = Logger.getLogger(this.getClass());
     
    //서비스영역의 접근을 위한 선언. 어노테이션을 통해 필요한 bean을 수동으로 등록.
    @Resource(name="sampleService")
    private SampleService sampleService;
     
    //요청 URL. 해당 주소를 호출하면 이 주소와 어노테이션이 매핑되어 해당 메소드가 실행.
    @RequestMapping(value="/sample/openBoardListEgov.do")
    //화면에 보여줄 jsp 파일 의미
    public ModelAndView openBoardListEgov(CommandMap commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("/sample/boardList");

        
        //목록을 저장할 List 선언. 다양한 내용을 map에 저장, map의 키 값 두가지.
        //실제 여러가지 비즈니스 로직은 service에서 작성. (조회수 증가, 게시글 읽기 등등)
        //Map<String,Object> resultMap = sampleService.selectBoardList(commandMap.getMap());
        //서비스 로직의 경과를 객체에 담아서 클라이언트로 전송.
       // mv.addObject("list", list);
        
        Map<String,Object> resultMap = sampleService.selectBoardListEgov(commandMap.getMap());
        
        mv.addObject("paginationInfo", (PaginationInfo)resultMap.get("paginationInfo"));
        mv.addObject("list", resultMap.get("result"));

         
        return mv;
    }
    
    @RequestMapping(value="/sample/openBoardList.do")
    public ModelAndView openBoardList(CommandMap commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("/sample/boardList");
         
        return mv;
    }

    @RequestMapping(value="/sample/selectBoardList.do")
    public ModelAndView selectBoardList(CommandMap commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("jsonView");
         
        List<Map<String,Object>> list = sampleService.selectBoardList(commandMap.getMap());
        mv.addObject("list", list);
        if(list.size() > 0){
            mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
        }
        else{
            mv.addObject("TOTAL", 0);
        }
         
        return mv;
    }



	@RequestMapping(value="/sample/testMapArgumentResolver.do")
	public ModelAndView testMapArgumentResolver(CommandMap commandMap) throws Exception{
	    ModelAndView mv = new ModelAndView("");
	     
	    if(commandMap.isEmpty() == false){
	        Iterator<Entry<String,Object>> iterator = commandMap.getMap().entrySet().iterator();
	        Entry<String,Object> entry = null;
	        while(iterator.hasNext()){
	            entry = iterator.next();
	            log.debug("key : "+entry.getKey()+", value : "+entry.getValue());
	        }
	    }
	    return mv;
	}
	
	@RequestMapping(value="/sample/openBoardWrite.do")
	public ModelAndView openBoardWrite(CommandMap commandMap) throws Exception{
	    ModelAndView mv = new ModelAndView("/sample/boardWrite");
	     
	    return mv;
	}
	
	@RequestMapping(value="/sample/insertBoard.do")
	public ModelAndView insertBoard(CommandMap commandMap, HttpServletRequest request) throws Exception{
	    ModelAndView mv = new ModelAndView("redirect:/sample/openBoardList.do");
	     
	    sampleService.insertBoard(commandMap.getMap(), request);
	     
	    return mv;
	}


	@RequestMapping(value="/sample/openBoardDetail.do")
	public ModelAndView openBoardDetail(CommandMap commandMap) throws Exception{
		CommonController cc = new CommonController();
		
		ModelAndView mv = new ModelAndView("/sample/boardDetail");
		
		Map<String, Object> map = sampleService.selectBoardDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));	//기존글의 상세정보
		mv.addObject("list", map.get("list"));	//첨부파일 목록
		
		return mv;
	}
	
	@RequestMapping(value="/sample/openBoardUpdate.do")
	public ModelAndView openBoardUpdate(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/sample/boardUpdate");
		
		Map<String, Object> map = sampleService.selectBoardDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		mv.addObject("list", map.get("list"));
		
		return mv;
	}
	
	@RequestMapping(value="/sample/updateBoard.do")
	public ModelAndView updateBoard(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/sample/openBoardDetail.do");
		
		sampleService.updateBoard(commandMap.getMap(), request);
		
		mv.addObject("IDX", commandMap.get("IDX"));
		return mv;
	}
	
	@RequestMapping(value="/sample/deleteBoard.do")
	public ModelAndView deleteBoard(CommandMap commandMap) throws Exception{
	    ModelAndView mv = new ModelAndView("redirect:/sample/openBoardList.do");
	     
	    sampleService.deleteBoard(commandMap.getMap());
	    return mv;
	}
	

}






