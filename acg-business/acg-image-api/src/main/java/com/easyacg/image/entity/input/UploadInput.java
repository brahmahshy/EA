package com.easyacg.image.entity.input;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 上传入参
 *
 * @author brahma
 */
@Getter
@Setter
public class UploadInput {
    /**
     * 存储策略名称</br>
     * 用于确认将文件保存到哪个存储策略中
     */
    @NotNull(message = "存储策略名称不能为空")
    private String storageName;

    /**
     * 保存路径
     */
    private String path;
}
