package com.qth.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "page")
public class IndexController {

    @RequestMapping(value = "{path}")
    public String forward(@PathVariable String path){
        return path;
    }
}
