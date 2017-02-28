/**
 * Created by TeasunKim on 2016-09-12.
 */


var __JoinCtrl = function ($scope, $http, $state, HOST) {
    var checkedId = "";
    $scope.join = [{
        e_mail: "", a_password: "", password_ck: "", a_name: "", a_phone: "", a_hospital: ""
    }];

    $scope.idCheck = function (id) {
        if(!id) alert("알맞은 이메일을 입력하세요.");
        else{
        $http({
            method: 'POST', //방식
            url: HOST + "/web/user/checkID", /* 통신할 URL */
            data: {e_mail: id}, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        })
            .success(function (data, status, headers, config) {
                    if (data) { //존재하지 않음,아이디 사용가능
                        checkedId = id;
                        alert('"' + id + '"' + '는 사용 가능합니다.');
                        /* 맞음 */
                    }
                    else {
                        alert('"' + id + '"' + '는 사용 불가능합니다.');
                            $scope.join.e_mail = "";
                        console.log('아이디 사용 불가');
                        /* 틀림 */
                    }
            })
        }
    };

    $scope.joinPost = function () {
        var joinObject = {
            e_mail: $scope.join.e_mail,
            a_password: $scope.join.a_password,
            a_name: $scope.join.a_name,
            a_phone: $scope.join.a_phone,
            a_hospital: $scope.join.a_hospital
        };


        if (checkedId != joinObject.e_mail) {
            alert('아이디를 중복 체크 해주세요!!');
        }
        else {

            if ($scope.join.a_password != $scope.join.password_ck) {
                alert('비밀번호가 틀립니다.');
                $scope.join.a_password = "";
                $scope.join.a_passwordck = "";
            }
            else {
                $http({
                    method: 'POST', //방식
                    url: HOST + "/web/user/join", /* 통신할 URL */
                    data: joinObject, /* 파라메터로 보낼 데이터 */
                    headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
                })
                    .success(function (data, status, headers, config) {
                        if (data) {
                            alert("계정이 등록되었습니다.");
                            $state.go('re_login');
                            /* 맞음 */
                        }
                        else {
                            console.log('join_fail');
                            /* 틀림 */
                        }
                    })
                    .error(function (data, status, headers, config) {
                        /* 서버와의 연결이 정상적이지 않을 때 처리 */
                        console.log(status);
                    });
            }
        }
    }
};
