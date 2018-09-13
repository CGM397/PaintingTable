//draw
function draw() {
    isWork = true;
    document.getElementById('cvs').onmousedown=null;
    document.getElementById('cvs').onmouseup=null;
    clearCanvas();
    var oC = document.getElementById('cvs');
    var oGc = oC.getContext('2d');
    oGc.strokeStyle = "black";
    var oneBorder = [];
    oC.onmousedown = function (ev) {
        oneBorder = [];
        ev = ev || window.event;
        oGc.beginPath();
        oGc.moveTo(ev.offsetX, ev.offsetY);
        document.onmousemove = function (ev) {
            ev = ev || window.event;//获取event对象
            oGc.lineTo(ev.offsetX, ev.offsetY);
            oGc.stroke();
            var point={"x":ev.offsetX,"y":ev.offsetY};
            oneBorder.push(point);
            allPoints.push(point);
        };
        oC.onmouseup = function (ev) {
            document.onmousemove = null;
            document.onmouseup = null;
            oGc.closePath();
            var specialPoint = {"x":-1,"y":-1};
            allPoints.push(specialPoint);         //magic!
            pictureBorders.push(oneBorder);
        };
    };
}

//save
function save() {
    if (!isWork){
        alert("请先点击'画图'按钮进行画图");
    }else if(!isDistinguished){
        alert("请先点击'识别'按钮识别图形");
    } else {
        var onePicture = new Picture(pictureId, allPoints, tags);
        savePicture(onePicture);
        for(var i = 0; i < saveTags.length; i++){
            savePictureTag(saveTags[i]);
        }
    }
}

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

//show all pictureId
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

//show one picture
function showOnePicture() {
    var selects=document.getElementById("selectMenu");
    var index=selects.selectedIndex;
    var selectedPictureId = selects[index].text;
    if(selectedPictureId == "-- please select --"){
        alert("请选择一个图片编号");
        return;
    }
    clearCanvas();
    $.ajax({
        type: "POST",
        url: "/PictureManagement/findPicture",
        //async:false,
        data: {
            pictureId: selectedPictureId
        },
        success: function (result) {
            document.getElementById('idNum').value = selectedPictureId;
            pictureId = selectedPictureId;
            reDrawPicture(result.allPoints);

            var tags = result.tags;
            for(var i = 0; i < tags.length; i++){
                showOneTag(pictureId, tags[i]);
            }
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

//delete
function del() {
    var selects=document.getElementById("selectMenu");
    var index=selects.selectedIndex;
    var tagID=selects[index].text;
    if(isWork != false){
        alert("请先查看图片!");
    }
    else if(index == 0){
        alert("请选择标注!");
    }
    else{
        $.ajax({
            type: 'POST',
            url:"/deleteOneTag",
            data: {
                pictureId:pictureId
                },
            success:function(result){
                alert("success!");
                var selects=document.getElementById("selectMenu");
                var index=selects.selectedIndex;
                var canvas = document.getElementById("cvs");
                var ctx = canvas.getContext("2d");
                // canvas清屏
                ctx.clearRect(50, 50, canvas.width-100, canvas.height-100);
                ctx.drawImage(image, 50, 50, 700, 500);
                document.getElementById("description").value="";
                selects.removeChild(selects.options[index]);
            },
            error: function () {
                alert("There is no tag to delete!");
            }
        });
    }
}

//distinguish picture type
function distinguish() {
    pictureId = "pic--" + new Date().getTime();
    document.getElementById('idNum').value = pictureId;
    drawDistinguishArea();
    isDistinguished = true;
}

function clearCanvas() {
    pictureId = "";
    pictureBorders = [];
    allPoints = [];
    res = null;
    isDistinguished = false;

    var canvas = document.getElementById("cvs");
    var ctx = canvas.getContext("2d");
    var bg = new Image();
    bg.src = "../static/img/1.jpg";
    bg.onload = drawImg;//图片加载完成再执行
    function drawImg(){
        ctx.drawImage(bg,0,0,cvs.width,cvs.height);
    }
    document.getElementById('description').value = "";
    document.getElementById('idNum').value = "";
}

function addSelection(OnePictureId) {
    var selection=document.getElementById("selectMenu");
    selection.options.add(new Option(OnePictureId));
}

function reDrawPicture(allPoints) {
    var len = allPoints.length;
    var oCanvas = document.getElementById('cvs');
    var oGc = oCanvas.getContext('2d');
    for (var i = 0; i < len - 1; i++) {
        if(allPoints[i+1].x == -1 && allPoints[i+1].y == -1){
            i += 2;
            continue;
        }
        oGc.beginPath();
        oGc.moveTo(allPoints[i].x , allPoints[i].y);
        oGc.lineTo(allPoints[i+1].x, allPoints[i+1].y);
        oGc.stroke();
        oGc.closePath();
    }
}

function reDrawTagBorder(border, type) {
    var oCanvas = document.getElementById('cvs');
    var oGc = oCanvas.getContext('2d');
    oGc.strokeStyle = "red";
    oGc.beginPath();
    oGc.strokeRect(border[0].x, border[0].y, border[1].x - border[0].x, border[1].y - border[0].y);
    oGc.closePath();
    var numX = (border[0].x > border[1].x) ? border[1].x : border[0].x;
    var numY = (border[0].y > border[1].y) ? border[1].y : border[0].y;
    oGc.font="20px Arial";
    oGc.fillText(type, numX, numY - 10);
    oGc.strokeStyle = "black";
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

function drawDistinguishArea() {
    var oCanvas = document.getElementById('cvs');
    var oGc = oCanvas.getContext('2d');
    oCanvas.onmousedown = function (ev) {
        ev = ev || event;
        var startX = ev.offsetX;
        var startY = ev.offsetY;
        oCanvas.onmouseup = function (ev) {
            ev = ev || event;
            oCanvas.onmousemove = null;
            oCanvas.onmouseup = null;
            var endX = ev.offsetX;
            var endY = ev.offsetY;
            oGc.strokeStyle = "red";
            if (Math.abs(endX - startX) > 10 && Math.abs(endY - startY) > 10) {
                oGc.beginPath();
                oGc.strokeRect(startX, startY, endX - startX, endY - startY);
                oGc.closePath();
                var tagId = "tag--" + new Date().getTime();
                var startPoint = {"x": startX, "y": startY};
                var endPoint = {"x": endX, "y": endY};
                var border = [];
                border.push(startPoint);
                border.push(endPoint);
                var numX = (startX > endX) ? endX : startX;
                var numY = (startY > endY) ? endY : startY;
                var type = distinguishMethod(countBorders(startX,startY,endX,endY));
                var oneTag = new PictureTag(pictureId, tagId, border, type);
                tags.push(tagId);
                saveTags.push(oneTag);
                oGc.font="20px Arial";
                oGc.fillText(type, numX, numY - 10);
            }
            oGc.strokeStyle = "black";
        };
    };
}

function distinguishMethod(num) {
    var drawingType = "";
    if(num == 1){
        //swal("圆", "圆", "success");
        drawingType = "圆";
    }else if(num == 2){
        //swal("三角形", "三角形", "success");
        drawingType = "三角形";
    }else if(num == 3){
        //swal("长方形", "长方形", "success");
        drawingType = "长方形";
    }else if(num == 4){
        //swal("正方形", "正方形", "success");
        drawingType = "正方形";
    }else{
        //swal("未知图形", "未知图形", "success");
        drawingType = "未知图形";
    }
    return drawingType;
}

function countBorders(startX, startY, endX, endY) {
    var res = 0;
    var x1 = (startX < endX) ? startX : endX;
    var x2 = (startX > endX) ? startX : endX;
    var y1 = (startY < endY) ? startY : endY;
    var y2 = (startY > endY) ? startY : endY;
    for(var i = 0; i < pictureBorders.length; i++){
        var oneLine = pictureBorders[i];
        var len = 0;
        for(var j = 0; j < oneLine.length; j++){
            var thisX = oneLine[j].x;
            var thisY = oneLine[j].y;
            if(thisX < x1 || thisX > x2 || thisY < y1 || thisY > y2)
                break;
            len++;
        }
        if(len == oneLine.length)
            res++;
    }
    return res;
}