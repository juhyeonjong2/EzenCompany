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
        labels:{
          info: "총 {rows}명"
        },
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
        ]
      });
    })
    
    /**
     * Initiate Modals
     */

    // 2. 회원 디테일 정보 팝업
    //const employeeDetailModal = document.getElementById('employeeDetailModal')
    //if (employeeDetailModal) {
    //    employeeDetailModal.addEventListener('show.bs.modal', event => 
    //    {
    //        const button = event.relatedTarget;
            //클릭한 사람의 이메일을 가져옴
    //        const email = button.getAttribute('data-bs-email');
    //        console.log(email);
            //vo안에 이사람의 정보를 전부 가져와서 뿌려준다
    //    });
        
   // }
  })();
