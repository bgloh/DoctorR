/**
 * Created by TeasunKim on 2016-09-12.
 */

var __PatientEvaluateCtrl = function ($interval, $scope, $http, store, $state, $uibModal, $rootScope, $filter, HOST) {
    var userObject = store.get('obj');
    $scope.loadingStyle = {'display': 'block'};
    $scope.quantity = 4;

    $scope.a_name = userObject.a_name;
    $scope.a_id = userObject.a_id;

    $scope.QnaListPost = function () {
        $scope.loadingStyle = {'display': 'block'};
        var doctorObject = {
            a_id : $scope.a_id
        };
        $http({
            method: 'POST', //방식
            url: HOST + "/web/qna/QnaListPost", /* 통신할 URL */
            data: doctorObject, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        })
            .success(function (data, status, headers, config) {
                if (data) { //존재하지 않음,아이디 사용가능
                    $scope.qna_List = data;
                    $scope.loadingStyle = {'display': 'none'};
                }
                else {

                }
            });
    };
    $scope.QnaListPost();


    $scope.dateTime = function(date) {
        date = date.toString();
        d = (date.split(' ')[0]);
        h = (date.split(' ')[1].split(':')[0]);
        m = (date.split(' ')[1].split(':')[1].split(':')[0]);
        return d+"\n["+h+":"+m+"]";
    };


    $scope.answer = function(qna_id, input_answer) {
        $scope.loadingStyle = {'display': 'block'};
        var qnaObject = {
            qna_id : qna_id,
            a_id : $scope.a_id,
            qna_answer : input_answer
        };

        $http({
            method: 'POST', //방식
            url: HOST + "/web/qna/answerQna", /* 통신할 URL */
            data: qnaObject, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        })
            .success(function (data, status, headers, config) {
                if (data) { //존재하지 않음,아이디 사용가능
                    $scope.QnaListPost();
                }
                else {
                    alert('답변이 이미 작성되었습니다.')
                }
            });
    };

};

