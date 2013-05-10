/**
 * User: aladdin
 * Date: 12/21/12
 * Time: 10:46 AM
 */
$.yyLoadListener('search-employee', {
    finishedListener:{
        initListener:function (yy) {
            var searchEmployeeList = yy.findInModule('search-employee-list');
            searchEmployeeList.init({
                key:'empId',
                dataToHtml:function (data) {
                    var result = '<div class="employee_info"><div>' + data.nickName + '</div>' +
                        '<div>' + data.gender + '</div>' +
                        '<div>' + data.empName + '</div>' +
                        '<div>' + data.location + '</div>' +
                        '<div>' + data.lastUpdateTime + '</div></div>' +
                        '<div class="employee_tag">' + data.tag + '</div>';
                    return result;
                }
            });
            searchEmployeeList.setPageSize(30);
        }
    },
    eventListener:{
        inquireEmployeeListener:{
            click:function (yy) {
                var inquireForm = yy.findInModule('inquire-employee-from');
                var searchEmployeeList = yy.findInModule('search-employee-list');
                searchEmployeeList.clear();
                var msg = inquireForm.getData();
                msg.act = 'INQUIRE_EMPLOYEE';
                msg.pageSize = searchEmployeeList.getPageSize();
                inquireForm.sendMessage(msg);
            }
        },
        searchEmployeeListEventListener:{
            scrollHalf:function (yy) {
                var pageIndex = yy.getPageIndex();
                if (yy.getPageNum() > pageIndex) {
                    pageIndex++;
                    var inquireForm = yy.findInModule('inquire-employee-from');
                    var msg = inquireForm.getData();
                    msg.act = 'INQUIRE_EMPLOYEE';
                    msg.pageSize = yy.getPageSize();
                    msg.pageIndex = pageIndex;
                    yy.sendMessage(msg);
                }
            }
        }
    },
    messageListener:{
        employeeListMessageListener:{
            INQUIRE_EMPLOYEE:function (yy, message) {
                if (message.flag === 'SUCCESS') {
                    var searchEmployeeList = yy.findInModule('search-employee-list');
                    searchEmployeeList.setPageIndex(message.pageIndex);
                    searchEmployeeList.setPageNum(message.pageNum);
                    searchEmployeeList.setPageSize(message.pageSize);
                    searchEmployeeList.setPageTotal(message.pageTotal);
                    searchEmployeeList.loadData(message.data);
                }
            }
        }
    }
});


