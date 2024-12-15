package com.easyacg.image.entity.input;

import com.easyacg.core.contents.enums.BooleanEnum;
import com.easyacg.core.validate.IsEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

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
     * 是否需要压缩
     */
    @IsEnum(enumClass = BooleanEnum.class)
    private String isNeedLossy;

    /**
     * 保存路径
     */
    private String path;

    public String getIsNeedLossy() {
        return StringUtils.isBlank(isNeedLossy) ? "true" : isNeedLossy;
    }
}
