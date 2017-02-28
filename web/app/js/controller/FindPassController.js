/**
 * Created by TeasunKim on 2016-09-12.
 */


var __FindPassCtrl = function ($scope, $http, $state, HOST) {
    $scope.findPASS = [{
        e_mail: "", a_name: ""
    }];
    $scope.findPASSPost = function () {
        var findPASSObject = {
            e_mail: $scope.findPASS.e_mail,
            a_name: $scope.findPASS.a_name
        };
        $http({
            method: 'POST', //방식
            url: HOST + "/web/user/findPASS", /* 통신할 URL */
            data: findPASSObject, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        })
            .success(function (data, status, headers, config) {
                if (data.msg == 'false') {
                    alert('해당하는 비밀번호가 없습니다.')
                } else {
                    alert('메일로 비밀번호가 전송되었습니다.')
                    $state.go("re_login");

                }
            })
            .error(function (data, status, headers, config) {
                /* 서버와의 연결이 정상적이지 않을 때 처리 */
                console.log(status);
            });
    };
};

