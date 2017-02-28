/* IMPORTANT!
 - attribute should be applied to canvas
 - ChartJS 2.0 is required
 */


(function() {
    angular.module('ps.chartjs', [])
        .directive("psChart",["$timeout", psChart]);

    function psChart($timeout) {
        return {
            restrict: "A",
            scope: {
                chartType: "@",
                chartData: "=",
                chartOptions: "=",
                chartObj: "=",
                chartShow: "="
            },
            link: function($scope, element, attrs) {
                var ctx = element[0].getContext("2d");
                var chartObj = {};
                var isInitial = true;

                if ($scope.chartType == undefined) {
                    throw new Error('No chart type specified')
                }

                if ($scope.chartShow == undefined) {
                    initChart();
                }

                $scope.$watch("chartShow", function(val) {
                    val === true ? initChart() : destroyChart();
                });

                function initChart() {

                    isInitial ? $timeout(init) : init();

                    function init() {
                        chartObj = new Chart(ctx, {
                            type: $scope.chartType,
                            data: $scope.chartData,
                            options: $scope.chartOptions
                        });

                        if ($scope.chartObj) {
                            $scope.chartObj = chartObj;
                        }

                        isInitial = false;
                    }
                }

                function destroyChart() {
                    console.log("destroeing the cahrt");
                    if (chartObj.destroy != undefined)  chartObj.destroy();
                }

                $scope.destroyChart = destroyChart;
            }
        }
    }
})();
