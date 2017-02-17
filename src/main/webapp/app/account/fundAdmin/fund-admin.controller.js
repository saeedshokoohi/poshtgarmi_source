(function() {
    'use strict';

    angular
        .module('poshtgarmiApp')
        .controller('FundAdminController', FundAdminController);


    FundAdminController.$inject = ['$translate', '$timeout', 'Auth', 'LoginService'];

    function FundAdminController ($translate, $timeout, Auth, LoginService) {
        var vm = this;

        vm.doNotMatch = null;
        vm.error = null;
        vm.errorUserExists = null;
        vm.success = null;

    }
})();
