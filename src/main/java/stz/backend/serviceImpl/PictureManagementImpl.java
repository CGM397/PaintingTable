package stz.backend.serviceImpl;

import stz.backend.DAO.PictureDaoImpl;
import stz.backend.DAO.PictureTagDaoImpl;
import stz.backend.entity.Picture;
import stz.backend.service.PictureManagementService;

import java.util.ArrayList;

public class PictureManagementImpl implements PictureManagementService {

    private PictureDaoImpl pictureDao = new PictureDaoImpl();
    private PictureTagDaoImpl pictureTagDao = new PictureTagDaoImpl();

    @Override
    public boolean savePicture(Picture picture) {
        return pictureDao.save(picture);
    }

    @Override
    public boolean modifyPicture(Picture picture) {
        return pictureDao.modify(picture);
    }

    @Override
    public boolean deletePicture(String pictureId) {
        ArrayList<String> pictureTags = pictureDao.find(pictureId).getTags();
        boolean res = pictureDao.delete(pictureId);
        if(pictureTags != null)
            for(int i = 0; i < pictureTags.size(); i++)
                res = res && pictureTagDao.delete(pictureId, pictureTags.get(i));
        return res;
    }

    @Override
    public Picture findPicture(String pictureId) {
        return pictureDao.find(pictureId);
    }

    @Override
    public ArrayList<String> showAllPictures() {
        return pictureDao.showAllPictureId();
    }
}
