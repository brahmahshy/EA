package com.acg.easy.photo.util;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class MetadataUtil {
    public static void getImageMetaData() throws ImageProcessingException, IOException {
        File file = new File("");
        Metadata metadata = ImageMetadataReader.readMetadata(file);
        Iterable<Directory> directories = metadata.getDirectories();
        for (Directory directory : directories) {
            Collection<Tag> tags = directory.getTags();
            for (Tag tag : tags) {
                System.out.println(tag);
            }
        }
    }
}
