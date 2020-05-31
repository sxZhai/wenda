package wenda.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;
import wenda.model.PostImage;
import wenda.utils.FileUtil;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/image")
public class ImageController {

    @GetMapping
    public String getAll(HttpServletRequest request){
        return "llddllll";
    }

    @PostMapping
    public String addOne(HttpServletRequest request, @RequestBody PostImage newImage){

        // 获取图片的base64串
        String imageContent = newImage.getImageBase64();
        // 获取图片名
        String imageName = newImage.getName();

        System.out.println("获取图片，name: " + newImage.getName());

        // 加时间戳
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        // 拼接文件名
        imageName = timeStamp + "_" + imageName;

        // 获取文件路径
        Path imagePath = Paths.get(imageName);
        // 保存图片
        FileUtil.writeFile(imagePath,FileUtil.base64Decoding(imageContent));

        System.out.println("收到图片，path: " + imagePath);

        // 返回信息
        JSONObject res = new JSONObject();
        res.put("msg", "GET图片"+imageName);

        // 返回位置信息JSON
        return res.toString();
    }
}
