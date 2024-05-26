package com.brahma.photo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.brahma.photo.entity.PhotoDo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PhotoMapper extends BaseMapper<PhotoDo> {
}
