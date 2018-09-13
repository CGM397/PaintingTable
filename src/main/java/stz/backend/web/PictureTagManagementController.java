package stz.backend.web;

import org.springframework.web.bind.annotation.*;
import stz.backend.entity.PictureTag;
import stz.backend.entity.PictureTagToSend;
import stz.backend.enums.DrawingType;
import stz.backend.service.PictureTagManagementService;
import stz.backend.serviceImpl.PictureTagManagementImpl;

@RestController
@RequestMapping("/PictureTagManagement")
public class PictureTagManagementController implements PictureTagManagementService {

    private PictureTagManagementImpl management = new PictureTagManagementImpl();

    @RequestMapping(value = "/savePictureTag", method = RequestMethod.POST)
    @ResponseBody
    public boolean save(@RequestBody PictureTagToSend picture){
        PictureTag res = new PictureTag();
        res.setPictureId(picture.getPictureId());
        res.setTagId(picture.getTagId());
        res.setBorder(picture.getBorder());
        DrawingType type = DrawingType.transToDrawingType(picture.getDrawingType());
        res.setDrawingType(type);
        return savePictureTag(res);
    }

    @Override
    public boolean savePictureTag(PictureTag pictureTag) {
        return management.savePictureTag(pictureTag);
    }

    @Override
    @RequestMapping(value = "/modifyPictureTag", method = RequestMethod.POST)
    @ResponseBody
    public boolean modifyPictureTag(@RequestBody PictureTag pictureTag) {
        return management.modifyPictureTag(pictureTag);
    }

    @Override
    @RequestMapping(value = "/deletePictureTag", method = RequestMethod.POST)
    @ResponseBody
    public boolean deletePictureTag(@RequestParam String pictureId, @RequestParam String tagId) {
        return management.deletePictureTag(pictureId, tagId);
    }

    @Override
    @RequestMapping(value = "/findPictureTag", method = RequestMethod.POST)
    @ResponseBody
    public PictureTag findPictureTag(@RequestParam String pictureId, @RequestParam String tagId) {
        return management.findPictureTag(pictureId, tagId);
    }
}
