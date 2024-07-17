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

$('#slickVideos').slick({
  infinite: false,
  variableWidth: false,
  speed: 300,
  slidesToShow: 4,
  slidesToScroll: 4,
  prevArrow: '<button class="shadow-lg slide-arrow prev-arrow circle-button"><i class="fas fa-chevron-left"></i></button>',
  nextArrow: '<button class="shadow-lg slide-arrow next-arrow circle-button"><i class="fas fa-chevron-right"></i></button>',
  responsive: [
      {
        breakpoint: 1024,
        settings: {
          slidesToShow: 3,
          slidesToScroll: 3,
        }
      },
      {
        breakpoint: 600,
        settings: {
          slidesToShow: 2,
          slidesToScroll: 2
        }
      },
      {
        breakpoint: 480,
        settings: {
          slidesToShow: 1,
          slidesToScroll: 1
        }
      }
      // You can unslick at a given breakpoint now by adding:
      // settings: "unslick"
      // instead of a settings object
    ]
})

function sortVideos(sortType){

    if($("#btn-sort-latest").hasClass("btn-dark") && sortType=="latest") return false;
    if($("#btn-sort-oldest").hasClass("btn-dark") && sortType=="oldest") return false;
    $.post(baseUrl+"/"+channelName+"/videos?s="+sortType, function(data){
        $('#sortVideos').html(data);

        if(sortType=="latest"){
            $("#btn-sort-latest").removeClass("btn-light");
            $("#btn-sort-latest").addClass("btn-dark");
            $("#btn-sort-oldest").removeClass("btn-dark");
            $("#btn-sort-oldest").addClass("btn-light");
        }
        if(sortType=="oldest"){
            $("#btn-sort-oldest").removeClass("btn-light");
            $("#btn-sort-oldest").addClass("btn-dark");
            $("#btn-sort-latest").removeClass("btn-dark");
            $("#btn-sort-latest").addClass("btn-light");
        }
    })

    return false;
}

