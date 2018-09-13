package stz.backend.entity;

import java.util.ArrayList;

public class PictureTagToSend {
    String pictureId;

    String tagId;

    ArrayList<Coordinate> border;

    String drawingType;

    public PictureTagToSend(){}

    public PictureTagToSend(String pictureId, String tagId, ArrayList<Coordinate> border, String drawingType){
        this.pictureId = pictureId;
        this.tagId = tagId;
        this.border = border;
        this.drawingType = drawingType;
    }

    public String getPictureId() {
        return pictureId;
    }

    public String getTagId() { return tagId; }

    public ArrayList<Coordinate> getBorder() {
        return border;
    }

    public String getDrawingType() {
        return drawingType;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public void setTagId(String tagId) { this.tagId = tagId; }

    public void setDrawingType(String drawingType) {
        this.drawingType = drawingType;
    }

    public void setBorder(ArrayList<Coordinate> border) {
        this.border = border;
    }
}
