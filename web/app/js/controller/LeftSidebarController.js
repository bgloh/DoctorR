/**
 * Created by TeasunKim on 2016-09-12.
 */


var __LeftSidebarCtrl = function ($interval, $scope, $http, store, $state, $uibModal, $rootScope, $filter, HOST) {
    var userObject = store.get('obj');
    $scope.getClass = function (path) {
        return ($state.$current.toString()=== path) ? 'active' : '';
    };

    $scope.loading = true;
    $scope.a_name = userObject.a_name;
    $scope.loadingStyle = {'display': 'block'};

    $scope.dateTime = function(date) {
        date = date.toString();
        d = (date.split(' ')[0]);
        d = (d.split('-')[1] + "월" + d.split('-')[2] + "일");
        h = (date.split(' ')[1].split(':')[0]);
        m = (date.split(' ')[1].split(':')[1].split(':')[0]);
        return d+"\n["+h+":"+m+"]";
    };


    $scope.allPatientListPost = function () {

        $http({
            method: 'POST', //방식
            url: HOST + "/web/api/getAllPatientList", /* 통신할 URL */
            data: userObject, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        })
            .success(function (data, status, headers, config) {
                if (data) { //존재하지 않음,아이디 사용가능
                    console.log(data);
                    $scope.allPatientList = data;
                    $scope.loadingStyle = {'display': 'none'};
                }
                else {
                    alert('환자가 없습니다.');
                }
            });
    };
    $scope.allPatientListPost();



};
