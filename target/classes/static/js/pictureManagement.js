function savePicture(picture) {
    $.ajax({
        type: "POST",
        url: "/PictureManagement/savePicture",
        contentType: "application/json",
        data: JSON.stringify(picture),
        success: function (result) {
            if(result){
                swal("保存成功", "保存成功", "success");
                addSelection(pictureId);
            }
            else
                swal("保存失败", "保存失败", "fail");
            clearCanvas();
        },
        error: function (result) {
            alert("error");
        }
    });
}

function deletePicture(onePictureId) {
    $.ajax({
        type: 'POST',
        url:"/PictureManagement/deletePicture",
        data: {
            pictureId:onePictureId
        },
        success:function(result){
            if(result){
                var selects=document.getElementById("selectMenu");
                var index=selects.selectedIndex;
                selects.removeChild(selects.options[index]);
                if(document.getElementById("idNum").value == onePictureId)
                    clearCanvas();
                swal("删除成功", "删除成功", "success");
            }else
                swal("删除失败", "删除失败", "fail");
        },
        error: function () {
            alert("error");
        }
    });
}

function findOnePicture(onePictureId) {
    $.ajax({
        type: "POST",
        url: "/PictureManagement/findPicture",
        //async:false,
        data: {
            pictureId: onePictureId
        },
        success: function (result) {
            document.getElementById('idNum').value = onePictureId;
            pictureId = onePictureId;
            allPoints = result.allPoints;
            tags = result.tags;
            reDrawPicture(result.allPoints);

            var tagIds = result.tags;
            for(var i = 0; i < tagIds.length; i++)
                showOneTag(pictureId, tagIds[i]);
        },
        error: function (result) {
            alert("error");
        }
    });
}

function showAllPictureId() {
    $.ajax({
        type: 'POST',
        url:"/PictureManagement/showAllPictures",
        data: { },
        success:function(result){
            var selection=document.getElementById("selectMenu");
            for(var i=0;i<result.length;i++)
                selection.options.add(new Option(result[i]));
        },
        error: function () {
            alert("error");
        }
    });
}