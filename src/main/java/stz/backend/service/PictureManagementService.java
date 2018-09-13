package stz.backend.service;

import stz.backend.entity.Picture;

import java.util.ArrayList;

public interface PictureManagementService {

    public boolean savePicture(Picture picture);

    public boolean modifyPicture(Picture picture);

    public boolean deletePicture(String pictureId);

    public Picture findPicture(String pictureId);

    public ArrayList<String> showAllPictures();
}
