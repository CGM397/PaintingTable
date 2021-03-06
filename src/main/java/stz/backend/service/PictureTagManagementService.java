package stz.backend.service;

import stz.backend.entity.PictureTag;

/**
 * 增删改查
 */
public interface PictureTagManagementService {

    public boolean savePictureTag(PictureTag pictureTag);

    public boolean modifyPictureTag(PictureTag pictureTag);

    public boolean deletePictureTag(String pictureId, String tagId);

    public PictureTag findPictureTag(String pictureId, String tagId);

}
