/**
* Template Name: NiceAdmin
* Template URL: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/
* Updated: Mar 17 2024 with Bootstrap v5.3.3
* Author: BootstrapMade.com
* License: https://bootstrapmade.com/license/
*/

(
    function() 
    {
    "use strict";

   /**
   * Easy selector helper function
   */
    const select = (el, all = false) => {
      el = el.trim()
      if (all) {
        return [...document.querySelectorAll(el)]
      } else {
        return document.querySelector(el)
      }
    }

    /**
     * Initiate Datatables
     */
    const datatables = select('.datatable', true);
    datatables.forEach(datatable => {
    new simpleDatatables.DataTable(datatable, {
        perPageSelect: [5, 10, 15, ["All", -1]]
        /*
        labels:{
        info: "총 {rows}개"
        },*/

        /*
        columns: [{
            select: 2,
            sortSequence: ["desc", "asc"]
        },
        {
            select: 3,
            sortSequence: ["desc"]
        },
        {
            select: 4,
            cellClass: "green",
            headerClass: "red"
        }
        ]*/
    });
    })


    /**
     * Initiate Modals
     */
     // 게시판 추가 모달
     const boardAddModal = document.getElementById('boardAddModal')
     if (boardAddModal) {
      boardAddModal.addEventListener('show.bs.modal', event => 
         {
            $.fn.zTree.init($("#tree_reader"), setting, zNodes);
            $.fn.zTree.init($("#tree_writer"), setting, zNodes);
            $('#board_inputName').val('');
         });   
     }

     // 게시판 정보 모달
     const boardEditModal = document.getElementById('boardEditModal')
     if (boardEditModal) {
      boardEditModal.addEventListener('show.bs.modal', event => 
         {
            $.fn.zTree.init($("#tree_edit_writer"), setting, zEditNodes);
            $.fn.zTree.init($("#tree_edit_reader"), setting, zEditNodes);
            $('#board_inputEditName').val('샘플 게시판');
         });   
     }

     // 분류 수정 팝업 (treename 넘겨받기)
     const boardAddCategoryModal = document.getElementById('boardAddCategoryModal')
     if (boardAddCategoryModal) {
      boardAddCategoryModal.addEventListener('show.bs.modal', event => 
         {
          console.log(event);   
          const target = event.relatedTarget;             
          console.log(target);
          // 옵션으로 넘기면 이렇게 가져올수 있다.
          console.log(target._config['data-bs-tree']);
          let hiddenInput = $(boardAddCategoryModal).find('.modal-body .tree_name_input');
          hiddenInput.val(target._config['data-bs-tree']);

          // 직위, 직책, 직무, 부서
          // 디비에서 불러온 데이터. (ajax로 불러올것)
          let dbCategorys = fetchCategorys();
             
          // 카테고리 목록 만들기
          let body = $(boardAddCategoryModal).find('.modal-body .container-fluid');
          let aleadyCategory = body.find('.category');
          if(aleadyCategory != null){
            aleadyCategory.remove();
          } 

          let html = '<div class="row mb-1 category">'
                    + '<div>'
                    + '  <label class="col-sm-4 col-form-label">분류</label>'
                    + '  <div class="col">'
                    + '    <select class="form-select" onchange="changePopupCategory(boardAddCategoryModal, this.value)" aria-label="Default select example">';
              html += '      <option selected value="ALL">모두</option>';
              // 목록 시작 : 디비에서 정보 가져오기
                for(let i=0;i<dbCategorys.length;i++){
                    html += '      <option value="' + dbCategorys[i].code +'">' + dbCategorys[i].value +'</option>';
                }
              // 목록 종료

              html += '    </select>'
                    + '  </div>'
                    + '</div>'
                    + '</div>';
          body.append(html);

          changePopupCategory(boardAddCategoryModal, 'ALL');
  
         });   
     }

     const boardAddAttributeModal = document.getElementById('boardAddAttributeModal')
     if (boardAddAttributeModal) {
      boardAddAttributeModal.addEventListener('show.bs.modal', event => 
         {
          //console.log(event);   
          const target = event.relatedTarget;             
          //console.log(target);
          // 옵션으로 넘기면 이렇게 가져올수 있다.
          //console.log(target._config['data-bs-tree']);
          let treeName = target._config['data-bs-tree'];
          let parentTreeId = target._config['data-bs-treeid'];
          let categoryCode = target._config['data-bs-category'];

          // hidden input에 파라메터 설정
          $(boardAddAttributeModal).find('.modal-body .tree_name_input').val(treeName);
          $(boardAddAttributeModal).find('.modal-body .parent_tree_id_input').val(parentTreeId);
          $(boardAddAttributeModal).find('.modal-body .category_code_input').val(categoryCode);

          // categoryCode에 따라 속성 데이터 가져오기.
          let dbAttributes =fetchAttributes(categoryCode);
          let body = $(boardAddAttributeModal).find('.modal-body .container-fluid');
          let aleadyAttr = body.find('.attribute');
          if(aleadyAttr != null){
            aleadyAttr.remove();
          }

          let html = '<div class="row mb-1 attribute">'
                    + '<div>'
                    + '  <label class="col-sm-4 col-form-label"></label>'
                    + '  <div class="col">'
                    + '    <select class="form-select" aria-label="Default select example">';
              
              // 목록 시작 : 디비에서 정보 가져오기
                for(let i=0;i<dbAttributes.length;i++){
                  if(i==0){
                    html += '      <option selected value="' + dbAttributes[i].key +'">' + dbAttributes[i].value +'</option>';
                  } else {
                    html += '      <option value="' + dbAttributes[i].key +'">' + dbAttributes[i].value +'</option>';
                  }
                }
              // 목록 종료

              html += '    </select>'
                    + '  </div>'
                    + '</div>'
                    + '</div>';
          body.append(html);
         });   
     }

  })();


/********************************
 *  zTree 설정
 *******************************/
// zTree configuration information, refer to API documentation (setting details)
var setting = {
  view: {
      addHoverDom: addHoverDom,
      removeHoverDom: removeHoverDom,
      selectedMulti: false,
      showLine:false
  },
  check: {
      enable: false,
      chkStyle: 'radio',
      radioType: "level"
  },
  data: {
      simpleData: {
          enable: true
      }
  },
  edit: {
      enable: true,
      showRemoveBtn: true,
      showRenameBtn:false,

  }
};
const categoryOpenIconPath = "/ezencompany/resources/icon/layers.svg";
const categoryCloseIconPath ="/ezencompany/resources/icon/layers-fill.svg";
const attributeIconPath ="/ezencompany/resources/assets/icon/puzzle.svg";

// zTree data attributes, refer to the API documentation (treeNode data details)

var zEditNodes =[

  { id:1, pId:0, name:'직위', ename:'POSITION', isParent:true, open:true, iconOpen:categoryOpenIconPath, iconClose:categoryCloseIconPath},
  { id:11, pId:1, name:'사원', ename:'Associate', icon:attributeIconPath},
  { id:12, pId:1, name:'주임', ename:'Associate Manager', icon:attributeIconPath},
  { id:13, pId:1, name:'대리', ename:'General Manager', icon:attributeIconPath},
  { id:14, pId:1, name:'과장', ename:'Deputy General Manager', icon:attributeIconPath},

  { id:2, pId:0, name:'직무', ename:'DUTY', isParent:true, open:true, iconOpen:categoryOpenIconPath, iconClose:categoryCloseIconPath},
  { id:21, pId:2, name:'개발', ename:'Development', icon:attributeIconPath},
  { id:22, pId:2, name:'경영지원', ename:'anagement Support', icon:attributeIconPath},
  { id:23, pId:2, name:'사업', ename:'Business', icon:attributeIconPath},

  { id:3, pId:0, name:'부서', ename:'DEPARTMENT', isParent:true, open:true, iconOpen:categoryOpenIconPath, iconClose:categoryCloseIconPath},
  { id:31, pId:3, name:'경영지원', ename:'Management Support', icon:attributeIconPath},
  { id:32, pId:3, name:'개발1팀', ename:'DevelopmentA', icon:attributeIconPath},

  { id:4, pId:0, name:'직책', ename:'RESPONSIBILITY', isParent:true, open:true, iconOpen:categoryOpenIconPath, iconClose:categoryCloseIconPath},
  { id:41, pId:4, name:'파트장', ename:'Part Leader', icon:attributeIconPath},
  { id:42, pId:4, name:'임원', ename:'Executive', icon:attributeIconPath},
  { id:43, pId:4, name:'최고기술책임자', ename:'CTO', icon:attributeIconPath},
  { id:44, pId:4, name:'대표이사', ename:'CEO', icon:attributeIconPath},


  /*
  { id:1, pId:0, name:"카테고리1", isParent:true, open:true, iconOpen:categoryOpenIconPath, iconClose:categoryCloseIconPath},
  { id:11, pId:1, name:"속성1" , icon:attributeIconPath},
  { id:12, pId:1, name:"속성2" , icon:attributeIconPath},
  { id:13, pId:1, name:"속성3" , icon:attributeIconPath},
  { id:14, pId:1, name:"속성4" , icon:attributeIconPath},
  { id:2, pId:0, name:"카테고리2", isParent:true, open:true, iconOpen:categoryOpenIconPath, iconClose:categoryCloseIconPath},
  { id:21, pId:2, name:"속성1" , icon:attributeIconPath},
  { id:22, pId:2, name:"속성1" , icon:attributeIconPath},
  { id:23, pId:2, name:"속성1" , icon:attributeIconPath},
  { id:24, pId:2, name:"속성1" , icon:attributeIconPath},
  { id:3, pId:0, name:"카테고리3", isParent:true, open:true, iconOpen:categoryOpenIconPath, iconClose:categoryCloseIconPath},
  { id:4, pId:0, name:"카테고리4", isParent:true, open:true, iconOpen:categoryOpenIconPath, iconClose:categoryCloseIconPath},
  */
];

var zNodes =[];

function addHoverDom(treeId, treeNode) {
  const sObj = $("#" + treeNode.tId + "_span");
  if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;

  // 부모 객체인경우 + 버튼 추가.
  if(treeNode.isParent == true){
    const addStr = "<span class='button add' id='addBtn_" + treeNode.tId
              + "' title='add node' onfocus='this.blur();'></span>";

    sObj.after(addStr);

    var btn = $("#addBtn_"+treeNode.tId);
    if (btn) btn.bind("click", function()
    {
        let ztreeName = btn.closest("ul.ztree").attr("id");
        openMultiModal('boardAddAttributeModal', { 'data-bs-tree' : ztreeName, 'data-bs-treeid' : treeNode.id, 'data-bs-category' : treeNode.ename });
        return false;
    });

  }
};
function removeHoverDom(treeId, treeNode) {
  $("#addBtn_"+treeNode.tId).unbind().remove();
};

let categoryIdCount = 1;
function addCategory(treeName, categoryName, categoryCode){
  let categoryid = 0;
  const zTree = $.fn.zTree.getZTreeObj(treeName);
  if(zTree != null){
    categoryid = 100 + categoryIdCount++;
      zTree.addNodes(null, 
        { id:categoryid, pId:0, name:categoryName, ename:categoryCode, isParent:true, open:true, iconOpen:categoryOpenIconPath, iconClose:categoryCloseIconPath}
      );
  }

  return categoryid;
}

let attributeIdCount = 1;
function addAttribute(treeName, parentTreeId, attributeName, attributeKey){


  let attributeId = 0;
  const zTree = $.fn.zTree.getZTreeObj(treeName);
  if(zTree != null){
    let parentNode = null;
    
    let nodes = zTree.getNodes();
    for(let i=0;i<nodes.length;i++){
      if(nodes[i].id == parentTreeId){
        parentNode = nodes[i];
        break;
      }
    }

    if(parentNode == null){
      console.log("addAttribute fail. parentNode is null");
      return;
    }
    attributeId = 1000+ attributeIdCount++;
    zTree.addNodes( parentNode, { id:attributeId, pId:parentTreeId, name:attributeName, ename:attributeKey, icon:attributeIconPath});
    
  }

  return attributeId;
}
function resetTreeId(){
  categoryIdCount = 1;
  attributeIdCount = 1;
}
$(document).ready(function(){
  resetTreeId();
  $.fn.zTree.init($("#tree_reader"), setting, zNodes);
  $.fn.zTree.init($("#tree_writer"), setting, zNodes);

  $.fn.zTree.init($("#tree_edit_reader"), setting, zNodes);
  $.fn.zTree.init($("#tree_edit_writer"), setting, zNodes);
});



/**********************************
 *  모달 제어
 ************************************/
function fetchCategorys(){

	let dbCategorys =[];
	
	$.ajax(
	{
		url: "/ezencompany/board/category/list",
		type: "get",
		async : false,
		success: function(categorys) {
			
			for(let i=0;i<categorys.length; i++)
			{
				let category = categorys[i];
				dbCategorys.push(
				{
					'code' : category.code,
					'value' :category.value,
					'cidx' : category.cidx
				});
			}
		},
		error: function(error) {
      		alert(error);
    	}
	});
	

    return dbCategorys;
}

function fetchAttributes(categoryCode){

  let dbAttributes =[];
  
  $.ajax(
	{
		url: "/ezencompany/board/attribute/list",
		type: "get",
		data: {code:categoryCode},
		async : false,
		success: function(attributes) {
			if(attributes != null){
			for(let i=0;i<attributes.length; i++)
			{
				let attribute = attributes[i];
				dbAttributes.push(
				{
					'key' : attribute.otkey,
					'value' :attribute.value,
					'cidx' : attribute.cidx,
					'aidx' : attribute.aidx
				});
			}
			}
		},
		error: function(error) {
      		alert(error);
    	}
	});
  
  return dbAttributes;
}

function parsePermission(category){
  // 읽기 권한에 아무것도 없으면 '없음' 처리(관리자만)
  let result = {
    categoryKor : "없음",
    attributesKor : "관리자"
  };

  // 없음 처리
  if(category == null)
    return result;

  // 카테고리만 있고 속성이 빈경우에는 오류다. (모든 사원은 분류에 해당하는 데이터를 가지고있음.)
  let _attributesKor ="Unknown";
  if(category.categoryCode == 'ALL'){
    _attributesKor ="모두";
  }
 
  if(category.attributes != null && category.attributes.length >0 ){
    _attributesKor ="";
    for(let i=0;i<category.attributes.length;i++){
      let attribute = category.attributes[i];
      if(i==0){
        _attributesKor += attribute.attributeValue;
      }else {
        _attributesKor += ", " + attribute.attributeValue;
      }
    }
  }

  result.categoryKor = category.categoryValue;
  result.attributesKor = _attributesKor;
  return result;
}

let boardNo = 4; // 임시데이터 (디비에 있어야하는 값임)
function addBoard(name, read_permission, write_permission){

  if(name == null || name == '')
    return '';

  if(read_permission == null )
    return '';

  if(write_permission == null )
    return '';
    
    
    
    
    

  let html= '<tr>'
          + '  <td>'+ name +'</td>'
          + '  <td>';

  if(read_permission.length == 0){
    let result = parsePermission(null);
    html += '      <button type="button" class="btn btn-secondary rounded-pill ms-1" data-bs-toggle="tooltip" data-bs-placement="top" title="'
        + result.attributesKor +'">'+  result.categoryKor 
        +'</button>';
  }else {
    // 카테고리반복 
    for(let i=0;i<read_permission.length;i++){
      let result = parsePermission(read_permission[i]);
      html += '      <button type="button" class="btn btn-secondary rounded-pill ms-1" data-bs-toggle="tooltip" data-bs-placement="top" title="'
          + result.attributesKor +'">'+  result.categoryKor 
          +'</button>';
    }
  }
  html += '  </td>'
        + '  <td>';
  if(write_permission.length == 0){
    let result = parsePermission(null);
      html += '      <button type="button" class="btn btn-secondary rounded-pill ms-1" data-bs-toggle="tooltip" data-bs-placement="top" title="'
          + result.attributesKor +'">'+  result.categoryKor 
          +'</button>';
  }else {
    for(let i=0;i<write_permission.length;i++){
      let result = parsePermission(write_permission[i]);
      html += '      <button type="button" class="btn btn-secondary rounded-pill ms-1" data-bs-toggle="tooltip" data-bs-placement="top" title="'
          + result.attributesKor +'">'+  result.categoryKor 
          +'</button>';
    }
  }
  html += '  </td>'
      + '  <td>'
      + '   <a class="link-dark" href="#" onclick="return false;" data-bs-toggle="modal" data-bs-target="#boardEditModal" data-bs-no="'+ boardNo +'">'
      + '     <i class="bi bi-three-dots-vertical"></i>'
      + '   </a>'
      + '  </td>'
      + '</tr>';

    
  boardNo++;
  return html;
}


function changePopupCategory(modal, val){
  if(modal == null)
    return;
	console.log("changePopupCategory");

  // val 정보를 가지고 디비에서 속성값을 가져온다.
  let dbAttributes =fetchAttributes(val);
  let body = $(modal).find('.modal-body .container-fluid');
  let aleadyAttr = body.find('.attribute');
  if(aleadyAttr != null){
    aleadyAttr.remove();
  }


  let html = '<div class="row mb-1 attribute">'
            + '<div>'
            + '  <label class="col-sm-4 col-form-label"></label>'
            + '  <div class="col">'
            + '    <select class="form-select" aria-label="Default select example">';
      
      // 목록 시작 : 디비에서 정보 가져오기
            html += '      <option selected value="NONE">선택 안함</option>'; // 선택 안함은 무조건있음.
        for(let i=0;i<dbAttributes.length;i++){
            html += '      <option value="' + dbAttributes[i].key +'">' + dbAttributes[i].value +'</option>';
        }
      // 목록 종료

      html += '    </select>'
            + '  </div>'
            + '</div>'
            + '</div>';
  body.append(html);
  
}

function insertCategory(o) {
  
  let root = $(o).closest(".modal-content");
  let treeName = root.find(".tree_name_input").val();
  let body = root.find(".modal-body .container-fluid");

  let categoryCode = body.find('.category select option:selected').val();
  let categoryValue= body.find('.category select option:selected').text();
  let categoryId = addCategory(treeName, categoryValue, categoryCode);

  // 어트리뷰트가 선택아님이 아니라면 추가한다.
  let attributeKey = body.find('.attribute select option:selected').val();
  let attributeValue= body.find('.attribute select option:selected').text();

  if(attributeKey!='NONE'){
    addAttribute(treeName, categoryId, attributeValue, attributeKey);
  }

}

function insertAttribute(o){
  let root = $(o).closest(".modal-content");
  let treeName = root.find(".tree_name_input").val();
  let parentTreeId = root.find(".parent_tree_id_input").val();
  //let categoryCode = root.find(".category_code_input").val();
  let body = root.find(".modal-body .container-fluid");
  let attributeKey = body.find('.attribute select option:selected').val();
  let attributeValue= body.find('.attribute select option:selected').text();

  addAttribute(treeName, parentTreeId, attributeValue, attributeKey);
}

function insertBoard(){

  let boardName = $("#board_inputName").val();
  let reader = getTreeData('tree_reader');
  let writer = getTreeData('tree_writer');
  
  console.log(boardName);
  console.log(reader);
  console.log(writer);
  
  for(let i =0 ;i <reader.length;i++)
  {
  	let permission = reader[i];
  	consloe.log(permission.categoryCode);
  	consloe.log(permission.categoryValue);
  	
  	for(let j=0;j<permission.attributes.length;j++){
  		let attribute = permission.attributes[j];
  		
  	
  	}
 

  	
  }
  
  
  $.ajax(
	{
		url: "/ezencompany/admin/board/write",
		type: "post",
		data: {name:boardName, reader:reader, writer:writer},
		success: function(res) {
			console.log("111");
		},
		error: function(error) {
      		alert(error);
    	}
	});
  
  
  /*
  let html = addBoard(boardName,reader,writer);
  
  if(html == ''){
    alert("게시판 추가 실패");
    return;
  }

  let table = document.querySelector('.datatable ');
  let root =$(table).find('tbody');
  root.append(html);

  //let datatable = new simpleDatatables.DataTable(table);
  //console.log(datatable);
 // datatable.refresh();

 */
  initTooltips();
}

function getTreeData(treeName){
  const zTree = $.fn.zTree.getZTreeObj(treeName);
  let nodes = zTree.getNodes();

  let categorys=[];
  for(let i=0;i<nodes.length;i++){
    let node = nodes[i];
  
    let category = 
    {
      categoryCode : node.ename,
      categoryValue : node.name
    }
    let attributes = [];
    if(node.children != null){
      for(let j=0;j<node.children.length;j++){
        let children = node.children[j];
        let attribute ={
          attributeKey : children.ename,
          attributeValue : children.name
        };
        attributes.push(attribute);
      }
    }
    category['attributes'] = attributes;
    
    categorys.push(category);
  }
  
  return categorys;
}


function editBoard(){
  // 더미
}