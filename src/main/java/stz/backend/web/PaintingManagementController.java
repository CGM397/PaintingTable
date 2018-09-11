package stz.backend.web;

import org.springframework.web.bind.annotation.*;
import stz.backend.entity.Picture;
import stz.backend.entity.PictureToSend;
import stz.backend.enums.DrawingType;
import stz.backend.service.PaintingManagementService;
import stz.backend.serviceImpl.PaintingManagementImpl;

import java.util.ArrayList;

@RestController
@RequestMapping("/PaintingManagement")
public class PaintingManagementController implements PaintingManagementService {

    private PaintingManagementImpl management = new PaintingManagementImpl();

    @RequestMapping(value = "/savePaintingResult", method = RequestMethod.POST)
    @ResponseBody
    public boolean save(@RequestBody PictureToSend picture){
        Picture res = new Picture();
        res.setPictureId(picture.getPictureId());
        res.setBorder(picture.getBorder());
        DrawingType type = DrawingType.transToDrawingType(picture.getDrawingType());
        res.setDrawingType(type);
        return savePaintingResult(res);
    }

    @Override
    public boolean savePaintingResult(@RequestBody Picture picture) {
        return management.savePaintingResult(picture);
    }

    @Override
    @RequestMapping(value = "/modifyPaintingResult", method = RequestMethod.POST)
    @ResponseBody
    public boolean modifyPaintingResult(@RequestBody Picture picture) {
        return management.modifyPaintingResult(picture);
    }

    @Override
    @RequestMapping(value = "/deletePaintingResult", method = RequestMethod.POST)
    @ResponseBody
    public boolean deletePaintingResult(@RequestParam String pictureId) {
        return management.deletePaintingResult(pictureId);
    }

    @Override
    @RequestMapping(value = "/findByPictureId", method = RequestMethod.POST)
    @ResponseBody
    public Picture findByPictureId(@RequestParam String pictureId) {
        return management.findByPictureId(pictureId);
    }

    @Override
    @RequestMapping(value = "/showAllPictureId", method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<String> showAllPictureId() {
        return management.showAllPictureId();
    }
}
