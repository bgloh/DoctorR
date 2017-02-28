/**
 * Created by TeasunKim on 2016-09-12.
 */

var __PatientManageCtrl = function ($interval, $scope, $http, store, $state, $uibModal, $rootScope, $filter, HOST) {
    var userObject = store.get('obj');
    $scope.quantity = 4;
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

    $scope.getHospitalName = function () {
        $http({
            method: 'POST', //방식
            url: HOST + "/web/api/getdoctorHospital", /* 통신할 URL */
            data: userObject, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        })
            .success(function (data, status, headers, config) {
                if (data) { //존재하지 않음,아이디 사용가능
                    $scope.a_hospital = data.msg;
                }
                else {
                    alert('해당병원 없습니다.');
                }
            });
    };

    $scope.allPatientListPost = function () {
        $scope.getHospitalName();
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


    $scope.addPatient = function (u_id) {
        var addPatientObject = {
            u_id : u_id,
            a_id : userObject.a_id
        };
        $http({
            method: 'POST', //방식
            url: HOST + "/web/api/addPatient", /* 통신할 URL */
            data: addPatientObject, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        })
            .success(function (data, status, headers, config) {
                if (data) { //존재하지 않음,아이디 사용가능
                    alert('등록 되었습니다.');
                    $scope.loadingStyle = {'display': 'none'};
                    $scope.allPatientListPost();
                }
                else {
                    alert('등록을 못하였습니다.');
                }
            });
    };
    $scope.delPatient = function (u_id) {
        var delPatientObject = {
            u_id : u_id,
            a_id : userObject.a_id
        };
        $http({
            method: 'POST', //방식
            url: HOST + "/web/api/delPatient", /* 통신할 URL */
            data: delPatientObject, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        })
            .success(function (data, status, headers, config) {
                if (data) { //존재하지 않음,아이디 사용가능
                    alert('등록 취소 되었습니다.');
                    $scope.loadingStyle = {'display': 'none'};
                    $scope.allPatientListPost();
                }
                else {
                    alert('등록 취소를 못하였습니다.');
                }
            });
    };
};

