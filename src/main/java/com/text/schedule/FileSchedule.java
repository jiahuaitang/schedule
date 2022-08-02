package com.text.schedule;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class FileSchedule {

    @Value("${config.filePath1}")
    private String filePath1;
    @Value("${config.filePath2}")
    private String filePath2;
    @Value("${config.fileName}")
    private String fileName;
    @Value("${config.mac}")
    private String mac;
    @Value("${config.win}")
    private String win;

    @Scheduled(fixedDelay = 1000 * 60 * 60 * 3)
    public void copyFile() throws IOException {
        FileUtil fileUtil = new FileUtil(filePath1,filePath2,fileName,mac,win);
        fileUtil.run();
    }

}
