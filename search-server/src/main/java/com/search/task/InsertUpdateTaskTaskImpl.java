package com.search.task;

import com.search.localservice.TaskLocalService;
import com.wolf.framework.task.Task;
import java.util.List;

/**
 *
 * @author aladdin
 */
public class InsertUpdateTaskTaskImpl implements Task {

    private final TaskLocalService taskLocalService;
    private final String source;
    private final List<String> sourceIdList;

    public InsertUpdateTaskTaskImpl(TaskLocalService taskLocalService, String source, List<String> sourceIdList) {
        this.taskLocalService = taskLocalService;
        this.source = source;
        this.sourceIdList = sourceIdList;
    }

    @Override
    public void doWhenRejected() {
    }

    @Override
    public void run() {
        this.taskLocalService.insertInfoTask(this.source, this.sourceIdList);
        this.taskLocalService.insertFollowTask(this.source, this.sourceIdList);
    }
}
