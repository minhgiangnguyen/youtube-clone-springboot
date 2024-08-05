var formSearch = '<div id="search-bar" class="">'
                 +'<form class="form-inline" th:action="@{/results}" method="get">'
                    +'<div class="input-group">'
                 +'<input  type="search" class="form-control " placeholder="Search..." name="search_query" />'
                    +'<div class="input-group-prepend">'
                         +'<button class="btn input-group-text" type="submit">'
                             +'<i class="fas fa-search"></i>'
                                +'</button>'
                             +'</div>'
                         +'</div>'
                     +'</form>'
                 +'</div>'
var btnPrev = '<button id="btn-prev" class="btn col-4 d-none" type="submit"><i class="fa fa-arrow-left" ></i></button>'

$("#btn-search").click(function(){
    $("#btn-search").addClass("d-none");
     $("#search-bar").removeClass("d-none");
     $(".right-content").addClass("d-none");
//     $(".navbar-toggler").addClass("d-none");
//     $(".navbar-brand").addClass("d-none");
$("#youtube-logo").addClass("d-none");
     $("#btn-prev").removeClass("d-none");
     $("#topbar").addClass("pt-3");
 });

 $("#btn-prev").click(function(){
      $("#search-bar").addClass("d-none");
       $(".right-content").removeClass("d-none");
//       $(".navbar-toggler").removeClass("d-none");
//       $(".navbar-brand").removeClass("d-none");
       $("#youtube-logo").removeClass("d-none");
       $("#btn-prev").addClass("d-none");
       $("#topbar").removeClass("pt-3");
       $("#btn-search").removeClass("d-none");
  });


var width = $(window).width();
if(width >= 1200){
    $("#collapseSidebar").addClass("show");
}
else{
    $("#collapseSidebar").removeClass("show");
}
if(width < 650 && $("#btn-prev").hasClass("d-none")){
      $("#btn-search").removeClass("d-none");
      $("#search-bar").addClass("d-none");
  }
if(width>=650){
   $("#btn-search").addClass("d-none");
   $("#search-bar").removeClass("d-none");
   $(".right-content").removeClass("d-none");
//   $(".navbar-toggler").removeClass("d-none");
//   $(".navbar-brand").removeClass("d-none");
$("#youtube-logo").removeClass("d-none");
   $("#btn-prev").addClass("d-none");
   $("#topbar").removeClass("pt-3");
}


$(window).resize(function() {
var widthResize = $(window).width();
  if(widthResize >= 1200){
    $("#collapseSidebar").addClass("show");
  }else{
    $("#collapseSidebar").removeClass("show");
  }
  if(widthResize < 650 && $("#btn-prev").hasClass("d-none")){
      $("#btn-search").removeClass("d-none");
      $("#search-bar").addClass("d-none");
  }
  if(widthResize>=650){
       $("#btn-search").addClass("d-none");
       $("#search-bar").removeClass("d-none");
       $(".right-content").removeClass("d-none");
//       $(".navbar-toggler").removeClass("d-none");
//       $(".navbar-brand").removeClass("d-none");
    $("#youtube-logo").removeClass("d-none");
       $("#btn-prev").addClass("d-none");
       $("#topbar").removeClass("pt-3");
  }
});



const loaderContent= '<div id="loaderContent" class="loaderContent mt-3 d-flex justify-content-center">'
                        +'<div class="loader"></div>'
                         +'</div>';