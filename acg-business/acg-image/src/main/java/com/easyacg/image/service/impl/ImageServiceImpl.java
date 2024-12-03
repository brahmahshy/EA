package com.easyacg.image.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easyacg.image.mybatis.ImageDo;
import com.easyacg.image.mybatis.mapper.ImageMapper;
import com.easyacg.image.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author brahma
 */
@Slf4j
@Service
public class ImageServiceImpl extends ServiceImpl<ImageMapper, ImageDo> implements ImageService {}
