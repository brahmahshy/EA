package com.easyacg.image.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * 图片格式枚举
 *
 * @author brahma
 */
@Getter
@AllArgsConstructor
public enum ImageFormatEnum {
    JPG(".jpg", "image/jpeg", "JPEG图片"),
    JPEG(".jpeg", "image/jpeg", "JPEG图片"),
    PNG(".png", "image/png", "PNG图片"),
    GIF(".gif", "image/gif", "GIF图片"),
    BMP(".bmp", "image/bmp", "BMP图片"),
    WEBP(".webp", "image/webp", "WEBP图片"),
    HEIC(".heic", "image/heic", "HEIC图片"),
    HEIF(".heif", "image/heif", "HEIF图片"),
    RAW(".raw", "image/x-raw", "RAW图片"),
    TIFF(".tiff", "image/tiff", "TIFF图片"),
    TIF(".tif", "image/tiff", "TIF图片");

    /**
     * 文件扩展名（包含点号）
     */
    private final String extension;

    /**
     * MIME类型
     */
    private final String mimeType;

    /**
     * 描述
     */
    private final String description;

    /**
     * 判断文件是否为图片格式
     *
     * @param fileName 文件名
     * @return 是否为图片
     */
    public static boolean isImage(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            return false;
        }
        String lowerFileName = fileName.toLowerCase();
        return Arrays.stream(ImageFormatEnum.values())
                .anyMatch(format -> lowerFileName.endsWith(format.getExtension()));
    }

    /**
     * 根据MIME类型判断是否为图片
     *
     * @param mimeType MIME类型
     * @return 是否为图片
     */
    public static boolean isImageByMimeType(String mimeType) {
        if (StringUtils.isEmpty(mimeType)) {
            return false;
        }
        String lowerMimeType = mimeType.toLowerCase();
        return Arrays.stream(ImageFormatEnum.values()).anyMatch(format -> lowerMimeType.equals(format.getMimeType()));
    }
} 