package stz.backend.service;

import stz.backend.entity.Picture;

import java.util.ArrayList;

public interface PictureDao {

    public boolean save(Picture picture);

    public boolean modify(Picture picture);

    public boolean delete(String pictureId);

    public Picture find(String pictureId);

    public ArrayList<String> showAllPictureId();
}
