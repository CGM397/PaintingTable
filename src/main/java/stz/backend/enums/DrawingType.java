package stz.backend.enums;

public enum DrawingType {
    CIRCLE("圆"),
    TRIANGLE("三角形"),
    SQUARE("正方形"),
    RECTANGLE("长方形"),
    Unknown("未知图形");

    String value;
    DrawingType(String value){
        this.value = value;
    }

    public static String transToString(DrawingType type){
        if(type.equals(CIRCLE))
            return "圆";
        if(type.equals(TRIANGLE))
            return "三角形";
        if(type.equals(SQUARE))
            return "正方形";
        if(type.equals(RECTANGLE))
            return "长方形";
        else
            return "未知图形";
    }

    public static DrawingType transToDrawingType(String type){
        if(type.equals("圆"))
            return DrawingType.CIRCLE;
        if(type.equals("三角形"))
            return DrawingType.TRIANGLE;
        if(type.equals("正方形"))
            return DrawingType.SQUARE;
        if(type.equals("长方形"))
            return DrawingType.RECTANGLE;
        else
            return DrawingType.Unknown;
    }
}
