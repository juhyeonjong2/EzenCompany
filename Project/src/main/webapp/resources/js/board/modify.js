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
     * Initiate Editor
     */
     tinymce.init({
        selector: '#editor_board_content'
      });

 
  })();
