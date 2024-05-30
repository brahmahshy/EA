package com.acg.easy.common.util;

import com.acg.easy.common.entity.BrahmaException;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@UtilityClass
public class FileUtil {
    public List<File> getFile(String filePath) {
        return getFile(new File(filePath));
    }

    public List<File> getFile(File file) {
        if (!file.exists()) throw new BrahmaException("文件或文件夹不存在！！！");

        List<File> fileList = new ArrayList<>();

        for (File listFile : Objects.requireNonNull(file.listFiles())) {
            if (listFile.isDirectory()) {
                fileList.addAll(getFile(listFile));
                continue;
            }

            fileList.add(listFile);
        }

        return fileList;
    }

    public static void main(String[] args) {
        System.out.println(getFile("G:\\活动记录"));
    }
}
