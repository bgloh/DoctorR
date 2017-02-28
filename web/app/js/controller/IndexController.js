/**
 * Created by TeasunKim on 2016-09-12.
 */

var __IndexCtrl = function ($interval, $scope, $http, store, $state, $uibModal, $rootScope, $filter, HOST, Excel) {
    var userObject = store.get('obj');
    $scope.patient = null;
    $scope.start = false;
    $scope.quantity = 4;
    $scope.selected_u_name ="";
    $scope.sleep_series = ['수면(분)', ''];
    $scope.feed_series = ['수유(분)', '분유(ml)'];
    $scope.temp_series = ['온도', '미세먼지','이산화탄소','VOC'];
    $scope.dust_series = ['미세먼지', ''];
    $scope.co_series = ['이산화탄소', ''];
    $scope.voc_series = ['VOC', ''];
    $scope.loadingStyle = {'display': 'block'};
    $scope.showType = "list";
    $scope.a_name = userObject.a_name;
    $scope.maxFeedRepeat = 5;
    $scope.maxSleepRepeat = 5;
    $scope.maxListRepeat = 10;
    $scope.maxTHListRepeat = 5;
    $scope.sleepArray = [];
    $scope.sleepDates = [];
    $scope.feedArray = [];
    $scope.feedDates = [];
    $scope.tempArray = [];
    $scope.dustArray = [];
    $scope.coArray = [];
    $scope.vocArray = [];
    $scope.dataDates = [];
    $scope.datasetOverride = [{ yAxisID: 'y-axis-1' }, { yAxisID: 'y-axis-2' }];
    $scope.options = {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
            yAxes: [
                {
                    id: 'y-axis-1',
                    type: 'linear',
                    display: true,
                    position: 'left'
                },
                {
                    id: 'y-axis-2',
                    type: 'linear',
                    display: true,
                    position: 'right'
                }
            ]
        }
    };
    $scope.sleepOptions = {
        responsive: true,
        maintainAspectRatio: false
    };
    $scope.dataOptions = {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
            yAxes: [
                {
                    id: 'y-axis-1',
                    type: 'linear',
                    display: true,
                    position: 'left'
                },
                {
                    id: 'y-axis-2',
                    type: 'linear',
                    display: true,
                    position: 'left'
                },
                {
                    id: 'y-axis-3',
                    type: 'linear',
                    display: true,
                    position: 'right'
                },
                {
                    id: 'y-axis-4',
                    type: 'linear',
                    display: true,
                    position: 'right'
                }
            ]
        }
    };
    $scope.addMaxSleepRepeat = function() {
        $scope.maxSleepRepeat += 5;
    };

    $scope.addMaxFeedRepeat = function() {
        $scope.maxFeedRepeat += 5;
    };

    $scope.addMaxListRepeat = function() {
        $scope.maxListRepeat += 5;
    };
    $scope.addMaxTHListRepeat = function () {
        $scope.maxTHListRepeat += 5;
    };
    $scope.dateTime = function(date) {
        date = date.toString();
        d = (date.split(' ')[0]);
        d = (d.split('-')[1] + "월" + d.split('-')[2] + "일");
        return d;
    };

    $scope.timeSplit = function(mill) {
        var str = "";
        h = parseInt(mill/3600);
        if(h > 0) str += h + "시간 ";
        mill = mill%3600;
        m = parseInt(mill/60);
        if(m > 0) str += m + "분 ";
        s = mill%60;
        return str+s+"초";
    };

    $scope.patientListPost = function () {

        $http({
            method: 'POST', //방식
            url: HOST + "/web/api/AllPatientList", /* 통신할 URL */
            data: userObject, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        })
            .success(function (data, status, headers, config) {
                if (data) { //존재하지 않음,아이디 사용가능
                    $scope.patientList = data;
                    $scope.loadingStyle = {'display': 'none'};
                }
                else {
                    alert('환자가 없습니다.');
                }
            });
    };


    $scope.colours = [{
        fillColor: '#B2EBF4',
        strokeColor: '#003399',
        highlightFill: 'rgba(47, 132, 71, 0.8)',
        highlightStroke: 'rgba(47, 132, 71, 0.8)',
        backgroundColor: '#803690'
    }];


    $scope.diaryListPost = function (u_id,u_name,patient) {
        $scope.patient = patient;
        $scope.selected_u_name = u_name;
        $scope.loadingStyle = {'display': 'block'};
        $scope.start = true;
        var patientObject = {
            u_id : u_id
        };

        $http({
            method: 'POST', //방식
            url: HOST + "/web/diary/getDiaryList", /* 통신할 URL */
            data: patientObject, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        })
            .success(function (data, status, headers, config) {
                if (data) { //존재하지 않음,아이디 사용가능
                    $scope.check_List = data;
                    $scope.loadingStyle = {'display': 'none'};
                    $scope.sleepListPost(u_id,u_name);
                    $scope.feedListPost(u_id,u_name);
                    $scope.dataListPost(u_id,u_name);

                    $scope.maxFeedRepeat = 5;
                    $scope.maxSleepRepeat = 5;
                    $scope.maxListRepeat = 10;
                    $scope.maxTHListRepeat = 5;
                    $scope.showType = 'SFlist';
                }
                else {

                }
            });
    };


    $scope.sleepListPost = function (u_id,u_name,patitent) {
        $scope.selected_u_name = u_name;
        $scope.loadingStyle = {'display': 'block'};
        var patientObject = {
            u_id : u_id
        };

        $http({
            method: 'POST', //방식
            url: HOST + "/web/diary/getSleepList", /* 통신할 URL */
            data: patientObject, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        })
            .success(function (data, status, headers, config) {
                if (data) { //존재하지 않음,아이디 사용가능
                    $scope.sleep_List = data;
                    $scope.sleepArray = [];
                    $scope.sleepDates = [];
                    var array = [];
                    var dates = [];
                    var prev_date = "";
                    var total_sleep = 0;
                    for(var i=0; i<data.length && dates.length<60; i++){
                        var s_date = new Date(data[i].s_start);
                        s_date = "" + (s_date.getYear()+1900) +"_"+ (s_date.getMonth()+1) +"_"+ s_date.getDate();

                        if(prev_date != s_date && prev_date != ""){
                            array.push((total_sleep/60).toFixed(3));
                            dates.push(""+prev_date);
                            total_sleep = 0;
                        }

                        total_sleep += data[i].s_total;
                        prev_date = s_date;
                    }
                    if(data.length>0) {
                        array.push((total_sleep/60).toFixed(3));
                        dates.push("" + prev_date);
                    }

                    $scope.sleepData = {
                        type: "line",
                        labels: dates,
                        datasets: [{
                            label: "수면시간(분)",
                            backgroundColor: 'rgba(255,204,51,0.8)',
                            hoverBackgroundColor: 'rgb(255,204,51)',
                            borderColor: 'rgba(255,204,51,1 )',
                            hoverBorderColor: 'rgba(255,204,51,1 )',
                            data: array
                        }]
                    };
                    /*
                    $scope.sleepArray = [array,[]];
                    $scope.sleepDates = dates;*/
                       /* */
                    $scope.loadingStyle = {'display': 'none'};
                }
                else {

                }
            });
    };


    $scope.feedListPost = function (u_id,u_name,patitent) {
        $scope.selected_u_name = u_name;
        $scope.loadingStyle = {'display': 'block'};
        var patientObject = {
            u_id : u_id
        };

        $http({
            method: 'POST', //방식
            url: HOST + "/web/diary/getFeedList", /* 통신할 URL */
            data: patientObject, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        })
            .success(function (data) {
                if (data) { //존재하지 않음,아이디 사용가능
                    $scope.feed_List = data;
                    $scope.feedArray = [];
                    $scope.feedDates = [];
                    var prev_date = "";
                    var total_feed = 0;
                    var total_powder = 0;
                    var array1 = [];
                    var array2 = [];
                    var dates = [];
                    for(var i=0; i<data.length && dates.length<60; i++){
                        var f_date = new Date(data[i].f_start);
                        f_date = "" + (f_date.getYear()+1900) +"_"+ (f_date.getMonth()+1) +"_"+ f_date.getDate();

                        if(prev_date != f_date && prev_date != ""){
                            array1.push((total_feed/60).toFixed(3));
                            array2.push(total_powder);
                            dates.push(""+prev_date);
                            total_feed = 0;
                            total_powder = 0;
                        }

                        if(data[i].feed == '분유'){
                            total_powder += data[i].f_total;
                        }
                        else if(data[i].feed == '좌' || data[i].feed == '우' ){
                            total_feed += data[i].f_total;
                        }


                        prev_date = f_date;
                    }

                    if(data.length>0) {
                        array1.push((total_feed/60).toFixed(3));
                        array2.push(total_powder);
                        dates.push(""+prev_date);
                    }

                    $scope.feedData = {
                        type: "line",
                        labels: dates,
                        datasets: [{
                            label: "수유시간(분)",
                            backgroundColor: 'rgba(100,255,100,0.3)',
                            hoverBackgroundColor: 'rgba(255,255,255,0.7)',
                            borderColor: 'rgba(100,255,100,0.5)',
                            hoverBorderColor: 'rgba(239,111,32,1)',
                            data: array1,
                            yAxisID: 'y-axis-1'
                        }, {
                            label: "분유량(ml)",
                            backgroundColor: 'rgba(100,100,255,0.3)',
                            hoverBackgroundColor: 'rgba(255,255,255,0.7)',
                            borderColor: 'rgba(100,100,255,0.5)',
                            hoverBorderColor: 'rgba(239,111,32,1)',
                            data: array2,
                            yAxisID: 'y-axis-2'
                        }]
                    };

                    $scope.loadingStyle = {'display': 'none'};
                }
                else {

                }
            });
    };
    $scope.dataListPost = function (u_id,u_name,patient) {
        $scope.selected_u_name = u_name;
        $scope.loadingStyle = {'display': 'block'};
        var patientObject = {
            u_id : u_id
        };

        $http({
            method: 'POST', //방식
            url: HOST + "/web/diary/getDataList", /* 통신할 URL */
            data: patientObject, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        })
            .success(function (data) {
                if (data) { //존재하지 않음,아이디 사용가능
                    $scope.data_List = data;
                    $scope.tempArray = [];
                    $scope.dustArray = [];
                    $scope.coArray = [];
                    $scope.vocArray = [];
                    $scope.dataDates = [];
                    var prev_date = "";
                    var total_temp = 0;
                    var total_dust= 0;
                    var total_co = 0;
                    var total_voc = 0;
                    for(var i=0, cnt=0; i<data.length && $scope.dataDates.length<60; i++,cnt++){
                        var d_date = new Date(data[i].d_date);
                        d_date = "" + (d_date.getYear()+1900) +"_"+ (d_date.getMonth()+1) +"_"+ d_date.getDate();

                        if(prev_date != d_date && prev_date != ""){
                            total_temp = total_temp/cnt;
                            total_dust = total_dust/cnt;
                            total_co = total_co/cnt;
                            total_voc = total_voc/cnt;
                            $scope.tempArray.push(total_temp.toFixed(3));
                            $scope.dustArray.push(total_dust.toFixed(3));
                            $scope.coArray.push(total_co.toFixed(3));
                            $scope.vocArray.push(total_voc.toFixed(3));
                            $scope.dataDates.push(""+prev_date);
                            total_temp = 0;
                            total_dust= 0;
                            total_co = 0;
                            total_voc = 0;
                            cnt = 0;
                        }

                        total_temp += data[i].d_temperature;
                        total_dust += data[i].d_dust;
                        total_co += data[i].d_co2;
                        total_voc += data[i].d_voc;

                        prev_date = d_date;
                    }

                    if(data.length>0) {
                        total_temp = total_temp/cnt;
                        total_dust = total_dust/cnt;
                        total_co = total_co/cnt;
                        total_voc = total_voc/cnt;
                        $scope.tempArray.push(total_temp.toFixed(3));
                        $scope.dustArray.push(total_dust.toFixed(3));
                        $scope.coArray.push(total_co.toFixed(3));
                        $scope.vocArray.push(total_voc.toFixed(3));
                        $scope.dataDates.push(""+prev_date);
                    }
                    $scope.dataData = {
                        type: "line",
                        labels: $scope.dataDates,
                        datasets: [{
                            label: "온도",
                            backgroundColor: 'rgba(255,204,51,0.0)',
                            hoverBackgroundColor: 'rgba(255,255,255,0.7)',
                            borderColor: 'rgba(255,100,100,0.5 )',
                            hoverBorderColor: 'rgba(255,204,51,1 )',
                            data: $scope.tempArray,
                            yAxisID: 'y-axis-1'
                        }, {
                            label: "미세먼지",
                            backgroundColor: 'rgba(239,130,32,0.0)',
                            hoverBackgroundColor: 'rgba(255,255,255,0.7)',
                            borderColor: 'rgba(100,255,100,0.5)',
                            hoverBorderColor: 'rgba(239,111,32,1)',
                            data: $scope.dustArray,
                            yAxisID: 'y-axis-2'
                        }, {
                            label: "이산화탄소",
                            backgroundColor: 'rgba(239,130,32,0.0)',
                            hoverBackgroundColor: 'rgba(255,255,255,0.7)',
                            borderColor: 'rgba(100,100,255,0.5)',
                            hoverBorderColor: 'rgba(239,111,32,1)',
                            data: $scope.coArray,
                            yAxisID: 'y-axis-3'
                        }, {
                            label: "VOC",
                            backgroundColor: 'rgba(239,130,32,0.0)',
                            hoverBackgroundColor: 'rgba(255,255,255,0.7)',
                            borderColor: 'rgba(239,130,32,0.5)',
                            hoverBorderColor: 'rgba(239,111,32,1)',
                            data: $scope.vocArray,
                            yAxisID: 'y-axis-4'
                        }]
                    };

                    /*$scope.tempArray = [$scope.tempArray,$scope.dustArray,$scope.coArray,$scope.vocArray];
                    $scope.dustArray = [$scope.dustArray,[]];
                    $scope.coArray = [$scope.coArray,[]];
                    $scope.vocArray = [$scope.vocArray,[]];*/

                    $scope.loadingStyle = {'display': 'none'};
                }
                else {

                }
            });
    };

    $scope.showList = function (type) {
        $scope.showType = type + 'list';
    };

    $scope.showGraph = function (type) {
        $scope.showType = type + 'graph';
    };


    $scope.patientListPost();


    $scope.exportToExcel=function(tableId,type){ // ex: '#my-table'
        var exportHref=Excel.tableToExcel(tableId,$scope.selected_u_name+" 환자");
        // $timeout(function(){location.href=exportHref;},100); // trigger download
        var a = document.createElement('a');
        a.href=exportHref;
        a.download = $scope.selected_u_name+ type + ".xls";
        a.click();
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
                    $scope.loadingStyle = {'display': 'none'};
                }
                else {
                    alert('연결상태에 오류가 있습니다.');
                }
            });
    };
    $scope.showImg=function(url) {
        url = "/storedimg/" + url;
        window.open(url,'Image','width=500px,height=500px,resizable=1');
    }

};

