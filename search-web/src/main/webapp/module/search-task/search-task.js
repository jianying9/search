/**
 * User: aladdin
 * Date: 12/21/12
 * Time: 10:46 AM
 */
$.yyLoadListener('search-task', {
    finishedListener:{
        initListener:function (yy) {
            var searchTaskList = yy.findInModule('search-task-list');
            searchTaskList.init({
                key:'taskId',
                dataToHtml:function (data) {
                    var state = '...';
                    switch(data.state) {
                        case 1:
                            state = '抓取中';
                            break;
                        case 2:
                            state = '解析中';
                            break;
                        case 0:
                            state = '完成';
                            break;
                        case 199:
                            state = '爬虫异常';
                            break;
                        case 299:
                            state = '解析异常';
                            break;
                    }
                    var result = '<div class="task_tag">' + data.context.tag +'</div>' +
                        '<div class="task_location">' + data.context.location + '</div>' +
                        '<div class="task_source">' + data.source +'</div>' +
                        '<div class="task_state">' + state + '</div>' +
                        '<div class="task_lastUpdateTime">' + data.lastUpdateTime + '</div>';
                    return result;
                }
            });
            searchTaskList.setPageSize(30);
            yy.sendMessage({act:'INQUIRE_SEARCH_TASK',pageSize:searchTaskList.getPageSize()});
        }
    },
    eventListener:{
        insertSearchTaskListener:{
            click:function (yy) {
                var insertSearchTaskWindow = yy.group.openWindow({
                    class:'yy_hide insert_search_task_window',
                    key:'insert-search-task-window'
                });
                insertSearchTaskWindow.setHeaderLabel('新增搜索任务');
                insertSearchTaskWindow.loadModule('search-task-insert');
                if (insertSearchTaskWindow.isVisible()) {
                    insertSearchTaskWindow.bounceOut();
                } else {
                    insertSearchTaskWindow.bounceIn();
                }
            }
        },
        searchTaskListEventListener:{
            scrollHalf:function (yy) {
                var pageIndex = yy.getPageIndex();
                if(yy.getPageNum() > pageIndex) {
                    pageIndex++;
                    yy.sendMessage({act:'INQUIRE_SEARCH_TASK',pageSize:yy.getPageSize(), pageIndex:pageIndex});
                }
            }
        }
    },
    messageListener:{
        taskListMessageListener:{
            INQUIRE_SEARCH_TASK:function(yy, message) {
                if (message.flag === 'SUCCESS') {
                    var searchTaskList = yy.findInModule('search-task-list');
                    searchTaskList.setPageIndex(message.pageIndex);
                    searchTaskList.setPageNum(message.pageNum);
                    searchTaskList.setPageSize(message.pageSize);
                    searchTaskList.setPageTotal(message.pageTotal);
                    searchTaskList.loadData(message.data);
                }
            },
            INSERT_SEARCH_TASK:function(yy, message) {
                if (message.flag === 'SUCCESS') {
                    var searchTaskList = yy.findInModule('search-task-list');
                    searchTaskList.addItemDataFirst(message.data);
                }
            }
        }
    }
});


