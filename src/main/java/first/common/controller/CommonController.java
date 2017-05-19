package first.common.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import first.common.common.CommandMap;
import first.common.service.CommonService;


@Controller
public class CommonController {
    Logger log = Logger.getLogger(this.getClass());
     
    @Resource(name="commonService")
    private CommonService commonService;
    
    /**첨부파일 다운로드
     * 화면에서 특정 첨부파일 다운로드 요청
     * 서버에서 해당 첨부파일 정보 요청
     * DB에서 파일정보 조회
     * 조회된 데이터를 바탕으로 클라이언트에 데이터 전송 = 파일 정보를  response에 담아주는 것
     */
    
    /**
     * 예외처리나 보안관련 문제는 고려하지 않음
     */
    
    
    //끝나고 /sample/openselectdetail.do로 돌아가면서 같은 파라미터를 한번 더 받아오니까 casting 오류가 생김.
    @RequestMapping(value="/common/downloadFile.do")
    public void downloadFile(CommandMap commandMap, HttpServletResponse response) throws Exception{
    	
    	Map<String,Object> map = null;
    	String storedFileName;
    	String originalFileName;
    	
    	//파라미터 값이 여러개라 충돌이 일어나면서 CastException이 일어남
    	//변수를 받아오는 과정을 try에 넣고
    	try{
    		//1번 이상 다운로드 했을 때
	    	String[] arrStr = (String[])commandMap.get("IDX");
	    	String[] arrArg = (String[])arrStr[0].split(",");
	    	
	    	commandMap.put("IDX", arrArg[0]);
	    	
	    	map = commonService.selectFileInfo(commandMap.getMap());
	    } catch(ClassCastException e){
	    	//캐스팅 익셉션이 일어나면 처음 방식으로 파일 정보 읽어오기
	        map = commonService.selectFileInfo(commandMap.getMap());
	    } finally{
	        
	        storedFileName = (String)map.get("STORED_FILE_NAME");
	        originalFileName = (String)map.get("ORIGINAL_FILE_NAME");
	    
	    }
       
        //읽어온 파일을 byte[]형태로 변환
        byte fileByte[] = FileUtils.readFileToByteArray(new File("C:\\dev\\file\\"+storedFileName));
         
        //읽어들인 파일 정보를 화면에서 다운로드 할 수 있도록 변환
        response.setContentType("application/octet-stream");
        response.setContentLength(fileByte.length);
        //content-disposition 속성 : 해당 패킷이 어떤 형식의 데이터인가
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(originalFileName,"UTF-8")+"\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.getOutputStream().write(fileByte);
        
        response.getOutputStream().flush();
        response.getOutputStream().close();
	    
    }
    
}



