/**
 * Created by TeasunKim on 2016-09-12.
 */

var __PatientListCtrl = function ($interval, $scope, $http, store, $state, $uibModal, $rootScope, $filter, HOST, Excel) {
    var userObject = store.get('obj');
    $scope.quantity = 4;
    $scope.numDiary = 0;
    $scope.loading = true;
    $scope.a_name = userObject.a_name;
    $scope.loadingStyle = {'display': 'block'};

    $scope.dateTime = function(date) {
        date = date.toString();
        d = (date.split(' ')[0]);
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
                    $scope.allPatientList = data;
                    $scope.loadingStyle = {'display': 'none'};
                }
                else {
                    alert('환자가 없습니다.');
                }
            });
    };
    $scope.allPatientListPost();

    $scope.hospitalize = function(u_id){

        var requestObject = {
            u_id : u_id
        };

        $http({
            method: 'POST', //방식
            url: HOST + "/web/api/hospitalize", /* 통신할 URL */
            data: requestObject, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        })
            .success(function (data, status, headers, config) {
                if (data) { //존재하지 않음,아이디 사용가능
                    alert('환자가 재입원처리 되었습니다.');
                    $state.go($state.current, {}, {reload: true});
                    $scope.allPatientListPost();
                    $scope.loadingStyle = {'display': 'none'};
                }
                else {
                    alert('연결상태에 오류가 있습니다.');
                }
            });
    };

    $scope.discharged = function(u_id){

        var requestObject = {
            u_id : u_id
        };

        $http({
            method: 'POST', //방식
            url: HOST + "/web/api/discharged", /* 통신할 URL */
            data: requestObject, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        })
            .success(function (data, status, headers, config) {
                if (data) { //존재하지 않음,아이디 사용가능
                    alert('환자가 퇴원처리 되었습니다.');
                    $state.go($state.current, {}, {reload: true});
                    $scope.allPatientListPost();
                    $scope.loadingStyle = {'display': 'none'};
                }
                else {
                    alert('연결상태에 오류가 있습니다.');
                }
            });
    };
};

