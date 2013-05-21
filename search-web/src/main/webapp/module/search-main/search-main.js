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
        },
        searchEmployeeListener:{
            click:function (yy) {
                var mainPanel = yy.findInModule('main-content');
                var searchEmployeeWindow = mainPanel.openWindow({
                    class:'yy_hide',
                    key:'search-employee-window'
                });
                searchEmployeeWindow.setHeaderLabel('人才库');
                searchEmployeeWindow.loadModule('search-employee');
                if (searchEmployeeWindow.isVisible()) {
                    searchEmployeeWindow.bounceOut();
                } else {
                    searchEmployeeWindow.bounceIn();
                }
            }
        },
        inquireTagListener:{
            click:function (yy) {
                var mainPanel = yy.findInModule('main-content');
                var inquireTagWindow = mainPanel.openWindow({
                    class:'yy_hide',
                    key:'inquire-tag-window'
                });
                inquireTagWindow.setHeaderLabel('标签排行');
                inquireTagWindow.loadModule('search-tag');
                if (inquireTagWindow.isVisible()) {
                    inquireTagWindow.bounceOut();
                } else {
                    inquireTagWindow.bounceIn();
                }
            }
        }
    },
    messageListener:{
    }
});


