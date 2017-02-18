(function () {
    'use strict';

    angular
        .module('poshtgarmiApp')
        .controller('FundUserController', FundUserController);


    FundUserController.$inject = ['$window','$translate', '$state', '$timeout', 'Auth', 'Fundstat'];

    function FundUserController($window,$translate, $state, $timeout, Auth, Fundstat) {
        var vm = this;

        vm.doNotMatch = null;
        vm.error = null;
        vm.errorUserExists = null;
        vm.success = null;
        vm.gotoPayment = gotoPayment;

        vm.fundstat = Fundstat.getCurrentFundStat().get();
        function gotoPayment() {
            $window.location.href ='/#/accountInfo/' + vm.fundstat.currentIteration.id + '/1009/1010';
        }
    }
})();
