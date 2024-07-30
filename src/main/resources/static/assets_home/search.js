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
  }else{
     $(".search-item").removeClass("row");
     $(".search-item").children().removeClass("col-6");
  }
});