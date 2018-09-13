package stz.backend.entity;

import java.util.ArrayList;

public class Picture {

    String pictureId;

    ArrayList<Coordinate> allPoints;

    ArrayList<String> tags;

    public Picture() { }

    public Picture(String pictureId, ArrayList<Coordinate> allPoints, ArrayList<String> tags){
        this.pictureId = pictureId;
        this.allPoints = allPoints;
        this.tags = tags;
    }

    public String getPictureId() {
        return pictureId;
    }

    public ArrayList<Coordinate> getAllPoints() {
        return allPoints;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public void setAllPoints(ArrayList<Coordinate> allPoints) {
        this.allPoints = allPoints;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
}
