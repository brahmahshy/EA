package com.brahma.service.impl;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import com.brahma.entity.BrahmaException;
import com.brahma.entity.PhotoDo;
import com.brahma.mapper.PhotoMapper;
import com.brahma.service.PhotoService;
import com.brahma.util.SnowIdWorker;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class PhotoServiceImpl implements PhotoService {
    @Resource
    private PhotoMapper photoMapper;

    @Resource
    private SnowIdWorker snowIdWorker;

    @Override
    @Transactional
    public void readPhoto(List<File> files) {
        int size = files.size();
        List<CompletableFuture<Integer>> tasks = new ArrayList<>(size);
        files.forEach(file -> tasks.add(CompletableFuture.supplyAsync(() -> {
            PhotoDo photoDo = new PhotoDo();
            photoDo.setName(file.getName());
            photoDo.setAliseName("");
            photoDo.setFilePath(file.getAbsolutePath());
            photoDo.setShootingStartTime(LocalDateTime.now());
            photoDo.setShootingEndTime(LocalDateTime.now());
            photoDo.setPhotographerId(0L);
            photoDo.setModelId(0L);
            photoDo.setLocation("");
            log.debug("insert photo {}", photoDo);
            return photoMapper.insert(photoDo);
        })));
//        CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0]));
        try {
            long count = 0L;
            for (CompletableFuture<Integer> task : tasks) {
                Integer num = task.get();
                if (num != null && num != 0) {
                    count++;
                }
            }
            if (count < size) {
                throw new BrahmaException("操作失败");
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new BrahmaException(e);
        }
    }
}
