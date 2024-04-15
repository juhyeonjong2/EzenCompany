(
    function() 
    {
    "use strict";
    
    /**
     * Initiate Modals
     */
    
      // 폴더 추가 팝업
     const folderAddModal = document.getElementById('folderAddModal')
     if (folderAddModal) 
     {
        folderAddModal.addEventListener('show.bs.modal', event => 
        {
             $.ajax(
			{
				url: "/blog/folder/list",
				type: "get",
				async:false,				
				success: function(resData) {
					if(resData.result == "SUCCESS")
					{
						let parent = $("#folderAdd_parent");
						// 옵션 모두 삭제
						$("select#folderAdd_parent option").remove();
						
						// 없음 항목 추가
						parent.append(makeFolderOption(0, '없음', true));
						let folders = resData.folders;
						for(let i=0;i<folders.length;i++){
							let folder = folders[i];
							parent.append(makeFolderOption(folder.fno,  folder.fname, false));
						}
					}
				},
				error: function(error) {
		      		alert(error);
		    	}
			});
		
    	});
	 }

  })();

function insertFolder(){
	
	let parentFno = $("#folderAdd_parent").val();
	console.log(parentFno);
	let fname = $("#folder_inputName").val();
	console.log(fname);
	
	if(!fname){
		return;
	}
	
  	$.ajax(
		{
			url: "/blog/folder/write",
			type: "post",
			data:{pfno:parentFno, fname:fname},				
			success: function(resData) {
				if(resData.result == "SUCCESS")
				{
				  // write에 성공한 경우. write페이지의 폴더 목록을 갱신해야함.
				  refreshFolders();
				}
			},
			error: function(error) {
	      		alert(error);
	    	}
		});
	
	// 아작스로 전송.

}


function refreshFolders(){
	 $.ajax(
	{
		url: "/blog/folder/list",
		type: "get",				
		success: function(resData) {
			if(resData.result == "SUCCESS")
			{
				let parent = $("#folrder_list");
				// 옵션 모두 삭제
				$("select#folrder_list option").remove();
				
				let folders = resData.folders;
				
				let ffno = $("#folderFno").val();
				for(let i=0;i<folders.length;i++){
					let folder = folders[i];
					if(ffno == -1) {
						if(i==0){
							parent.append(makeFolderOption(folder.fno,  folder.fname, true));
						}
						else {
							parent.append(makeFolderOption(folder.fno,  folder.fname, false));
						}
					}
					else {
						if(folder.fno == ffno){
							parent.append(makeFolderOption(folder.fno,  folder.fname, true));
						}else {
							parent.append(makeFolderOption(folder.fno,  folder.fname, false));
						}
						
					}
				}
			}
		},
		error: function(error) {
      		alert(error);
    	}
	});
}










