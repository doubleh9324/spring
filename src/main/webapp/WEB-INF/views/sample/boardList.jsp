<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
</head>
<body>
    <h2>게시판 목록</h2>
    <table class="board_list">
        <colgroup>
            <col width="10%"/>
            <col width="*"/>
            <col width="15%"/>
            <col width="20%"/>
        </colgroup>
        <thead>
            <tr>
                <th scope="col">글번호</th>
                <th scope="col">제목</th>
                <th scope="col">조회수</th>
                <th scope="col">작성일</th>
            </tr>
        </thead>
        <tbody id="addlist">
             
        </tbody>
    </table>
    
    <div id="lastPostsLoader"></div>
    <div id="PAGE_NAVI"></div>
    <input type="hidden" id="PAGE_INDEX" name="PAGE_INDEX" value="1"/>
     
    <br/>
    <a href="#" class="btn" id="write">글쓰기</a>
    
     <a href="http://localhost:8080/first/sample/openBoardList.do" id="link">board :)</a>
    <%@ include file="/WEB-INF/include/include-body.jspf" %>
    <script type="text/javascript">
    
$(document).ready(function(){
	

    $(window).scroll(function(){  
        if  ($(window).scrollTop() == $(document).height() - $(window).height()){  
           lastPostFunc();
        }  
    });

    function lastPostFunc()  
    {  
        $("div#lastPostsLoader").html("로딩중...");  
        addList();
        
    };  

    function addList(){
    	var pageno = ($("#PAGE_INDEX").val());
    	pageno++;
    	_movePage(pageno);
    };

    $("#write").on("click", function(e){ //글쓰기 버튼
        e.preventDefault();
        fn_openBoardWrite();
    });
    
    
    //해쉬값이 존재하면 그만클 불러오고 포커스 주기
    if(document.location.hash){
    	
    	
    	var hash = window.location.hash.replace("#", "");
    	var target = $("#"+hash);
    	var page = Math.floor(hash/20)+1;
    	window.location.hash = page;
    	
    	$("#PAGE_INDEX").val(page);
    	
   		for(var i=1; i<=page; i++){
   			fn_selectBoardList(i);
   		}
   		
   		location.hash = "#page"+page;
    }else{
    	fn_selectBoardList(1);
    }
});
 


 
function fn_openBoardWrite(){
    var comSubmit = new ComSubmit();
    comSubmit.setUrl("<c:url value='/sample/openBoardWrite.do' />");
    comSubmit.submit();
}
/*
function fn_openBoardDetail(obj){
    var comSubmit = new ComSubmit();
    comSubmit.setUrl("<c:url value='/sample/openBoardDetail.do' />");
    window.alert(obj.parent().find("#IDX").val());
    comSubmit.addParam("IDX", obj.find("#IDX").val());
    comSubmit.submit();
}
*/
function fn_openBoardDetail(idx,num){
	
	window.location.hash="#"+num;
    var comSubmit = new ComSubmit();
    comSubmit.setUrl("<c:url value='/sample/openBoardDetail.do' />");
    comSubmit.addParam("IDX", idx);
    var pageno = ($("#PAGE_INDEX").val());
    comSubmit.addParam("INDEX", pageno)
    comSubmit.submit();
}
 
function fn_selectBoardList(pageNo){
    var comAjax = new ComAjax();
    comAjax.setUrl("<c:url value='/sample/selectBoardListAjax.do' />");
    comAjax.setCallback("fn_selectBoardListCallback");
    comAjax.addParam("PAGE_INDEX",pageNo);
    comAjax.addParam("PAGE_ROW", 20);
    comAjax.ajax();
}
 
function fn_selectBoardListCallback(data){
    var total = data.TOTAL;
    var body = $("table>tbody");
    var last = $("#addlist");
   //	body.empty();
    if(total == 0){
        var str = "<tr>" +
                        "<td colspan='4'>조회된 결과가 없습니다.</td>" +
                    "</tr>";
        body.append(str);
    }
    else{
        var params = {
            divId : "PAGE_NAVI",
            pageIndex : "PAGE_INDEX",
            totalCount : total,
            eventName : "fn_selectBoardList"
        };
        gfn_renderPaging(params);
         
        //값을 가져와서 뿌려주는 부분
        var str = "";
        var pageNo = $("#PAGE_INDEX").val();
        var i=1;
        var num = pageNo*20-20 ;
        
        $.each(data.list, function(key, value){
        	if(num < 0)
        		num = 0;
            str += "<tr id='"+ (num + i)+"'>" +
                        "<td>" + value.IDX + "</td>" +
                        "<td class='title'>" +
                            "<a href='#"+ (num + i)+"' name='title' onclick='fn_openBoardDetail("+value.IDX+","+(num+i)+")'>" + value.TITLE + "</a>" +
                            "<input type='hidden' name='title' value=" + value.IDX + ">" +
                        "</td>" +
                        "<td>" + value.HIT_CNT + "</td>" +
                        "<td>" + value.CREA_DTM + "</td>" +
                    "</tr>";
                    i++;
        });
        var anchor = "<a id='page"+pageNo+"'>------</a>";
        body.append(anchor);
        body.append(str);
    }
}
        





    </script>
</body>
</html>


