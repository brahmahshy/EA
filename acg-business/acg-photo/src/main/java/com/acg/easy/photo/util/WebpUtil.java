package com.acg.easy.photo.util;

import com.luciad.imageio.webp.WebPWriteParam;
import lombok.experimental.UtilityClass;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@UtilityClass
public class WebpUtil {
    private void convertJpegToWebpWithLossyCompression() {
        try {
            BufferedImage image = ImageIO.read(new File("C:\\Users\\admin\\Desktop\\pbd00106jp-7.jpg"));
            ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/webp").next();
            WebPWriteParam writeParam = new WebPWriteParam(writer.getLocale());
            writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            // 设置有损压缩
            writeParam.setCompressionType(writeParam.getCompressionTypes()[WebPWriteParam.LOSSY_COMPRESSION]);
            //设置 75% 的质量. 设置范围 0-1，公认比较均衡的压缩比，兼顾图片质量和图片大小
            writeParam.setCompressionQuality(0.75f);

            // Save the image
            writer.setOutput(new FileImageOutputStream(new File("C:\\Users\\admin\\Desktop\\sample.webp")));
            writer.write(null, new IIOImage(image, null, null), writeParam);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
