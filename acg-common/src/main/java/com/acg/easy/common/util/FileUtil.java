package com.acg.easy.common.util;

import com.acg.easy.common.config.StorageProperties.LocalProperties;
import com.acg.easy.common.config.StorageProperties.SmbProperties;
import com.acg.easy.common.entity.BrahmaException;
import com.acg.easy.common.enums.ImageFormatEnum;
import com.hierynomus.msfscc.fileinformation.FileIdBothDirectoryInformation;
import com.hierynomus.smbj.SMBClient;
import com.hierynomus.smbj.auth.AuthenticationContext;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.session.Session;
import com.hierynomus.smbj.share.DiskShare;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@UtilityClass
public class FileUtil {
    public static List<File> getFile(LocalProperties local) {
        return getFile(local.getPath());
    }

    public List<File> getFile(String filePath) {
        return getFile(new File(filePath));
    }

    public List<File> getFile(File file) {
        if (!file.exists()) {
            throw new BrahmaException("文件或文件夹不存在！！！");
        }

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

    public List<File> getSmbFiles(SmbProperties smbProperties) {
        List<File> fileList;

        try (SMBClient client = new SMBClient()) {
            log.info("正在创建SMB连接...");
            Connection connection = client.connect(smbProperties.getHost());
            log.info("已连接到服务器: {}", smbProperties.getHost());

            AuthenticationContext authContext =
                    new AuthenticationContext(smbProperties.getUsername(), smbProperties.getPassword().toCharArray(),
                                              null);
            Session session = connection.authenticate(authContext);
            log.info("认证成功");

            log.info("正在连接共享文件夹: {}", smbProperties.getShareName());
            try (DiskShare share = (DiskShare) session.connectShare(smbProperties.getShareName())) {
                log.info("已连接到共享文件夹，开始访问文件路径：{}", smbProperties.getBasePath());
                fileList = listSmbFiles(share, smbProperties.getBasePath());
            }
        } catch (IOException e) {
            log.error("SMB连接失败", e);
            throw new BrahmaException("SMB连接失败: " + e.getMessage());
        }

        return fileList;
    }

    private List<File> listSmbFiles(DiskShare share, String path) {
        List<File> fileList = new ArrayList<>();

        for (FileIdBothDirectoryInformation file : share.list(path)) {
            String fileName = file.getFileName();
            // 跳过 . 和 .. 目录
            if (".".equals(fileName) || "..".equals(fileName)) {
                continue;
            }

            String fullPath = path + "\\" + fileName;
            if (share.getFileInformation(fullPath).getStandardInformation().isDirectory()) {
                fileList.addAll(listSmbFiles(share, fullPath));
            } else {
                File localFile = new File(fileName);
                fileList.add(localFile);
            }
        }

        return fileList;
    }

    /**
     * 过滤出图片文件
     *
     * @param files 文件列表
     * @return 图片文件列表
     */
    public List<File> filterImageFiles(List<File> files) {
        if (files == null || files.isEmpty()) {
            return new ArrayList<>();
        }
        return files.stream().filter(file -> ImageFormatEnum.isImage(file.getName())).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        SmbProperties smbProperties = new SmbProperties();
        smbProperties.setHost("Easyacg");
        smbProperties.setShareName("personal_folder");
        smbProperties.setUsername("brahma");
        smbProperties.setPassword("Sdfqwer@1");
        smbProperties.setBasePath("\\Photos\\MobileBackup\\vivo X Flod3 Pro\\2024\\07");
        List<File> smbFiles = getSmbFiles(smbProperties);
        smbFiles.stream().map(File::getAbsolutePath).forEach(System.out::println);
        System.out.println(smbFiles.size());
    }
}
