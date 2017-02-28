/**
 * Created by TeasunKim on 2016-09-12.
 */

var __StatusCtrl = function ($interval, $scope, $http, store, $state, $uibModal, $rootScope, $filter, HOST) {
    var userObject = store.get('obj');
    $scope.loadingStyle = {'display': 'block'};
    $scope.quantity = 4;
    $scope.chart_view = 1;
    $scope.temp_series = ['온도', '습도'];
    $scope.sleep_series = ['수면시간',''];
    $scope.blood_series = ['혈압',''];

    $scope.a_name = userObject.a_name;
    $scope.colours = [{
        fillColor: '#B2EBF4',
        strokeColor: '#003399',
        highlightFill: 'rgba(47, 132, 71, 0.8)',
        highlightStroke: 'rgba(47, 132, 71, 0.8)',
        backgroundColor: '#803690'
    }];

    $scope.patientStatusListPost = function () {

        $http({
            method: 'POST', //방식
            url: HOST + "/web/api/getPatientStatusList", /* 통신할 URL */
            data: userObject, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        })
            .success(function (data, status, headers, config) {
                if (data) { //존재하지 않음,아이디 사용가능
                    $scope.patientStatusList = data;
                    console.log(data);
                    $scope.loadingStyle = {'display': 'none'};
                }
                else {
                    alert('환자가 없습니다.');
                }
            });
    };
    $scope.patientStatusListPost();

};

