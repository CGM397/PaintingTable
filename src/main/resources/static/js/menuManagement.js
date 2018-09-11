//draw
function draw() {
    isWork = true;
    document.getElementById('cvs').onmousedown=null;
    document.getElementById('cvs').onmouseup=null;
    clearCanvas();
    var oC = document.getElementById('cvs');
    var oGc = oC.getContext('2d');
    oC.onmousedown = function (ev) {
        ev = ev || window.event;
        oGc.beginPath();
        oGc.moveTo(ev.offsetX, ev.offsetY); //ev.clientX-oC.offsetLeft,ev.clientY-oC.offsetTop鼠标在当前画布上X,Y坐标
        document.onmousemove = function (ev) {
            ev = ev || window.event;//获取event对象
            oGc.lineTo(ev.offsetX, ev.offsetY);
            oGc.stroke();
            var point={"x":ev.offsetX,"y":ev.offsetY};
            border.push(point);
        };
        oC.onmouseup = function (ev) {
            document.onmousemove = null;
            document.onmouseup = null;
            num += 1;
            oGc.closePath();
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
        $.ajax({
            type: "POST",
            url: "/PaintingManagement/savePaintingResult",
            contentType: "application/json",
            data: JSON.stringify(res),
            success: function (result) {
                if(result){
                    swal("保存成功", "保存成功", "success");
                    addSelection(pictureId);
                }
                else
                    swal("保存失败", "保存失败", "success");

                clearCanvas();

            },
            error: function (result) {
                alert("error");
            }
        });
    }
}

//show all pictureId
function showAllPictureId() {
    $.ajax({
        type: 'POST',
        url:"/PaintingManagement/showAllPictureId",
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
        url: "/PaintingManagement/findByPictureId",
        //async:false,
        data: {
            pictureId: selectedPictureId
        },
        success: function (result) {
            document.getElementById("description").value = transToString(result.drawingType);
            document.getElementById('idNum').value = selectedPictureId;
            border = result.border;
            pictureId = selectedPictureId;
            reDrawPicture();
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
    if(num == 1){
        swal("圆", "圆", "success");
        drawingType = "圆";
    }else if(num == 2){
        swal("三角形", "三角形", "success");
        drawingType = "三角形";
    }else if(num == 3){
        swal("长方形", "长方形", "success");
        drawingType = "长方形";
    }else if(num == 4){
        swal("正方形", "正方形", "success");
        drawingType = "正方形";
    }else{
        swal("未知图形", "未知图形", "success");
        drawingType = "未知图形";
    }
    pictureId = new Date().getTime();
    res = new Picture(pictureId, border, drawingType);
    isDistinguished = true;
    document.getElementById('description').value = drawingType;
    document.getElementById('idNum').value = pictureId;
}

function clearCanvas() {
    pictureId = "";
    border = [];
    drawingType = "";
    num = 0;
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

function reDrawPicture() {
    var len = border.length;
    var oCanvas = document.getElementById('cvs');
    var oGc = oCanvas.getContext('2d');
    for (var i = 0; i < len - 1; i++) {
        oGc.beginPath();
        oGc.moveTo(border[i].x , border[i].y);
        oGc.lineTo(border[i+1].x, border[i+1].y);
        oGc.stroke();
        oGc.closePath();
    }
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