package com.easyacg.storage.properties;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Cloudflare S3 存储策略配置
 *
 * @author brahma
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class R2Properties extends S3Properties {
    private String accountId;
}
