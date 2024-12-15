package com.easyacg.image.util;

import com.easyacg.image.constant.ImageFormatEnum;
import com.luciad.imageio.webp.WebPWriteParam;
import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * WebP工具类
 *
 * @author brahma
 */
@UtilityClass
public class WebpUtil {
    private static final String WEBP_FILE_SUFFIX = ImageFormatEnum.WEBP.getExtension();

    public Path convertJpegToWebpWithLossyCompression(MultipartFile imageFile) throws IOException {
        Path tempFile = null;
        try {
            tempFile = Files.createTempFile("temp", imageFile.getOriginalFilename());
            imageFile.transferTo(tempFile);
            return convertJpegToWebpWithLossyCompression(tempFile.toFile());
        } finally {
            if (tempFile != null) {
                Files.delete(tempFile);
            }
        }
    }

    public Path convertJpegToWebpWithLossyCompression(File imageFile) throws IOException {
        BufferedImage image = ImageIO.read(imageFile);
        ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/webp").next();
        WebPWriteParam writeParam = new WebPWriteParam(writer.getLocale());
        writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        // 设置有损压缩
        writeParam.setCompressionType(writeParam.getCompressionTypes()[WebPWriteParam.LOSSY_COMPRESSION]);
        //设置 75% 的质量. 设置范围 0-1，公认比较均衡的压缩比，兼顾图片质量和图片大小
        writeParam.setCompressionQuality(0.75f);

        // Save the image
        Path webpFile = Files.createTempFile("", WEBP_FILE_SUFFIX);
        writer.setOutput(new FileImageOutputStream(webpFile.toFile()));
        writer.write(null, new IIOImage(image, null, null), writeParam);
        return webpFile;
    }
}
