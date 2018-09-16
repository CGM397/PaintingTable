function savePictureTag(tag) {
    $.ajax({
        type: "POST",
        url: "/PictureTagManagement/savePictureTag",
        contentType: "application/json",
        data: JSON.stringify(tag),
        success: function (result) {
            if(!result)
                swal("保存失败", "保存失败", "fail");
        },
        error: function (result) {
            alert("error");
        }
    });
}

function showOneTag(pictureId, tagId) {
    $.ajax({
        type: "POST",
        url: "/PictureTagManagement/findPictureTag",
        data: {
            pictureId: pictureId,
            tagId: tagId
        },
        success: function (result) {
            reDrawTagBorder(result.border, transToString(result.drawingType));
        },
        error: function (result) {
            alert("error");
        }
    });
}

function transToString(type) {
    if(type == "CIRCLE")
        return "圆";
    else if(type == "TRIANGLE")
        return "三角形";
    else if(type == "RECTANGLE")
        return "长方形";
    else if(type == "SQUARE")
        return "正方形";
}