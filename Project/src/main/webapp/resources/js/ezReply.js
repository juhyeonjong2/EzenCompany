/**
 * 
 */

const DEFAULT_HEIGHT = 30; // textarea 기본 height
function ezReplyOnInput(event){
	let target = event.target;

  target.style.height = 0;
  target.style.height = DEFAULT_HEIGHT + target.scrollHeight + 'px';
}

function ezReplyRootSubmit(o){
 console.log("ezReply_RootSubmit");
 console.log(o);
 
 let rootTextArea = $("#ezReply_root_value");
 console.log(rootTextArea.val());
 
 let html = ezReplayMakeChild(null);
 console.log(html);
 
 $("#reply_root").append(html);
 
 
 rootTextArea.val(''); // 초기화
 
 
}

function ezReplyChildSubmit(o){
 console.log("ezReply_ChildSubmit");
 console.log(o);
}

function ezReplayMakeChild(data){

// data.content : 댓글
// data.author : 작성자
// data.date : 날짜


	let html = '<li class="ezReply_child">'
	         + '	<div class="ezReply_node pb-1 d-flex">'
	    	 + '		<div class="col-1 pt-2">'
			 + '			<div class="ezReply_author">'
			 + '				<p class="text-center m-0 t-0">고길동 :</p>'
			 + '			</div>'
			 + '			<div class="ezReply_author_me">'
			 					// <!--<p class="text-center m-0 t-0">(블로그 주인)</p>-->
			 + '			</div>'
			 + '		</div>'
			 + '		<div class="col pt-2">'
			 + '			<div class="ezReply_content">'
			 + '				데이터'
			 + '			</div>'
			 + '			<div>'
			 + '				<span class="fs-6 text-body-tertiary">2024.03.19</span>'
			 + '			</div>'
			 + '			<div class="d-flex">'
			 + '				<div class="col">'
			 + '					<button class="btn btn-secondary btn-sm ms-1">답글</button>'
			 + '				</div>'
			 + '				<div class="col d-flex justify-content-end">'
									//<!--<button class="btn btn-warning btn-sm me-2">수정</button>
									//<button class="btn btn-danger btn-sm me-2">삭제</button>-->
			 + '				</div>'
			 + '			</div>'
			 + '		</div>'
	    	 + '	</div>'
	         + '	<ul class="ezReply_cont">'
	         		// 여기 내용.
	         + '	</ul>'
	         + '</li>';
	         
	return html;
}




 function ezReply_Init(top, body, bottom){
 	console.log("ezReply_Init");
 }
 
 
 function ezReply_redraw(idArray , list) {
 
 	//let counter = idArray.counter;
 	//let body = idArray.body;
 	
 
 }
 
 
 function ezReply_add(parent, data){
 	
 }
 
 
 
