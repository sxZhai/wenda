package wenda.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

public class FileUtil {

    /**
     * 解码图片文件
     * @param imageContent 待解码的图片文件的字符串格式
     * @return 解码后图片文件的二进制内容
     */
    public static byte[] base64Decoding(String imageContent) {
        if(imageContent != null)
            return Base64.getDecoder().decode(imageContent);
        else return null;
    }

    /**
     * 将解码后的二进制内容写入文件中
     * @param path 写入的路径
     * @param imageContents 解码后的二进制内容
     */
    public static void writeFile(Path path, byte[] imageContent) {
        if(imageContent != null)
            try {
                Files.write(path, imageContent, StandardOpenOption.CREATE);
            } catch (IOException e) {
//                System.out.println("写入文件出错，path: " + path);
                System.out.println(e);
            }
    }

}
