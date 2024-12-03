package com.easyacg.storage.mybatis;

import com.baomidou.mybatisplus.annotation.TableName;
import com.easyacg.core.mybatis.TableBaseDo;
import com.easyacg.storage.mybatis.enums.StorageModeEnum;
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
