package stz.backend.web;

import org.springframework.web.bind.annotation.*;
import stz.backend.entity.Picture;
import stz.backend.service.PictureManagementService;
import stz.backend.serviceImpl.PictureManagementImpl;

import java.util.ArrayList;

@RestController
@RequestMapping("/PictureManagement")
public class PictureManagementController implements PictureManagementService {

    private PictureManagementImpl pictureManagement = new PictureManagementImpl();

    @Override
    @RequestMapping(value = "/savePicture", method = RequestMethod.POST)
    @ResponseBody
    public boolean savePicture(@RequestBody Picture picture) {
        return pictureManagement.savePicture(picture);
    }

    @Override
    @RequestMapping(value = "/modifyPicture", method = RequestMethod.POST)
    @ResponseBody
    public boolean modifyPicture(@RequestBody Picture picture) {
        return pictureManagement.modifyPicture(picture);
    }

    @Override
    @RequestMapping(value = "/deletePicture", method = RequestMethod.POST)
    @ResponseBody
    public boolean deletePicture(@RequestParam String pictureId) {
        return pictureManagement.deletePicture(pictureId);
    }

    @Override
    @RequestMapping(value = "/findPicture", method = RequestMethod.POST)
    @ResponseBody
    public Picture findPicture(@RequestParam String pictureId) {
        return pictureManagement.findPicture(pictureId);
    }

    @Override
    @RequestMapping(value = "/showAllPictures", method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<String> showAllPictures() {
        return pictureManagement.showAllPictures();
    }
}
