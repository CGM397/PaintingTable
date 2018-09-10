package stz.backend.entity;

import stz.backend.enums.DrawingType;

import java.util.ArrayList;

public class Picture {

    String pictureId;

    ArrayList<Coordinate> border;

    DrawingType drawingType;

    public Picture(){ }

    public Picture(String pictureId, ArrayList<Coordinate> border, DrawingType drawingType) {
        this.pictureId = pictureId;
        this.border = border;
        this.drawingType = drawingType;
    }

    public String getPictureId(){ return pictureId; }

    public ArrayList<Coordinate> getBorder() { return border; }

    public DrawingType getDrawingType() { return drawingType; }

    public void setPictureId(String pictureId){ this.pictureId = pictureId; }

    public void setBorder(ArrayList<Coordinate> border) { this.border = border; }

    public void setDrawingType(DrawingType drawingType) { this.drawingType = drawingType; }
}
