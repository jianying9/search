/**
 * User: aladdin
 * Date: 12/21/12
 * Time: 10:46 AM
 */
$.yyLoadListener('search-tag', {
    finishedListener:{
        initListener:function (yy) {
            var searchTagList = yy.findInModule('search-tag-list');
            searchTagList.init({
                key:'tag',
                dataToHtml:function (data) {
                    var result = '<div>' + data.tag + '</div>' +
                        '<div>' + data.total + '</div>' +
                        '<div>' + data.lastUpdateTime + '</div>';
                    return result;
                }
            });
            searchTagList.setPageSize(30);
            searchTagList.sendMessage({act:'INQUIRE_TAG', pageSize:30});
        }
    },
    eventListener:{
        tagListEventListener:{
            scrollBottom:function (yy) {
                var pageIndex = yy.getPageIndex();
                if (yy.getPageNum() > pageIndex) {
                    pageIndex++;
                    var pageSize = yy.getPageSize();
                    yy.sendMessage({act:'INQUIRE_TAG', pageSize:pageSize, pageIndex:pageIndex});
                }
            }
        }
    },
    messageListener:{
        tagListMessageListener:{
            INQUIRE_TAG:function (yy, message) {
                if (message.flag === 'SUCCESS') {
                    var searchtAGList = yy.findInModule('search-tag-list');
                    searchtAGList.setPageIndex(message.pageIndex);
                    searchtAGList.setPageNum(message.pageNum);
                    searchtAGList.setPageSize(message.pageSize);
                    searchtAGList.setPageTotal(message.pageTotal);
                    searchtAGList.loadData(message.data);
                }
            }
        }
    }
});


