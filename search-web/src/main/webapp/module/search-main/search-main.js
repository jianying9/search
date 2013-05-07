/**
 * User: zoe
 * Date: 12/21/12
 * Time: 10:46 AM
 */
$.yyLoadListener('search-main', {
    finishedListener:{
        initListener:function (yy) {

        }
    },
    eventListener:{
        searchTaskListener:{
            click:function (yy) {
                var mainPanel = yy.findInModule('main-content');
                var searchTaskWindow = mainPanel.openWindow({
                    class:'yy_hide',
                    key:'search-task-window'
                });
                searchTaskWindow.setHeaderLabel('搜索任务');
                searchTaskWindow.loadModule('search-task');
                if (searchTaskWindow.isVisible()) {
                    searchTaskWindow.bounceOut();
                } else {
                    searchTaskWindow.bounceIn();
                }
            }
        }
    },
    messageListener:{
    }
});


