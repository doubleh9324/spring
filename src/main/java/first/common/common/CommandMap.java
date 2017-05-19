package first.common.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 컨트롤러의 
 * public ModelAndView openSampleBoardList(Map<String,Object> commandMap) throws Exception
 * Map<String, Object> commandMap에 사용자가 넘겨준 파라미터 저장.
 * HandlerMethodArgumentResolver는 컨트롤러의 파라미터가 Map 형식이면 동작하지 않음.
 * <mvc:annotaion-driven/> 선언시 스프링에서 기본적으로 설정된 ArgumentResolver를 거치게됨.
 * 때문에 map을 대신할 commandmap작성.
 ***Map을 상속받으면 작성한 ArgumentResolver를 사용 할 수 없음!!
 */
public class CommandMap {
    Map<String,Object> map = new HashMap<String,Object>();
     
    public Object get(String key){
        return map.get(key);
    }
     
    public void put(String key, Object value){
        map.put(key, value);
    }
     
    public Object remove(String key){
        return map.remove(key);
    }
     
    public boolean containsKey(String key){
        return map.containsKey(key);
    }
     
    public boolean containsValue(Object value){
        return map.containsValue(value);
    }
     
    public void clear(){
        map.clear();
    }
     
    public Set<Entry<String, Object>> entrySet(){
        return map.entrySet();
    }
     
    public Set<String> keySet(){
        return map.keySet();
    }
     
    public boolean isEmpty(){
        return map.isEmpty();
    }
     
    public void putAll(Map<? extends String, ? extends Object> m){
        map.putAll(m);
    }
     
    public Map<String,Object> getMap(){
        return map;
    }
}


