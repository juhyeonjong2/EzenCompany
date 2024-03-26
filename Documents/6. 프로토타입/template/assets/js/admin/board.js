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

    console.log("111111");
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


    console.log("22222");


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


    // 트리뷰 참고
    // https://www.w3schools.com/howto/tryit.asp?filename=tryhow_js_treeview
    // https://opentutorials.org/module/2398/13715
    
    // 노드
    //  - 체크박스 (이미지/글자) (+)
    // 리프
    //  - (이미지/글자) (-)
    const toggler = document.getElementsByClassName("caret");
    for (let i = 0; i < toggler.length; i++)  
    {
      toggler[i].addEventListener("click", function() 
      {
        console.log("111", this.parentElement);
        console.log("222", this.parentElement.parentElement);
        console.log("333", this.parentElement.parentElement.querySelector(".nested"));
        console.log("444", this.parentElement.parentElement.querySelector(".nested").classList);
        console.log("555", this.parentElement.parentElement.querySelector(".nested").classList.toggle("active"));
        // 여기 토글하면 안바뀜. 모달이라서 그런거 같으니 remove / add로 변경할 것 to do
        
        this.parentElement.parentElement.querySelector(".nested").classList.toggle("active");
        this.classList.toggle("caret-down");
      });
    }


  })();