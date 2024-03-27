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
     // 분류 수정 팝업 (treename 넘겨받기)
     const boardAddCategoryModal = document.getElementById('boardAddCategoryModal')
     if (boardAddCategoryModal) {
      boardAddCategoryModal.addEventListener('show.bs.modal', event => 
         {
          //console.log(event);   
          const target = event.relatedTarget;             
          //console.log(target);
          // 옵션으로 넘기면 이렇게 가져올수 있다.
          //console.log(target._config['data-bs-tree']);
          let hiddenInput = $(boardAddCategoryModal).find('.modal-body .tree_name_input');
          hiddenInput.val(target._config['data-bs-tree']);

          // 직위, 직책, 직무, 부서
          // 디비에서 불러온 데이터. (ajax로 불러올것)
          let dbCategorys = FetchCategorys();
             
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

              // 목록 시작 : 디비에서 정보 가져오기
                for(let i=0;i<dbCategorys.length;i++){
                  if(i==0){
                    html += '      <option selected value="' + dbCategorys[i].code +'">' + dbCategorys[i].value +'</option>';
                  }else {
                    html += '      <option value="' + dbCategorys[i].code +'">' + dbCategorys[i].value +'</option>';
                  }
                }
              // 목록 종료

              html += '    </select>'
                    + '  </div>'
                    + '</div>'
                    + '</div>';
          body.append(html);

          changePopupCategory(boardAddCategoryModal, 'POSITION');
  
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
          let dbAttributes =FetchAttributes(categoryCode);
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
const categoryOpenIconPath = "../assets/icon/layers.svg";
const categoryCloseIconPath ="../assets/icon/layers-fill.svg";
const attributeIconPath ="../assets/icon/puzzle.svg";

// zTree data attributes, refer to the API documentation (treeNode data details)
/*
var zNodes =[

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
  

];
*/
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

  //$.fn.zTree.init($("#tree_edit_reader"), setting, zNodes);
  //$.fn.zTree.init($("#tree_edit_writer"), setting, zNodes);
  
});



/**********************************
 *  모달 제어
 ************************************/
function FetchCategorys(){
  let dbCategorys = [
    {'code' : 'POSITION' ,'value' : '직위'},
    {'code' : 'RESPONSIBILITY' ,'value' : '직책'},
    {'code' : 'DUTY' ,'value' : '직무'},
    {'code' : 'OCCUPATION' ,'value' : '직종'},
    {'code' : 'DEPARTMENT' ,'value' : '부서'}];

    return dbCategorys;
}

function FetchAttributes(categoryCode){
// 디비에서 가져오는 로직 샘플 (ajax로 불러올것)
  let dbAttributes =null;

  if(categoryCode=='POSITION') 
  {
    // 직위
    dbAttributes = [
      {'key' : 'Intern'                 ,'value' : '인턴'},
      {'key' : 'Staff'                  ,'value' : '사원'},
      {'key' : 'Associate'              ,'value' : '주임'},
      {'key' : 'Associate Manager'      ,'value' : '대리'},
      {'key' : 'General Manager'        ,'value' : '과장'},
      {'key' : 'Deputy General Manager' ,'value' : '차장'},
      {'key' : 'General Manager'        ,'value' : '부장'},
      {'key' : 'Director'               ,'value' : '이사'},
      {'key' : 'Managing Director'      ,'value' : '상무'},
      {'key' : 'Deputy Vice President'  ,'value' : '전무'},
      {'key' : 'Vice President'         ,'value' : '부사장'},
      {'key' : 'President'              ,'value' : '사장'},
      {'key' : 'Vice Chairman'          ,'value' : '부회장'},
      {'key' : 'Chairman'               ,'value' : '회장'}
    ];
  }else if(categoryCode=='RESPONSIBILITY'){
    //직책
    dbAttributes = [
      {'key' : 'Staff'                      ,'value' : '사원'},
      {'key' : 'Part Leader'                ,'value' : '파트장'},
      {'key' : 'Branch Manager'             ,'value' : '지점장'},
      {'key' : 'Headquarter Manager'        ,'value' : '본부장'},
      {'key' : 'Group Leader'               ,'value' : '그룹장'},
      {'key' : 'Head of Department'         ,'value' : '부서장'},
      {'key' : 'Team Leader'                ,'value' : '팀장'},
      {'key' : 'Head of Corporate Business' ,'value' : '사업부장'},
      {'key' : 'Head of Division'           ,'value' : '부문장'},
      {'key' : 'Center Leader'              ,'value' : '센터장'},
      {'key' : 'General Manager'            ,'value' : '실장'},
      {'key' : 'Executive'                  ,'value' : '임원'},
      {'key' : 'Residing Advisor'           ,'value' : '상근고문'},
      {'key' : 'Advisor'                    ,'value' : '고문'},
      {'key' : 'CIO'                        ,'value' : '최고정보책임자'}, // [Chief Information Officer]
      {'key' : 'COO'                        ,'value' : '최고운영책임자'}, // [Chief Operating Officer]
      {'key' : 'CMO'                        ,'value' : '최고마케팅책임자'}, // [Chief Marketing Officer]
      {'key' : 'CFO'                        ,'value' : '최고재무책임자'}, // [Chief Financial Officer]
      {'key' : 'CTO'                        ,'value' : '최고기술책임자'},    // [Chief Technology Officer]
      {'key' : 'CEO'                        ,'value' : '대표이사'}       // [Chief Executive Officer]
    ];

  }else if(categoryCode=='DUTY'){
    // 직무
    dbAttributes = [
      {'key' : 'Development'        ,'value' : '개발'},
      {'key' : 'Management Support' ,'value' : '경영지원'},
      {'key' : 'Service'            ,'value' : '서비스'},
      {'key' : 'Business'           ,'value' : '사업'}
    ];
  }else if(categoryCode=='DEPARTMENT'){
    //부서
    dbAttributes = [
      {'key' : 'Management Support' ,'value' : '경영지원'},
      {'key' : 'Technical Support'  ,'value' : '기술지원'},
      {'key' : 'Technical Research Center'  ,'value' : '기술연구소'},
      {'key' : 'DevelopmentA'        ,'value' : '개발1팀'},
      {'key' : 'DevelopmentB'        ,'value' : '개발2팀'}
    ];

  }else if(categoryCode=='OCCUPATION'){
    // 직종
    dbAttributes = [
      {'key' : 'System Design'      ,'value' : '시스템 기획'},
      {'key' : 'Level Design'       ,'value' : '레벨 기획'},
      {'key' : 'Screenwriter'       ,'value' : '시나리오 작가'},
      {'key' : 'Client Programmer'  ,'value' : '클라이언트 프로그래머'},
      {'key' : 'Server Programmer'  ,'value' : '서버 프로그래머'},
      {'key' : 'Concept Art'        ,'value' : '콘셉 그래픽 디자이너'},
      {'key' : '3D Modeler'         ,'value' : '3D 모델러'},
      {'key' : 'Animator'           ,'value' : '에니메이터'},
      {'key' : 'Effecter'           ,'value' : '비주얼 이펙터'},
      {'key' : 'Sound'              ,'value' : '사운드 제작'},
      {'key' : 'Business'           ,'value' : '영업'},
      {'key' : 'Technical Support'  ,'value' : '기술지원'},
      {'key' : 'Management Support' ,'value' : '경영지원'}
    ];
  }

  return dbAttributes;
}


function changePopupCategory(modal, val){
  if(modal == null)
    return;

  // val 정보를 가지고 디비에서 속성값을 가져온다.
  let dbAttributes =FetchAttributes(val);
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

function insertBoard(o){

  let datatable = document.querySelector('.datatable ');
  
  
 // ws comment - 여기 작업중
  console.log("insertBoard");
  console.log(datatable);

  let root =$(datatable).find('.tbody');
  console.log(root);
  let html= '<tr>'
          + '  <td>공지사항</td>'
          + '  <td>'
          + '      <button type="button" class="btn btn-secondary rounded-pill ms-1" data-bs-toggle="tooltip" data-bs-placement="top" title="All">모두</button>'
          + '  </td>'
          + '  <td>'
          + '      <button type="button" class="btn btn-secondary rounded-pill ms-1" data-bs-toggle="tooltip" data-bs-placement="top" title="Admin Only">없음</button>'
          + '  </td>'
          + '  <td>'
          + '    <a class="link-dark" href="#"><i class="bi bi-three-dots-vertical"></i></a>'
          + '  </td>'
          + '</tr>';

  root.append(html);




}

function editBoard(o){

}