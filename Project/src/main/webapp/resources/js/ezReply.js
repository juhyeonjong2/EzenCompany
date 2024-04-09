/**
 * 
 */

const DEFAULT_HEIGHT = 30; // textarea 기본 height
function ezReply_onInput(event){
	let target = event.target;

  target.style.height = 0;
  target.style.height = DEFAULT_HEIGHT + target.scrollHeight + 'px';
}

function ezReply_rootSubmit(o){

	let rootTextArea = $("#ezReply_root_value");
	
	
	// 요청 데이터 만들기
	let content = rootTextArea.val();
    let bno = $("#inputBno").val();
    let prno = 0;
    
    $.ajax(
	{
		url: "/ezencompany/blog/reply/write",
		type: "post",
		data : {bno:bno, prno:prno, content:content},
		success: function(resData) {
			console.log(resData);
		},
		error: function(error) {
      		alert(error);
    	}
	});
    
	
	
	
	let data = {
		rno : 1,
		prno : 0,
		author: "김길동",
		content : content,
		date : "2024.04.09",
		isEditable : false,
		isMaster : true,
	}; 
	

	
	console.log(data);
	
	let html = ezReply_makeChild(data);
	$("#reply_root").append(html);
	
	
	 
	rootTextArea.val(''); // 초기화 
}

function ezReply_childSubmit(o){

	let parent = $(o).closest(".ezReply_node_reply");
	let childCont = parent.siblings("ul.ezReply_cont");
	let textareaObj = parent.find("textarea");
	let child = $(o).closest(".ezReply_child");
	let prnoObj = child.children("input#rno");
	
	 		 
	// 요청 데이터 만들기
	let content = textareaObj.val();
    let bno = $("#inputBno").val();
    let prno = prnoObj.val();
    
	console.log("print");
	console.log(bno);
	console.log(prno);
	console.log(content);
	
	
	$.ajax(
	{
		url: "/ezencompany/blog/reply/write",
		type: "post",
		data : {bno:bno, prno:prno, content:content},
		success: function(resData) {
			console.log(resData);
		},
		error: function(error) {
      		alert(error);
    	}
	});
    
    
    
			 
	// 아작스 통신으로 보낸후.
	let data = {
		rno : 2,
		prno : prno,
		author: "고길동",
		content : content,
		date : "2024.04.09",
		isEditable : true,
		isMaster : false,
	}; 
	 
	console.log(data);
	 
	// 데이터가 오면 추가.
	let html = ezReply_makeChild(data);
	childCont.append(html);
	
	
	
	// 등록후 자신 삭제.
	parent.remove();
}

function ezReply_activeSubReply(o){

	let parent = $(o).closest(".ezReply_node");
	let html = ezReply_makeChildReply(null);
	parent.after(html);
	
}

function ezReply_makeChild(data){

// data.content : 댓글
// data.author : 작성자 명
// data.date : 날짜
// data.isEditable : 수정 삭제 가능
// data.isMaster : 블로그 주인.(작성자 표시) 
// data.rno : 댓글 번호
// data.prno : 부모 댓글 번호. (redraw 할때 필요함) 0인경우 최상위
	


	let html = '<li class="ezReply_child">'
			 + '    <input type="hidden" id="rno" name="rno" value="' + data.rno +'">'
	         + '	<div class="ezReply_node pb-1 d-flex">'
	    	 + '		<div class="col-1 pt-2">'
			 + '			<div class="ezReply_author">'
			 + '				<p class="text-center m-0 t-0">' + data.author + ' :</p>'
			 + '			</div>'
			 + '			<div class="ezReply_author_me">';
		if(data.isMaster){
		html+= '				<p class="text-center m-0 t-0">(블로그 주인)</p>';
		}
		html+= '			</div>'
			 + '		</div>'
			 + '		<div class="col pt-2">'
			 + '			<div class="ezReply_content">'+ data.content + '</div>'
			 + '			<div><span class="fs-6 text-body-tertiary">' + data.date + '</span></div>'
			 + '			<div class="d-flex">'
			 + '				<div class="col">'
			 + '					<button class="btn btn-secondary btn-sm ms-1" onClick="ezReply_activeSubReply(this)">답글</button>'
			 + '				</div>'
			 + '				<div class="col d-flex justify-content-end">';
			 if(data.isEditable){
		html+= '						<button class="btn btn-warning btn-sm me-2">수정</button>'
			 + '						<button class="btn btn-danger btn-sm me-2">삭제</button>';
			}
		html+= '				</div>'
			 + '			</div>'
			 + '		</div>'
	    	 + '	</div>'
	         + '	<ul class="ezReply_cont"></ul>'
	         + '</li>';
	         
	return html;
}

function ezReply_makeChildReply(data){

	let html = '<div class="ezReply_node_reply">' 
			 + '	<div class="reply_input ms-3 pb-2">'
			 + '		<div class="d-flex">'
			 + '			<textarea placeholder="댓글을 입력하세요.." onInput="ezReply_onInput(event)"></textarea>'
			 + ' 			<button class="ms-1 me-1 btn btn-secondary btn-small" onClick="ezReply_childSubmit(this)">댓글 등록</button>'
			 + '		</div>'
			 + '	</div>'
			 + '</div>';
			 
			 
	return html;
}


function ezReply_setCommentCount(cnt){

}

function ezReply_getCommentCount(){

}

function ezReply_redraw(data){
	// ws comment - 여기 작업중.
}

function ezReply_request_list(){

 	let bno = $("#inputBno").val();

	$.ajax(
	{
		url: "/ezencompany/blog/reply/list",
		type: "get",
		data : {bno:bno},
		success: function(resData) {
			console.log("11111");
			console.log(resData);
		},
		error: function(error) {
      		alert(error);
    	}
	});
    
}


$(document).ready(function(){
    ezReply_request_list();
});


/*
let reply_data ={
	total : 5,
	
	list : [
	{ 
		rno : 1,
		prno : 0,
		content: "content",
		author : "kkk",
		date : "2024.04.04",
		isEditable : false,
		isMaster : true
	},
	{
		rno : 2
		prno : 1,
		content: "content",
		author : "kkk",
		date : "2024.04.04",
		isEditable : false,
		isMaster : true
	}]
};
 
 
*/