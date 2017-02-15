(function () {
        'use strict';
        EO_ProgressBarController.$inject = ['$scope', '$attrs', '$element', '$http', '$timeout'];
        function EO_ProgressBarController($scope, $attrs, $element, $http, $timeout) {
            console.log('loading progress bar...');
//debugger;
            var $ctrl = this;
            $ctrl.disableProgress = true;
            $ctrl.isProgressOpen = false;
            $scope.isLoading = isLoading;
            $scope.$watch($scope.isLoading, function (isloading) {

            });
            var loading_screen;

            function isLoading() {


                $timeout(function () {

                //    hideProgressBar();


                    if ($http.pendingRequests.length > 0) {
                    //    debugger;
                        loading_screen=showProgressBar();


                    }
                    else
                        hideProgressBar();
                    return $http.pendingRequests.length > 0;
                }, 5000);


            }

            function showProgressBar() {
              //  debugger;
                var html = '<div>' +
                    '<span translate="messages.pleaseWait" > لطفا منتظر باشید...</span>' +
                    '</div>'+'    <div class="sk-wave"> ' +
                    '<div class="sk-rect sk-rect1"></div> ' +
                    '<div class="sk-rect sk-rect2"></div> ' +
                    '<div class="sk-rect sk-rect3"></div>' +
                    ' <div class="sk-rect sk-rect4"></div> ' +
                    '<div class="sk-rect sk-rect5"></div> ' +
                    '<div class="sk-rect sk-rect6"></div> ' +
                    '<div class="sk-rect sk-rect7"></div> ' +
                    '</div>';
                if (!$ctrl.isProgressOpen) {

                    $ctrl.isProgressOpen = true;
                    return pleaseWait({
                            logo: "/content/images/logo.png",
                        //    backgroundColor: '#f46d3b',
                        loadingHtml: html
                    });

                }
                else {
                    return loading_screen;
                }


            }

            function hideProgressBar() {
                if (loading_screen) {
                    loading_screen.finish();
                    $ctrl.isProgressOpen=false;
                }

            }




        }
        var EO_ProgressBar = {
            // template:'tets:{{$ctrl.testStr}}',
            templateUrl: 'app/components/eyeson/progress-bar/eyeson.progress-bar.template.html',
            controller: EO_ProgressBarController,
            bindings: {}

        };
        angular
            .module('poshtgarmiApp')
            .component('eoProgressBar', EO_ProgressBar)
    }

    )();
