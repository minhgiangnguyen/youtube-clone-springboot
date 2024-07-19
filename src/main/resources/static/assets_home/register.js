
 $(document).ready(function() {
    $('#file').change(function() {
//        $("#avatarImgTag").empty();
        $("#avatarImgTag").html('<img src="" alt="Photo Preview" id="avatar" width="200">');
        showAvatar(this);
    })
})
function showAvatar(fileInput){
    file = fileInput.files[0];
    reader = new FileReader();
    reader.onload=function(e){
        $('#avatar').attr('src',e.target.result);
    }
    reader.readAsDataURL(file);

}