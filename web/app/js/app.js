/**
 * Created by 이호세아 on 2016-05-17.
 */

angular.module("homeApp", [
    'chart.js',
    'ps.chartjs',
    'ngAnimate',
    'ui.router',
    'ngFileUpload',
    'angular-storage',
    'ui.bootstrap',
    'angular-jqcloud',
    'ui.bootstrap.alert',
    'angular-confirm',
    'duScroll'
])

    .config(function (storeProvider) {
        storeProvider.setStore('sessionStorage');
    })
    .controller("findPASSCtrl", __FindPassCtrl)
    .controller("joinCtrl", __JoinCtrl)
    .controller("leftSideBarCtrl", __LeftSidebarCtrl)
    .controller("loginCtrl", __LoginCtrl)
    .controller("indexCtrl", __IndexCtrl)
    .controller("helpCtrl", __HelpCtrl)
    .controller("diaryManageCtrl", __DiaryManageCtrl)
    .controller("patientEvaluateCtrl", __PatientEvaluateCtrl)
    .controller("patientListCtrl", __PatientListCtrl)
    .controller("patientManageCtrl", __PatientManageCtrl)
    .controller("statusCtrl", __StatusCtrl)
    .controller("diaryDetailCtrl", __DiaryDetailCtrl)


    .controller('scroll', function ($scope, $document) {
            $scope.toTheTop = function () {
                $document.scrollTopAnimated(0, 5000).then(function () {
                    console && console.log('You just scrolled to the top!');
                });
            }
            var section3 = angular.element(document.getElementById('section-3'));
            $scope.toSection3 = function () {
                $document.scrollToElementAnimated(section3);
            }
        }
    ).value('duScrollOffset', 30)

    .run(function ($rootScope, $state, store) {
        $rootScope.$on('$stateChangeStart', function (event, toState, toParams) {
            var requireLogin = toState.data.requireLogin;
            if (requireLogin && store.get('obj') == null) {
                event.preventDefault();
                $state.go('login');
            }
        });
    })
    .filter('unique', function () {
        return function (collection, keyname) {
            var output = [],
                keys = [];

            angular.forEach(collection, function (item) {
                var key = item[keyname];
                if (keys.indexOf(key) === -1) {
                    keys.push(key);
                    output.push(item);
                }
            });

            return output;
        };
    })

    //게시물을 end 숫자 까지 잘라서 보여줌
    .filter('slice', function () {
        return function (arr, start, end) {
            return arr.slice(start, end);
        };
    })

    //스크롤 이벤트가 필요한 부분만 추가한다.
    .directive("scroll", function ($window) {
        return function (scope, element, attrs) {
            angular.element($window).bind("scroll", function () {

                var windowHeight = "innerHeight" in window ? window.innerHeight : document.documentElement.offsetHeight;
                var body = document.body, html = document.documentElement;
                var docHeight = Math.max(body.scrollHeight, body.offsetHeight, html.clientHeight, html.scrollHeight, html.offsetHeight);
                var windowBottom = windowHeight + window.pageYOffset;
                if (windowBottom >= docHeight) {
                    scope.quantity = scope.quantity + 4;
                }
                scope.$apply();
            });
        };
    })

    //윈도우 크기 재구성
    .directive('resize', function ($window) {
        return function (scope, element) {
            var w = angular.element($window);
            scope.getWindowDimensions = function () {
                return {
                    'h': w.height(),
                    'w': w.width()
                };
            };
            scope.$watch(scope.getWindowDimensions, function (newValue, oldValue) {
                scope.windowHeight = newValue.h;
                scope.windowWidth = newValue.w;

                scope.style = function () {
                    return {
                        'height': (newValue.h - 100) + 'px',
                        'width': (newValue.w - 100) + 'px'
                    };
                };

            }, true);

            w.bind('resize', function () {
                scope.$apply();
            });
        }
    })



    // 댓글 엔터 입력 처리
    .directive('ngEnter', function () {
        return function (scope, element, attrs) {
            element.bind("keydown keypress", function (event) {
                if (event.which === 13) {
                    scope.$apply(function () {
                        scope.$eval(attrs.ngEnter);
                    });
                    event.preventDefault();
                }
            });
        };
    })

    // .constant('HOST', 'http://localhost:8080')
    .constant('HOST', 'http://52.41.218.18:8080')

    .config(function ($stateProvider, $urlRouterProvider, $httpProvider) {
        $stateProvider
            .state('login', {
                url: '/',
                templateUrl: 'login.html',
                controller: 'loginCtrl',
                controllerAs: 'loginCtrl',
                data: {
                    requireLogin: false
                }
            })


            .state('re_login', {
                url: '/',
                templateUrl: 'login.html',
                controller: 'loginCtrl',
                controllerAs: 'loginCtrl',
                data: {
                    requireLogin: false
                }
            })

            .state('main', {
                url: '/main',
                templateUrl: 'main.html',
                controller: 'indexCtrl',
                controllerAs: 'index',
                data: {
                    requireLogin: true
                }
            })

        .state('help', {
            url: '/help',
            templateUrl: 'help.html',
            controller: 'helpCtrl',
            controllerAs: 'help',
            data: {
                requireLogin: true
            }
        })


            .state('diaryDetail', {
                url: '/diaryDetail',
                templateUrl: 'diaryDetail.html',
                controller: 'diaryDetailCtrl',
                controllerAs: 'diaryDetail',
                data: {
                    requireLogin: true
                }
            })


            .state('diaryManage', {
            url: '/diaryManage',
            templateUrl: 'diaryManage.html',
            controller: 'diaryManageCtrl',
            controllerAs: 'diaryManage',
            data: {
                requireLogin: true
            }
        })

        .state('patientEvaluate', {
            url: '/patientEvaluate',
            templateUrl: 'patientEvaluate.html',
            controller: 'patientEvaluateCtrl',
            controllerAs: 'patientEvaluate',
            data: {
                requireLogin: true
            }
        })

        .state('patientList', {
            url: '/patientList',
            templateUrl: 'patientList.html',
            controller: 'patientListCtrl',
            controllerAs: 'patientList',
            data: {
                requireLogin: true
            }
        })

        .state('patientManage', {
            url: '/patientManage',
            templateUrl: 'patientManage.html',
            controller: 'patientManageCtrl',
            controllerAs: 'patientManage',
            data: {
                requireLogin: true
            }
        })

        .state('status', {
            url: '/status',
            templateUrl: 'status.html',
            controller: 'statusCtrl',
            controllerAs: 'status',
            data: {
                requireLogin: true
            }
        });

        $urlRouterProvider.otherwise('/');
        $httpProvider.defaults.headers.common['Access-Control-Allow-Origin'] = '*';
    })
    .factory('Excel',function($window){
        var uri='data:application/vnd.ms-excel;base64,',
            template='<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head> <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>',
            base64=function(s){return $window.btoa(unescape(encodeURIComponent(s)));},
            format=function(s,c){return s.replace(/{(\w+)}/g,function(m,p){return c[p];})};
        return {
            tableToExcel:function(tableId,worksheetName){
                var table=$(tableId),
                    ctx={worksheet:worksheetName,table:table.html()},
                    href=uri+base64(format(template,ctx));
                return href;
            }
        };
    });





