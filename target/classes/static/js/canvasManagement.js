function clearCanvas() {
    pictureId = "";
    pictureBorders = [];
    allPoints = [];
    isDistinguished = false;

    var canvas = document.getElementById("cvs");
    var ctx = canvas.getContext("2d");
    var bg = new Image();
    bg.src = "../static/img/1.jpg";
    bg.onload = drawImg;//图片加载完成再执行
    function drawImg(){
        ctx.drawImage(bg,0,0,cvs.width,cvs.height);
    }
    document.getElementById('idNum').value = "";
    canvas.onmousedown = null;
    canvas.onmouseup = null;
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