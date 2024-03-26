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
    const employeeDetailModal = document.getElementById('employeeDetailModal')
    if (employeeDetailModal) {
        employeeDetailModal.addEventListener('show.bs.modal', event => 
        {
            // Button that triggered the modal
            const button = event.relatedTarget;
            // Extract info from data-bs-* attributes
            const mno = button.getAttribute('data-bs-mno');
            console.log(mno);
            // If necessary, you could initiate an Ajax request here
            // and then do the updating in a callback.
            // Update the modal's content.
           
           // const modalTitle = exampleModal.querySelector('.modal-title')
            //const modalBodyInput = exampleModal.querySelector('.modal-body input')

            //modalTitle.textContent = `New message to ${recipient}`
            //modalBodyInput.value = recipient
        });
        
    }
  })();
