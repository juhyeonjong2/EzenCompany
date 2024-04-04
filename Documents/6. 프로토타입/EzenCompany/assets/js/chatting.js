(
    function() 
    {
    "use strict";

    /**
     * Initiate Modals
     */

    // 채팅 팝업(대화창) - 대화 상대의 mno or id를 넘겨받음.
    const attributeAddModal = document.getElementById('chattingRoomModal')
    if (attributeAddModal) {
        attributeAddModal.addEventListener('show.bs.modal', event => 
        {
            // Button that triggered the modal
            const button = event.relatedTarget;
            // Extract info from data-bs-* attributes
            const category = button.getAttribute('data-bs-mno');
            console.log(category);
            
        });
        
    }

  })();

