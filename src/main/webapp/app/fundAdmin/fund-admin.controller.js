(function() {
    'use strict';

    angular
        .module('poshtgarmiApp')
        .controller('FundAdminController', FundAdminController);


    FundAdminController.$inject = ['$translate', '$timeout', 'Auth', 'Fundstat'];

    function FundAdminController ($translate, $timeout, Auth, Fundstat) {
        var vm = this;

        vm.doNotMatch = null;
        vm.error = null;
        vm.errorUserExists = null;
        vm.success = null;
       vm.fundstat=Fundstat.getCurrentFundStat().get();

    }
})();
