package com.qth.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "page")
public class IndexController {

    @RequestMapping("index")
    public String index(){
        return "redirect:index";
    }

    @RequestMapping(value = "{path}")
    public String forward(@PathVariable String path){
        System.out.println("请求到" + path);
        return path;
    }
}
