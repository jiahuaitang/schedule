package com.text.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
public class DateSchedule {

    @Value("${config.endTime}")
    private String endTime;

    @Scheduled(fixedDelay = 1000 * 10)
    public void copyFile() throws IOException, ParseException {

        Date date = new Date();
        String time = endTime;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = sdf.parse(time);

        String format = sdf.format(date);
        Date parse1 = sdf.parse(format);

        if(parse1.compareTo(parse)>=0){
            log.info("系统停止时间:{}",dd.format(date));
            System.exit(0);
        }
    }

}
