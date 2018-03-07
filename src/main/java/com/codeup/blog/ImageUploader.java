/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.blog;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Service
public class ImageUploader {
    @Value("${uploads.folder}")
    private String uploadsFolder;

    public String upload(MultipartFile image) throws IOException {
        String filename = image.getOriginalFilename();
        String destinationPath = Paths.get(uploadsFolder(), filename).toString();
        image.transferTo(new File(destinationPath));
        return filename;
    }

    public byte[] read(String imageFilename) throws IOException {
        String imagePath = String.format("%s/%s", uploadsFolder(), imageFilename);

        return IOUtils.toByteArray(FileUtils.openInputStream(new File(imagePath)));
    }

    private String uploadsFolder() throws IOException {
        return String.format("%s/%s", new File(".").getCanonicalPath(), uploadsFolder);
    }
}
