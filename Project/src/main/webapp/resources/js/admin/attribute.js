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
        perPageSelect: [5, 10, 15, ["All", -1]],
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
     // 속성 추가 팝업 (category 넘겨받기)
     
     const attributeAddModal = document.getElementById('attributeAddModal')
     if (attributeAddModal) 
     {
        attributeAddModal.addEventListener('show.bs.modal', event => 
        {
             let cidx = $("#attributeList_cidx").val();
             $("#attributeAdd_cidx").val(cidx);
    	});
	 }
     
      // 속성 수정 팝업 (category/ attribute 넘겨받기)
     const attributeDetailModal = document.getElementById('attributeDetailModal')
     if (attributeDetailModal) 
     {
        attributeDetailModal.addEventListener('show.bs.modal', event => 
        {
             const button = event.relatedTarget;             
             const category = button.getAttribute('data-bs-category');
             console.log(category);

             let attribute = button.getAttribute('data-bs-attribute');
             console.log(attribute);
             
             $("#attributeDetail_cidx").val(category);
             $("#attributeDetail_aidx").val(attribute);
             
             $.ajax(
			{
				url: "/ezencompany/admin/attribute/info",
				type: "get",
				async:false,
				data : {aidx:attribute},
				success: function(resData) {
					if(resData != null)
					{
						$("#attributeDetail_cidx").val(resData.cidx);
						$("#attributeDetail_aidx").val(resData.aidx);
						$("#attributeDetail_key").val(resData.otkey);
						$("#attributeDetail_value").val(resData.value);
					}
				},
				error: function(error) {
		      		alert(error);
		    	}
			});
		
    	});
	 }
	 
	  // 분류 삭제 팝업 
     const attributeRemoveModal = document.getElementById('attributeRemoveModal')
     if (attributeRemoveModal) 
     {
        attributeRemoveModal.addEventListener('show.bs.modal', event => 
        { 
             // 상위 팝업에 있는 데이터 들고오기.
             let aidx = $("#attributeDetail_aidx").val();
             $("#attributeRemove_aidx").val(aidx);
		
    	});
	 }
     
     
  })();