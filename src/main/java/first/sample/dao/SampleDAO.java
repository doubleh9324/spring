package first.sample.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import first.common.dao.AbstractDAO;


/**
 * Dao는 db에 접슨하여 데이터를 조작하는 역할만 수행.
 * mybatis는 소스코드에서 sql을 분리하여 별도의 xml 파일에 저장하고 이 둘을 연결하는 방식으로 작동.
 * 실행할 쿼리 이름과 해당 쿼리에 필요한 변수들을 매개변수로 mybatis의 리스트 조회 메소드 호출
 *
 */
@Repository("sampleDAO")
public class SampleDAO extends AbstractDAO{

	//컴파일러의 경고 중 unchecked(검증되지 않은 연산자 관련 경고)를 억제
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectBoardList(Map<String, Object> map) 
	throws Exception {
		//selectList(쿼리이름, 실행하는데 필요한 변수)
		return (List<Map<String, Object>>)selectList("sample.selectBoardList", map);
	}
	
	public void insertBoard(Map<String, Object> map) throws Exception{
	    insert("sample.insertBoard", map);
	}

	public void updateHitCnt(Map<String, Object> map) {
		update("sample.updateHitCnt", map);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> selectBoardDetail(Map<String, Object> map) {
		return (Map<String, Object>)selectOne("sample.selectBoardDetail", map);
	}

	public void updateBoard(Map<String, Object> map) {
		update("sample.updateBoard", map);
	}

	public void deleteBoard(Map<String, Object> map) {
		update("sample.deleteBoard", map);
	}
	
	public void insertFile(Map<String, Object> map) throws Exception{
	    insert("sample.insertFile", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectFileList(Map<String, Object> map) throws Exception {
		return (List<Map<String, Object>>)selectList("sample.selectFileList", map);
	}

	public void deleteFileList(Map<String, Object> map) throws Exception{
	    update("sample.deleteFileList", map);
	}
	 
	public void updateFile(Map<String, Object> map) throws Exception{
	    update("sample.updateFile", map);
	}

}
