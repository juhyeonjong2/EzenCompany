(
    function() 
    {
    "use strict";
    
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


  function openMultiModal(modalId){
    let myModal = new bootstrap.Modal(document.getElementById(modalId), {});
    myModal.show();
  }
