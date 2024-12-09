package com.easyacg.storage.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.FileUtil;
import com.easyacg.core.entity.EasyacgException;
import com.easyacg.storage.entity.output.FileInfoVo;
import com.easyacg.storage.model.StorageModeEnum;
import com.easyacg.storage.properties.SmbProperties;
import com.easyacg.storage.service.StorageService;
import com.hierynomus.msdtyp.AccessMask;
import com.hierynomus.msfscc.fileinformation.FileIdBothDirectoryInformation;
import com.hierynomus.msfscc.fileinformation.FileStandardInformation;
import com.hierynomus.mssmb2.SMB2CreateDisposition;
import com.hierynomus.mssmb2.SMB2ShareAccess;
import com.hierynomus.smbj.SMBClient;
import com.hierynomus.smbj.auth.AuthenticationContext;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.session.Session;
import com.hierynomus.smbj.share.DiskShare;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * SMB相关操作实现类
 *
 * @author brahma
 */
@Slf4j
@Service
public class SmbStorageServiceImpl implements StorageService {

    @Override
    public StorageModeEnum getStorage() {
        return StorageModeEnum.SMB;
    }

    @Override
    public List<FileInfoVo> listObjects() {
        SmbProperties smbProperties = new SmbProperties();
        List<FileInfoVo> fileInfos;

        try (SMBClient client = new SMBClient()) {
            log.info("正在创建SMB连接...");
            Connection connection = client.connect(smbProperties.getHost());
            log.info("已连接到服务器: {}", smbProperties.getHost());

            AuthenticationContext authContext = new AuthenticationContext(
                    smbProperties.getUsername(),
                                                                          smbProperties.getPassword().toCharArray(),
                                                                          null
            );
            Session session = connection.authenticate(authContext);
            log.info("认证成功");

            log.info("正在连接共享文件夹: {}", smbProperties.getShareName());
            try (DiskShare share = (DiskShare) session.connectShare(smbProperties.getShareName())) {
                String basePath = smbProperties.getBasePath();
                log.info("已连接到共享文件夹，开始访问文件路径：{}", basePath);

                fileInfos = listFiles(share, basePath);
            }
        } catch (IOException e) {
            log.error("SMB连接失败", e);
            throw EasyacgException.build("SMB连接失败: " + e.getMessage());
        }

        log.info("成功获取{}个文件信息", fileInfos.size());
        return fileInfos;
    }

    private List<FileInfoVo> listFiles(DiskShare share, String basePath) {
        List<FileInfoVo> fileInfos = new ArrayList<>();

        for (FileIdBothDirectoryInformation fileInfo : share.list(basePath)) {
            String fileName = fileInfo.getFileName();
            // 跳过 . 和 .. 目录
            if (".".equals(fileName) || "..".equals(fileName)) {
                continue;
            }

            FileInfoVo info = getFileInfo(share, basePath, fileInfo);
            if (info != null) {
                fileInfos.add(info);
                log.debug("成功获取文件信息: {}", fileName);
            }
        }

        return fileInfos;
    }

    private FileInfoVo getFileInfo(DiskShare share, String basePath, FileIdBothDirectoryInformation fileInfo) {
        String fileName = fileInfo.getFileName();
        String remotePath = basePath + "\\" + fileName;

        try {
            // 获取文件信息
            FileStandardInformation standardInfo = share.getFileInformation(remotePath).getStandardInformation();

            // 如果是目录则跳过
            if (standardInfo.isDirectory()) {
                return null;
            }

            // 获取文件流
            InputStream inputStream = share.openFile(
                    remotePath,
                    EnumSet.of(AccessMask.GENERIC_READ),
                    null,
                    SMB2ShareAccess.ALL,
                    SMB2CreateDisposition.FILE_OPEN,
                    null
            ).getInputStream();

            // 使用builder模式构建FileInfoVo对象
            return FileInfoVo.builder()
                    .fileName(fileName)
                    .filePath(remotePath)
                    .fileSize(standardInfo.getEndOfFile())
                    .inputStream(inputStream)
                    .lastModified(LocalDateTimeUtil.of(fileInfo.getLastWriteTime().toInstant()))
                    .fileType(FileUtil.extName(fileName))
                    .build();
        } catch (Exception e) {
            log.error("获取文件信息失败: {}", fileName, e);
            return null;
        }
    }

    @Override
    public void putObject(InputStream file, Path path) {

    }
}
