package demo.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @Description: TODO
 * @author: 01369674
 * @date: 2018/8/14
 */

@Controller
@RequestMapping("/file")
public class FileController {

    @RequestMapping("/uploadPage")
    public String uploadPage(){
        return "redirect:/uploadPage.html";
    }


    @RequestMapping("/upload")
    public String uploadThumb(@RequestParam("file") MultipartFile file) throws IOException {
        if(!file.isEmpty()){
            file.transferTo(new File("d:/temp/"+file.getOriginalFilename()));
            return "redirect:/success.html";
        }else{
            return "redirect:/fail.html";
        }
    }
}
