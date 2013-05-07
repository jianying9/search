/**
 * User: aladdin
 * Date: 12/21/12
 * Time: 10:46 AM
 */
$.yyLoadListener('search-task-insert', {
    finishedListener:{
    },
    eventListener:{
        insertSearchTaskListener:{
            click:function (yy) {
                var insertForm = yy.findInModule('insert-search-task-from');
                var msg = insertForm.getData();
                msg.act = 'INSERT_SEARCH_TASK';
                insertForm.sendMessage(msg);
                insertForm.loadData({tag:''});
            }
        }
    },
    messageListener:{
        insertSearchTaskMessageListener:{
            INSERT_SEARCH_TASK:function(yy, message) {
                var errorPanel = yy.findInModule('insert-search-task-error');
                if (message.flag === 'SUCCESS') {
                    errorPanel.hide();
                    yy.window.bounceOut();
                } else {
                    errorPanel.setLabel(message.flag);
                    errorPanel.show();
                }
            }
        }
    }
});


