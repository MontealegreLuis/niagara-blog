/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.blog;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ImageResizer {
    private static final int MAX_HEIGHT = 500;

    @Value("${uploads.folder}")
    private String imagesFolder;

    public void resize(String imageFilename) throws IOException, IM4JavaException, InterruptedException {
        String imagePath = String.format("%s/%s", imagesFolder, imageFilename);

        ConvertCmd command = new ConvertCmd();
        IMOperation resize = new IMOperation();
        resize.resize(null, MAX_HEIGHT);
        resize.addImage();
        resize.addImage();
        command.run(resize, imagePath, imagePath);
    }
}
