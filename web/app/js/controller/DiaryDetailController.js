/**
 * Created by TeasunKim on 2016-09-12.
 */

var __DiaryDetailCtrl = function ($interval, $scope, $http, store, $state, $uibModal, $rootScope, $filter, HOST, Excel, $timeout) {
    var userObject = store.get('obj');
    $scope.list_id = $rootScope.list_id;
    $scope.loadingStyle = {'display': 'block'};
    var diaryObject = {
        list_id: $scope.list_id
    };

    $scope.dateTime = function(date) {
        date = date.toString();
        d = (date.split(' ')[0]);
        h = (date.split(' ')[1].split(':')[0]);
        m = (date.split(' ')[1].split(':')[1].split(':')[0]);
        return d+"\n["+h+":"+m+"]";
    };

    $scope.diaryDetailPost = function () {
        $http({
            method: 'POST', //방식
            url: HOST + "/web/diary/getDiary", /* 통신할 URL */
            data: diaryObject, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        })
            .success(function (data, status, headers, config) {
                if (data) { //존재하지 않음,아이디 사용가능
                    $scope.breakfast = data.c_breakfast;
                    $scope.lunch = data.c_lunch;
                    $scope.dinner = data.c_dinner;
                    $scope.temperature = data.c_temperature;
                    $scope.humid = data.c_humid;
                    $scope.sleepTime = data.c_sleepTime;
                    $scope.bloodPressure = data.c_bloodPressure;
                    $scope.drinking = data.c_drinking;

                    $scope.loadingStyle = {'display': 'none'};
                }
                else {

                }
            });
    };
    $scope.diaryDetailPost();
    $scope.a_name = userObject.a_name;

    $scope.exportToExcel=function(tableId){ // ex: '#my-table'
        var exportHref=Excel.tableToExcel(tableId,'sheet name');
        $timeout(function(){location.href=exportHref;},100); // trigger download
    };
};

