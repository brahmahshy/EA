package com.easyacg.storage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easyacg.storage.mybatis.StorageDo;
import com.easyacg.storage.mybatis.mapper.StorageMapper;
import com.easyacg.storage.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 存储策略服务 —— 数据交互实现类
 *
 * @author brahma
 */
@Slf4j
@Service
public class StorageServiceImpl extends ServiceImpl<StorageMapper, StorageDo> implements StorageService {}
