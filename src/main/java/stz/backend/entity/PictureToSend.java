package stz.backend.entity;

import java.util.ArrayList;

public class PictureToSend {
    String pictureId;

    ArrayList<Coordinate> border;

    String drawingType;

    public PictureToSend(){}

    public PictureToSend(String pictureId, ArrayList<Coordinate> border, String drawingType){
        this.pictureId = pictureId;
        this.border = border;
        this.drawingType = drawingType;
    }

    public String getPictureId() {
        return pictureId;
    }

    public ArrayList<Coordinate> getBorder() {
        return border;
    }

    public String getDrawingType() {
        return drawingType;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public void setDrawingType(String drawingType) {
        this.drawingType = drawingType;
    }

    public void setBorder(ArrayList<Coordinate> border) {
        this.border = border;
    }
}
