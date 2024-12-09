package com.easyacg.storage.entity.properties;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

/**
 * Cloudflare S3 存储策略配置
 *
 * @author brahma
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class R2Properties extends S3Properties {
    private String accountId;

    @Override
    public void init() {
        if (StringUtils.isEmpty(this.getEndpoint())) {
            this.setEndpoint(String.format("https://%s.r2.cloudflarestorage.com", this.accountId));
        }
    }
}
