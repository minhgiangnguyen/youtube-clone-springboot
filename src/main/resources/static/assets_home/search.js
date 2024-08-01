
var width = $(window).width();
if(width >= 720){
    $(".search-item").addClass("row");
    $(".search-item").children().addClass("col-6");
}
else{
    $(".search-item").removeClass("row");
    $(".search-item").children().removeClass("col-6");
}
$(window).resize(function() {
var widthResize = $(window).width();
  if(widthResize >= 720){
    $(".search-item").addClass("row");
    $(".search-item").children().addClass("col-6");
//    $(".description").removeClass("d-none");
  }else{
     $(".search-item").removeClass("row");
     $(".search-item").children().removeClass("col-6");
//     $(".description").addClass("d-none");
  }
});

let pageNum = 1;
var isAtBottom = false; // Flag to prevent multiple triggers

$(window).on('scroll', function() {
     if (pageNum<totalPage && !isAtBottom
     && $(window).scrollTop() + $(window).height() >= $(document).height()) {
        console.log('Scrollbar is at the bottom');
        isAtBottom = true;
         $(".videos").after( loaderContent);

         setTimeout(() => {
              $("#loaderContent").remove();
                 pageNum++;
                 $.post(baseUrl+"/results?search_query="+keyword+"&pageNum="+
                 pageNum+"&width="+width,function(data){

                         $('.videos').append(data);
                 })
              isAtBottom = false; // Reset the flag
         }, 1000);
     }
})