
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
    },
    callback : {
    	onClick:moveBlogPage
    }
};

/*--------------------------------------------------------------
# Init
--------------------------------------------------------------*/

$(document).ready(function(){
    $.fn.zTree.init($("#blog_tree"), setting, getBlogNodes());
   
});

function moveBlogPage(event, treeId, treeNode) {
    location.href = "/ezencompany/blog/page/" +  treeNode.ename;
};

function getBlogNodes(){

	let zNodes =[];
	
	let mno = $("#inputMno").val();
	$.ajax(
	{
		url: "/ezencompany/blog/folder/nodes",
		type: "get",
		data : {mno:mno},
		async : false,
		success: function(resData) {
			let nodes = resData.nodes;
			for(let i=0;i<nodes.length;i++)
			{
				//console.log(nodes[i]);
				
				let n = nodes[i]; 
				let node = {
					id : n.id,
					pId : n.pid,
					name: n.name,
				};
				
				if(n.node == false)
				{
					node.isParent = true;
					node.open = n.open;
				}
				else {
					node.ename = n.blogNo;
				}
				//console.log(node);
				zNodes.push(node);
			}
		},
		error: function(error) {
      		alert(error);
    	}
	});
	
   return zNodes;
}

