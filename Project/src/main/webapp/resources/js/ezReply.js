/**
 * 
 */

const DEFAULT_HEIGHT = 30; // textarea 기본 height
function ezReply_onInput(event){
	let target = event.target;

  target.style.height = 0;
  target.style.height = DEFAULT_HEIGHT + target.scrollHeight + 'px';
}

// default : Init 함수로 외부에서 지정 가능.
let ezReplyConfig = {
	create : "/ezencompany/blog/reply/write",
	read :   "/ezencompany/blog/reply/list",
	update : "/ezencompany/blog/reply/modify",
	delete : "/ezencompany/blog/reply/delete",
	
	parser : function (vo) {
		// 데이터 만들기
		let data = {
			rno : vo.bgrno,
			prno : vo.bgrpno,
			author: vo.author,
			content :vo.bgrcontent,
			date : vo.bgrdate,
			isEditable : vo.editable,
			isMaster : vo.master,
			isDeleted : vo.delyn =='n' ? false : true,
		};
		
		return data;
	} 
}

function ezReply_init(config, isRefresh){
	ezReplyConfig = config;
	
	if(isRefresh){
		ezReply_request_list();
	}
}


function ezReply_request(data, callback){
	
	//  개별로 뺀다. (url이 바뀌어야 한다)
	$.ajax(
	{
		url: ezReplyConfig.create,
		type: "post",
		data : {bno:data.bno, prno:data.prno, content:data.content},
		success: function(resData) {
			if(resData.result == "SUCCESS")
			{
				if(ezPley_isDirty(resData.total, 1)){
				 	ezReply_request_list(); // 다시그려야하는경우 목록 요청.
				} else {
					// 전체 댓글 수 갱신
					ezReply_setCommentCount(resData.total);
					
					let data = ezReplyConfig.parser(resData.data);
					ezReply_drawReply(data);	
				}
			}
			
			callback();
		},
		error: function(error) {
      		alert(error);
      		callback();
    	}
	});
}


// 루트 댓글 달기
function ezReply_rootSubmit(o){

	let rootTextArea = $("#ezReply_root_value");
	
	// 요청 데이터 만들기
    let data = {
    	content : rootTextArea.val(),
    	bno : $("#inputBno").val(),
    	prno: 0
    };
    
    ezReply_request(data, ()=>{ rootTextArea.val(''); });
}

function ezReply_childSubmit(o){

	let parent = $(o).closest(".ezReply_node_reply");	
	let textareaObj = parent.find("textarea");
	let child = $(o).closest(".ezReply_child");
	let prnoObj = child.children("input[name='rno']");
	
	 		 
	// 요청 데이터 만들기
     let data = {
    	content : textareaObj.val(),
    	bno : $("#inputBno").val(),
    	prno: prnoObj.val()
    };
    
	ezReply_request(data, ()=>{ parent.remove(); });
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
			 + '    <input type="hidden" name="rno" value="' + data.rno +'">'
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
			 + '	<div class="ezReply_input ms-3 pb-2">'
			 + '		<div class="d-flex">'
			 + '			<textarea placeholder="댓글을 입력하세요.." onInput="ezReply_onInput(event)"></textarea>'
			 + ' 			<button class="ms-1 me-1 btn btn-secondary btn-small" onClick="ezReply_childSubmit(this)">댓글 등록</button>'
			 + '		</div>'
			 + '	</div>'
			 + '</div>';
			 
			 
	return html;
}

function ezReply_modifyChildReply(data){

	let html = '<div class="ezReply_node_reply">' 
			 + '	<div class="ezReply_input ms-3 pb-2">'
			 + '		<div class="d-flex">'
			 + '			<textarea placeholder="댓글을 입력하세요.." onInput="ezReply_onInput(event)"></textarea>'
			 + ' 			<button class="ms-1 me-1 btn btn-secondary btn-small" onClick="ezReply_childSubmit(this)">댓글 등록</button>'
			 + '		</div>'
			 + '	</div>'
			 + '</div>';
			 
			 
	return html;
}

function ezPley_isDirty(serverReplyCnt, addCnt){
  let curCnt = parseInt(ezReply_getCommentCount());
  if(curCnt + addCnt != serverReplyCnt){
  	return true;
   }
   return false;
}

function ezReply_setCommentCount(cnt){
	$("#ezReply_comment_count").text(cnt);
}

function ezReply_getCommentCount(){
	return $("#ezReply_comment_count").text();
}

function ezReply_drawReply(data){
	if(data != null){
		let html = ezReply_makeChild(data);
		
		if(data.prno == 0){
			$("#reply_root").append(html);
		} else {
			let childCont = ezReply_findContainer(data.prno);
			if(childCont != null) {
				childCont.append(html);			
			}
		}
	}
}

function ezReply_redraw(list){

	// 댓글 수 갱신
	ezReply_setCommentCount(list.length);
	
	
	// 루트를 찾아서 모든 댓글 제거.
	$("#reply_root").empty();
	
	// 댓글 다시 그리기.
	for(let i=0;i<list.length;i++)
	{
		let data = ezReplyConfig.parser(list[i]);		
		ezReply_drawReply(data);	
	}
}

function ezReply_findContainer(prno){

	let nodes = $("#reply_root").find("input[name='rno']");
	let findObj = null;
	if(nodes != null) {
		for(let i=0; i< nodes.length; i++){
			let node = nodes[i];
			if(parseInt(node.value) == prno){
				findObj = $(node).closest(".ezReply_child").children("ul.ezReply_cont");
			}
		}	
	}
	
	return findObj;
}



function ezReply_request_list(){

 	let bno = $("#inputBno").val();

	$.ajax(
	{
		url: ezReplyConfig.read,
		type: "get",
		data : {bno:bno},
		success: function(resData) {
			ezReply_setCommentCount(resData.total);
			ezReply_redraw(resData.list);
		},
		error: function(error) {
      		alert(error);
    	}
	});
    
}



// 이건 init으로 대체하고 blog.js로 이동할 것.
$(document).ready(function(){
    ezReply_request_list();
});

