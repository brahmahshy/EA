package com.easyacg.image.entity.input;

import com.easyacg.core.validate.MustHasOne;
import lombok.Data;

/**
 * 通过存储策略读取图片
 *
 * @author brahma
 */
@Data
@MustHasOne({"id", "name"})
public class ReadByStorageBo {
    private String id;

    private String name;
}
