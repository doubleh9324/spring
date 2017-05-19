package first.sample.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import first.common.util.FileUtils;
import first.sample.dao.SampleDAO;

//이름이 " " 인 서비스 객체 선언.
@Service("sampleService")
public class SampleServiceImpl implements SampleService{
	Logger log = Logger.getLogger(this.getClass());
	
    @Resource(name="fileUtils")
    private FileUtils fileUtils;

	@Resource(name="sampleDAO")
    private SampleDAO sampleDAO;
	
	@Override
	public List<Map<String, Object>> selectBoardList(Map<String, Object> map) throws Exception {
		return sampleDAO.selectBoardList(map);
	}

	@Override
	public void insertBoard(Map<String, Object> map, HttpServletRequest request) throws Exception {
	    sampleDAO.insertBoard(map);
	     
	    /*
	    MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
	    //iterator : 어떤 데이터들의 집합체에서 컬렉션으로부터 정보를 얻어올 수 있는 인터페이스
	    //map, set은 순차접근이 불가능. map에 있는 데이터를 while문을 이용하여 순차적으로 접근하려고함
	    Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
	    MultipartFile multipartFile = null;
	    
	    while(iterator.hasNext()){
	        multipartFile = multipartHttpServletRequest.getFile(iterator.next());
	        if(multipartFile.isEmpty() == false){
	            log.debug("------------- file start -------------");
	            log.debug("name : "+multipartFile.getName());
	            log.debug("filename : "+multipartFile.getOriginalFilename());
	            log.debug("size : "+multipartFile.getSize());
	            log.debug("-------------- file end --------------\n");
	        }
	    }
	    */
	    
        List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
        for(int i=0, size=list.size(); i<size; i++){
            sampleDAO.insertFile(list.get(i));
        }

	}

	@Override
	public Map<String, Object> selectBoardDetail(Map<String, Object> map) throws Exception {
		sampleDAO.updateHitCnt(map);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		//게시글 상세정보를 map이라는 이름으로 resultmap에 저장
		Map<String, Object> tempMap = sampleDAO.selectBoardDetail(map);
		resultMap.put("map", tempMap);
		//게시판 첨부파일 목록을 가져와서 list란 이름으로 resultmap에 저장
		List<Map<String, Object>> list = sampleDAO.selectFileList(map);
		resultMap.put("list", list);
				
		return resultMap;
	}

	@Override
	public void updateBoard(Map<String, Object> map, HttpServletRequest request) throws Exception {
		sampleDAO.updateBoard(map);
		
		//해당 게시글의 첨부파일 목록을 삭제처리(DEL_GB = 'Y')
		sampleDAO.deleteFileList(map);
		
		//request에 담긴 파일정보를 list로 변환
		List<Map<String,Object>> list = fileUtils.parseUpdateFileInfo(map, request);
	    Map<String,Object> tempMap = null;
	    
	    for(int i=0, size=list.size(); i<size; i++){
	        tempMap = list.get(i);

        	System.out.println(tempMap.get("IDX")+" :IDX");
	        if(tempMap.get("IS_NEW").equals("Y")){
	            sampleDAO.insertFile(tempMap);
	            
	            System.out.println("Y");
	            System.out.println(tempMap.get("IDX")+" :IDX");
	        }
	        else{
	        	System.out.println("N");
	        	System.out.println(tempMap.get("IDX")+" :IDX");
	        	
	            sampleDAO.updateFile(tempMap);
	        }
	    }
	}
	
	@Override
	public void deleteBoard(Map<String, Object> map) throws Exception {
	    sampleDAO.deleteBoard(map);
	}


 
}


