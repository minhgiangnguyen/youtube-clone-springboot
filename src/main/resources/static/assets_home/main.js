var width = $(window).width();
if(width >= 1200)
    $("#collapseSidebar").addClass("show");
else
    $("#collapseSidebar").removeClass("show");
$(window).resize(function() {
var widthResize = $(window).width();
  if(widthResize >= 1200){
    $("#collapseSidebar").addClass("show");
  }else{
    $("#collapseSidebar").removeClass("show");
  }
});

const loaderContent= '<div id="loaderContent" class="loaderContent mt-3 d-flex justify-content-center">'
                        +'<div class="loader"></div>'
                         +'</div>';