/**
 * Created by TeasunKim on 2016-09-12.
 */


var __LoginCtrl = function ($scope, $http, store, $state, $filter, $interval, $rootScope, HOST) {
    $scope.login = [{
        e_mail: "", a_password: ""
    }];

    $scope.loadingStyle = {'display': 'none'};
    $scope.logout = function () {
        //
        // $http({
        //     method: 'POST', //방식
        //     url: "/user/logOut", /* 통신할 URL */
        //     headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        // })
        //     .success(function (data, status, headers, config) {
                alert("로그아웃 되었습니다.");
                store.set('obj', null);
                $state.go('login');
            // })
            // .error(function () {
            //     alert("logout error");
            // });
    };

    $scope.loginPost = function () {
        $scope.loadingStyle = {'display': 'block'};
        $scope.dis = true;
        var loginObject = {
            e_mail: $scope.login.e_mail,
            a_password: $scope.login.a_password
        };
       $http({
            method: 'POST', //방식
            url: HOST + "/web/user/login",
            data: loginObject,
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        })
            .success(function (data, status, headers, config) {

                if (data.num != 0) {
                        var myInfo = {
                            a_name: data.msg,
                            a_id: data.num,
                            login_time: $filter('date')(new Date(), 'yyyy-MM-dd HH-mm-ss')
                        };

                        store.set('obj', myInfo);
                        $rootScope.checkedTime = $filter('date')(new Date(), 'yyyy-MM-dd HH-mm-ss');
                        $scope.loadingStyle = {'display': 'none'};
                        $state.go('main');
                    }
                    else {
                    $scope.dis = false;
                    alert(data.msg);
                    $scope.loadingStyle = {'display': 'none'};
                     }

            })
            .error(function (data, status, headers, config) {
                console.log(status);
            });
    };
};

