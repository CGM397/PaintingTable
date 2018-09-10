package stz.backend.service;

import stz.backend.entity.Picture;

/**
 * 增删改查
 */
public interface PaintingManagementService {

    public boolean savePaintingResult(Picture picture);

    public boolean modifyPaintingResult(Picture picture);

    public boolean deletePaintingResult(String pictureId);

    public Picture findByPictureId(String pictureId);

}
