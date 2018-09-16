package stz.backend.serviceImpl;

import stz.backend.DAO.PictureTagDaoImpl;
import stz.backend.entity.PictureTag;
import stz.backend.service.PictureTagManagementService;

public class PictureTagManagementImpl implements PictureTagManagementService {

    private PictureTagDaoImpl pictureTagDao = new PictureTagDaoImpl();

    @Override
    public boolean savePictureTag(PictureTag pictureTag) {
        return pictureTagDao.save(pictureTag);
    }

    @Override
    public boolean modifyPictureTag(PictureTag pictureTag) {
        return pictureTagDao.modify(pictureTag);
    }

    @Override
    public boolean deletePictureTag(String pictureId, String tagId) {
        return pictureTagDao.delete(pictureId, tagId);
    }

    @Override
    public PictureTag findPictureTag(String pictureId, String tagId) {
        return pictureTagDao.find(pictureId, tagId);
    }
}