package com.easyacg.storage.entity.output;

import com.easyacg.storage.entity.properties.StorageProperties;
import com.easyacg.storage.model.Storage;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 存储策略Vo
 *
 * @author brahma
 */
@Data
public class StorageVo {
    private String id;

    private String name;

    private StorageProperties properties;

    private String remark;

    public static StorageVo trans(Storage storage) {
        StorageVo storageVo = new StorageVo();
        storageVo.setId(String.valueOf(storage.getId()));
        storageVo.setName(storage.getName());
        storageVo.setProperties(storage.getProperties());
        storageVo.setRemark(storage.getRemark());
        return storageVo;
    }

    public static List<StorageVo> transList(List<Storage> storageList) {
        if (CollectionUtils.isEmpty(storageList)) {
            return Collections.emptyList();
        }

        return storageList.stream().map(StorageVo::trans).collect(Collectors.toList());
    }
}
