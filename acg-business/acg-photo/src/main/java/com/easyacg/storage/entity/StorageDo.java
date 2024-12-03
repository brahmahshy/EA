package com.easyacg.storage.entity;

import com.easyacg.core.entity.TableBaseDo;
import com.easyacg.storage.StorageModeEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 存储策略表
 *
 * @author admin
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "x_storage", schema = "easyacg")
public class StorageDo extends TableBaseDo {
    /**
     * 存储策略名称
     */
    private String name;

    /**
     * 存储策略类型
     */
    private StorageModeEnum mode;

    /**
     * 存储策略配置
     */
    private String disposition;

    /**
     * 备注
     */
    private String remark;
}
