package com.acg.easy.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 图片格式枚举
 *
 * @author brahma
 */
@Getter
@AllArgsConstructor
public enum ImageFormatEnum {
    JPG(".jpg", "JPEG图片"),
    JPEG(".jpeg", "JPEG图片"),
    PNG(".png", "PNG图片"),
    GIF(".gif", "GIF图片"),
    BMP(".bmp", "BMP图片"),
    WEBP(".webp", "WEBP图片"),
    HEIC(".heic", "HEIC图片"),
    HEIF(".heif", "HEIF图片"),
    RAW(".raw", "RAW图片"),
    TIFF(".tiff", "TIFF图片"),
    TIF(".tif", "TIF图片");

    /**
     * 文件扩展名（包含点号）
     */
    private final String extension;

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
        if (fileName == null || fileName.isEmpty()) {
            return false;
        }
        String lowerFileName = fileName.toLowerCase();
        return Arrays.stream(ImageFormatEnum.values())
                     .anyMatch(format -> lowerFileName.endsWith(format.getExtension()));
    }

    /**
     * 获取文件扩展名（不含点号）
     *
     * @param fileName 文件名
     * @return 扩展名
     */
    public static String getExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        String lowerFileName = fileName.toLowerCase();
        return Arrays.stream(ImageFormatEnum.values())
                     .filter(format -> lowerFileName.endsWith(format.getExtension()))
                     .findFirst()
                     .map(format -> format.getExtension().substring(1))
                     .orElse("");
    }
} 