package first.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
 
import javax.servlet.http.HttpServletRequest;
 
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
 
//객체 관리를 스프링이 담당하도록
@Component("fileUtils")
public class FileUtils {
    private static final String filePath = "C:\\dev\\file\\";
     
    public List<Map<String,Object>> parseInsertFileInfo(Map<String,Object> map, HttpServletRequest request) throws Exception{
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
         
        MultipartFile multipartFile = null;
        String originalFileName = null;
        String originalFileExtension = null;
        String storedFileName = null;
         
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String, Object> listMap = null;
         
        String boardIdx = (String)map.get("IDX");
         
        File file = new File(filePath);
        if(file.exists() == false){
            file.mkdirs();
        }
         
        while(iterator.hasNext()){
            multipartFile = multipartHttpServletRequest.getFile(iterator.next());
            if(multipartFile.isEmpty() == false){
                originalFileName = multipartFile.getOriginalFilename();
                originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                storedFileName = CommonUtils.getRandomString() + originalFileExtension;
                 
                file = new File(filePath + storedFileName);
                multipartFile.transferTo(file);
                 
                listMap = new HashMap<String,Object>();
                listMap.put("BOARD_IDX", boardIdx);
                listMap.put("ORIGINAL_FILE_NAME", originalFileName);
                listMap.put("STORED_FILE_NAME", storedFileName);
                listMap.put("FILE_SIZE", multipartFile.getSize());
                list.add(listMap);
            }
        }
        return list;
    }



         
    public List<Map<String, Object>> parseUpdateFileInfo(Map<String, Object> map, HttpServletRequest request) throws Exception{
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
         
        MultipartFile multipartFile = null;
        String originalFileName = null;
        String originalFileExtension = null;
        String storedFileName = null;


         
        //클라이언트에서 전송된 파일 정보를 담아서 반환해줄 list
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String, Object> listMap = null;
         
        //map에서 신규 생성되는 게시글의 번호
        String boardIdx = (String)map.get("IDX");
        String requestName = null;
        String idx = null;
        
        while(iterator.hasNext()){
            multipartFile = multipartHttpServletRequest.getFile(iterator.next());
            
            //multifile이 비어있지 않은 경우 새롭게 저장해야함
            //삭제값으로 변경
            if(multipartFile.isEmpty() == false){
                originalFileName = multipartFile.getOriginalFilename();
                originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                storedFileName = CommonUtils.getRandomString() + originalFileExtension;
                
                multipartFile.transferTo(new File(filePath + storedFileName));
                 
                listMap = new HashMap<String,Object>();
                listMap.put("IS_NEW", "Y");
                listMap.put("BOARD_IDX", boardIdx);
                listMap.put("ORIGINAL_FILE_NAME", originalFileName);
                listMap.put("STORED_FILE_NAME", storedFileName);
                listMap.put("FILE_SIZE", multipartFile.getSize());
                list.add(listMap);
                
            } else {
            	/**빈 multifile을 받는경우(게시글에서 파일을 수정하지 않았을 경우)
            	 * 기존에 저장되어있던 내용인지 단순히 빈 파일인지 구분이 필요함
            	 */
            	//file태그의 name값을 가져옴
            	requestName = multipartFile.getName();
            	//기존에 저장된 파일의 경우 input 태그에 IDX_숫자로 name을 설정해두었음 그걸 만들기
            	idx = "IDX_" + requestName.substring(requestName.indexOf("_")+1);
            	
            	//IDX_숫자 의 값이 존재한다면 기존에 있던 파일이므로 삭제 태그를 "N"로 변경
            	if(map.containsKey(idx) == true && map.get(idx) != null){
            		listMap = new HashMap<String, Object>();
            		listMap.put("IS_NEW", "N");
            		listMap.put("FILE_IDX", map.get(idx));
            		list.add(listMap);
            	}
            }
        }
        return list;
    }
}


