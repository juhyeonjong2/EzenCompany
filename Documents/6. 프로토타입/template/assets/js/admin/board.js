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
     // 분류 수정 팝업 (category 넘겨받기)
     const categoryEditModal = document.getElementById('categoryEditModal')
     if (categoryEditModal) {
        categoryEditModal.addEventListener('show.bs.modal', event => 
         {
             const button = event.relatedTarget;             
             const category = button.getAttribute('data-bs-category');
             console.log(category);
 
             // to do
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
var newCount = 1;
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
      // 여기서 속성 추가 팝업을 호출 할 것.
        //var zTree = $.fn.zTree.getZTreeObj("tree_reader");
        //zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, name:"new node" + (newCount++)});
      console.log("111",treeNode.id );
        addAttribute("tree_reader",  treeNode.id, "Attribute" + (attributeIdCount));
        return false;
    });

  }
};
function removeHoverDom(treeId, treeNode) {
  $("#addBtn_"+treeNode.tId).unbind().remove();
};


// 이거 호출 작업 해야함.
let categoryIdCount = 1;
function addCategory(treeName, categoryName){
  const zTree = $.fn.zTree.getZTreeObj(treeName);
  if(zTree != null){
      zTree.addNodes(
        null, 
        { id:(100 + (categoryIdCount++)), pId:0, name:categoryName, isParent:true, open:true, iconOpen:categoryOpenIconPath, iconClose:categoryCloseIconPath}
      );
  }
}

let attributeIdCount = 1;
function addAttribute(treeName, parentTreeId, attributeName){
  const zTree = $.fn.zTree.getZTreeObj(treeName);
  if(zTree != null){
    let parentNode = null;
    
    let nodes = zTree.getNodes();
    for(let i=0;i<nodes.length;i++){
      if(nodes[i].id == parentTreeId){
        treeNode = nodes[i];
        break;
      }
    }

    if(parentNode == null){
      console.log("addAttribute fail. parentNode is null");
      return;
    }

    zTree.addNodes( parentNode, { id:(1000 + (attributeIdCount++)), pId:parentTreeId, name:attributeName, icon:attributeIconPath});
  }
}

$(document).ready(function(){
  //zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
  $.fn.zTree.init($("#tree_reader"), setting, zNodes);

  //$.fn.zTree.init($("#tree_writer"), setting, zNodes);
});