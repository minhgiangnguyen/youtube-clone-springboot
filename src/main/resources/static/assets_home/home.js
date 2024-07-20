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

let pageNum = 1;
var isAtBottom = false; // Flag to prevent multiple triggers

$(window).on('scroll', function() {
     if (pageNum<totalPage && !isAtBottom
     && $(window).scrollTop() + $(window).height() >= $(document).height()) {
        console.log('Scrollbar is at the bottom');
        isAtBottom = true;
         $( "#allVideo" ).after( loaderContent);

         setTimeout(() => {
              $("#loaderContent").remove();
                 pageNum++;
                 $.post(baseUrl+"/page/"+pageNum, function(data){
                         $('#allVideo .card-deck').append(data);
                 })
              isAtBottom = false; // Reset the flag
         }, 3000);
     }
})





