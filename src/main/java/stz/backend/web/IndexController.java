package stz.backend.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/canvas")
    public String canvas(){
        return "canvas";
    }

}
