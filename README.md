# <center>PaintingTable</center>
## <center>软件构造第一次作业</center>
### 1.使用教程
#### &emsp;1.1.数据库配置
&emsp;&emsp;找到项目中的sql文件夹，将其中的.sql文件导入数据库，数据库账号密码均改为"root"(或者修改src/main/java/stz/backend/DAO/BaseDao.java中的配置文件)

#### &emsp;1.2.运行环境要求
&emsp;&emsp;Java运行环境、Chrome浏览器(推荐)

#### &emsp;1.3.具体功能说明
&emsp;&emsp;首先点击"画图"按钮进行画图，画完之后点击"识别"按钮进行识别(按住鼠标左键框住待识别的图形)，识别完成后点击"保存"按钮保存，点击"查看"按钮可以查看之前所画的图片及其标注，点击"删除"按钮可以删除选中的图片及其标注，点击"修改"按钮可以在原有的图片上再继续画图形并识别。