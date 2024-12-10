package com.easyacg.controller.storage;

import com.easyacg.core.entity.ResponseVo;
import com.easyacg.storage.entity.input.CreateStorageBo;
import com.easyacg.storage.entity.input.UpdateStorageBo;
import com.easyacg.storage.model.Storage;
import com.easyacg.storage.service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 存储策略Controller
 *
 * @author brahma
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/storage")
public class StorageController {
    @Resource
    private StorageService storageService;

    /**
     * 创建存储策略
     *
     * @return 是否成功
     */
    @PostMapping("/create")
    @Operation(description = "创建存储策略")
    public ResponseVo<Void> create(@RequestBody CreateStorageBo storageBo) {
        storageService.createStorage(storageBo);
        return ResponseVo.success();
    }

    /**
     * 更新存储策略
     *
     * @return 是否成功
     */
    @PostMapping("/update")
    @Operation(description = "更新存储策略")
    public ResponseVo<Void> update(@RequestBody UpdateStorageBo storageBo) {
        storageService.updateStorage(storageBo);
        return ResponseVo.success();
    }

    /**
     * 删除存储策略
     *
     * @param name 存储策略名称
     * @return 是否删除成功
     */
    @DeleteMapping("/delete/{name}")
    @Operation(description = "删除存储策略")
    public ResponseVo<Void> delete(@PathVariable String name) {
        storageService.deleteByName(name);
        return ResponseVo.success();
    }

    /**
     * 获取所有存储策略
     *
     * @return 存储策略列表
     */
    @GetMapping("/list")
    @Operation(description = "获取所有存储策略")
    public ResponseVo<List<Storage>> list() {
        List<Storage> storageList = storageService.getAllStorage();
        return ResponseVo.success(storageList);
    }
}
