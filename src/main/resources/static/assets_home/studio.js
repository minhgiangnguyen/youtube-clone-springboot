
 $(document).ready(function() {
    $('#thumbFile').change(function() {
    console.log("onChange");
        $("#thumbImgTag").html('<img src="" alt="Thumbnail" id="thumbnail" width="400">');
        showThumbnail(this);
    })

})
function showThumbnail(fileInput){
    file = fileInput.files[0];
    reader = new FileReader();
    reader.onload=function(e){
        $('#thumbnail').attr('src',e.target.result);
    }
    reader.readAsDataURL(file);
}
