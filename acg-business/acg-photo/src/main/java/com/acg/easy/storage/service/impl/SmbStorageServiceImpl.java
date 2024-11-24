package com.acg.easy.storage.service.impl;

import com.acg.easy.core.entity.EasyacgException;
import com.acg.easy.storage.StorageModeEnum;
import com.acg.easy.storage.properties.SmbProperties;
import com.acg.easy.storage.service.StorageService;
import com.hierynomus.msfscc.fileinformation.FileIdBothDirectoryInformation;
import com.hierynomus.smbj.SMBClient;
import com.hierynomus.smbj.auth.AuthenticationContext;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.session.Session;
import com.hierynomus.smbj.share.DiskShare;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * SMB相关操作实现类
 *
 * @author brahma
 */
@Slf4j
@Service
public class SmbStorageServiceImpl implements StorageService<File> {
    @Resource
    private SmbProperties smbProperties;

    @Override
    public StorageModeEnum getStorage() {
        return StorageModeEnum.SMB;
    }

    @Override
    public List<File> listObjects() {
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
            throw EasyacgException.build("SMB连接失败: " + e.getMessage());
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

    @Override
    public void putObject(InputStream file, Path path) {

    }
}
