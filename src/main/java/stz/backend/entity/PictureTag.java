package stz.backend.entity;

import stz.backend.enums.DrawingType;

import java.util.ArrayList;

public class PictureTag {

    String pictureId;

    String tagId;

    ArrayList<Coordinate> border;

    DrawingType drawingType;

    public PictureTag(){ }

    public PictureTag(String pictureId, String tagId, ArrayList<Coordinate> border, DrawingType drawingType) {
        this.pictureId = pictureId;
        this.tagId = tagId;
        this.border = border;
        this.drawingType = drawingType;
    }

    public String getPictureId(){ return pictureId; }

    public String getTagId() { return tagId; }

    public ArrayList<Coordinate> getBorder() { return border; }

    public DrawingType getDrawingType() { return drawingType; }

    public void setPictureId(String pictureId){ this.pictureId = pictureId; }

    public void setTagId(String tagId) { this.tagId = tagId; }

    public void setBorder(ArrayList<Coordinate> border) { this.border = border; }

    public void setDrawingType(DrawingType drawingType) { this.drawingType = drawingType; }
}
