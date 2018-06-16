package com.gszx.upload.controller;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by macleo on 16/06/2018.
 */
@RestController
@EnableAutoConfiguration
public class UploadController {
    private Logger log = Logger.class.newInstance();
    /*
    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "./upload";

    @GetMapping("/")
    public String index() {
        return "upload";
    }

    @PostMapping("/upload") // //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }
*/
    @Value("${file.upload.dir}")
    private String path;

    /**
     * 实现文件上传
     * */
    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
    public Map<String,Object> fileUpload(@RequestParam("file") MultipartFile file){
        Map<String,Object> map = new HashMap<String, Object>();
        int no = 0;
        String msg = "上传失败！";

        if(!file.isEmpty()){
            String fileName = file.getOriginalFilename();

            File dest = new File(path + "/" + fileName);

            if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
                dest.getParentFile().mkdir();
            }

            try {
                file.transferTo(dest); //保存文件
                no = 1;
                msg = "上传成功！";

            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        map.put("no",no);
        map.put("msg", msg);

        return map;
    }
}
