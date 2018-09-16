//draw
function draw() {
    isWork = true;
    document.getElementById('cvs').onmousedown = null;
    document.getElementById('cvs').onmouseup = null;
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

//show one picture
function showOnePicture() {
    isWork = false;
    isDistinguished = false;
    var selects=document.getElementById("selectMenu");
    var index=selects.selectedIndex;
    var selectedPictureId = selects[index].text;
    if(selectedPictureId == "-- please select --"){
        alert("请选择一个图片编号");
        return;
    }
    clearCanvas();

    findOnePicture(selectedPictureId);
}

function deleteSelection(){
    var selects = document.getElementById("selectMenu");
    var index = selects.selectedIndex;
    var onePictureId = selects[index].text;
    if(onePictureId == "-- please select --"){
        alert("请选择一个图片编号");
        return;
    }
    deletePicture(onePictureId);
}

//distinguish picture type
function distinguish() {
    if(!isWork){
        alert("请先点击 '画图' 按钮进行画图。");
        return;
    }
    if(isDistinguished){
        alert("请勿重复点击 '识别' 按钮。");
        return;
    }
    pictureId = "pic--" + new Date().getTime();
    document.getElementById('idNum').value = pictureId;
    drawDistinguishArea();
    isDistinguished = true;
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