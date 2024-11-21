package com.acg.easy.core.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 只存在 id 入参的 bo 对象
 * <p>
 * 可用于 仅有 id 入参的 接口
 * 和 需要 id 作为入参的 接口
 */
@Getter
@Setter
@Schema(description = "基础bo，仅有id入参")
public class BaseIdBo {
    @NotNull
    @Schema(description = "用户id")
    private Long id;
}
