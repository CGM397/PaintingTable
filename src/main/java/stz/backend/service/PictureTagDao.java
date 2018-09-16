package stz.backend.service;

import stz.backend.entity.PictureTag;

public interface PictureTagDao {

    public boolean save(PictureTag pictureTag);

    public boolean modify(PictureTag pictureTag);

    public boolean delete(String pictureId, String tagId);

    public PictureTag find(String pictureId, String tagId);
}
