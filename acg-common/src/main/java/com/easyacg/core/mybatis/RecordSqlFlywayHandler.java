package com.easyacg.core.mybatis;

import com.easyacg.core.entity.EasyacgException;
import lombok.extern.slf4j.Slf4j;
import org.dromara.autotable.core.AutoTableGlobalConfig;
import org.dromara.autotable.core.config.PropertyConfig;
import org.dromara.autotable.core.recordsql.AutoTableExecuteSqlLog;
import org.dromara.autotable.core.recordsql.RecordSqlFileHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 自定义sql记录，文件名以Flyway方式生成
 *
 * @author brahma
 */
@Slf4j
@Component
public class RecordSqlFlywayHandler extends RecordSqlFileHandler {
    /**
     * 判断是否是第一次执行
     */
    private static boolean IS_FIRST = true;

    @Override
    protected Path getFilePath(AutoTableExecuteSqlLog autoTableExecuteSqlLog) {
        log.info("记录表 {} 的创建SQL到日志中", autoTableExecuteSqlLog.getTableName());
        PropertyConfig.RecordSqlProperties recordSql = AutoTableGlobalConfig.getAutoTableProperties().getRecordSql();
        Path path = Paths.get(".sql", "schema-" + recordSql.getVersion() + ".sql");
        if (IS_FIRST && Files.exists(path)) {
            // 第一次执行，删除旧文件
            try {
                Files.delete(path);
                IS_FIRST = false;
            } catch (IOException e) {
                throw EasyacgException.build(e.getMessage(), e);
            }
        }
        return path;
    }
}