
/*--------------------------------------------------------------
# zTree - 좌측 side bar
--------------------------------------------------------------*/
//var zTreeObj;
// zTree configuration information, refer to API documentation (setting details)
var setting = {
    view: {
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
        enable: false,
    }
};

/*--------------------------------------------------------------
# Init
--------------------------------------------------------------*/

$(document).ready(function(){
    $.fn.zTree.init($("#blog_tree"), setting, getNodes());
});


function getNodes(){
// zTree data attributes, refer to the API documentation (treeNode data details)
let zNodes =[
    { id:1, pId:0, name:"스프링 일지", open:true},
        { id:11, pId:1, name:"스프링 일지 1" },
        { id:12, pId:1, name:"스프링 일지 2" },
    { id:2, pId:0, name:"임시", open:true},
        { id:21, pId:2, name:"임시 폴더" ,open:true},
            { id:221, pId:21, name:"[임시]임시로만든 게시물" }];
            
   return zNodes;
}


