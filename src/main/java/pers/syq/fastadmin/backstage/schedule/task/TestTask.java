package pers.syq.fastadmin.backstage.schedule.task;

import org.springframework.stereotype.Component;

@Component("testTask")
public class TestTask implements ITask{
    @Override
    public void run(String params) {
        System.out.println("testTask:"+params);
    }
}
