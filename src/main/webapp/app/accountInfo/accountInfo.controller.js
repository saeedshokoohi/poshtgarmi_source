(function() {
    'use strict';

    angular
        .module('poshtgarmiApp')
        .controller('AccountInfoController', AccountInfoController);


    AccountInfoController.$inject = ['$translate', '$timeout', 'Auth', 'Fundstat','entity'];

    function AccountInfoController ($translate, $timeout, Auth, Fundstat,entity) {
        var vm = this;
        vm.password='';
        vm.payInfo=entity;
        vm.doNotMatch = null;
        vm.error = null;
        vm.errorUserExists = null;
        vm.success = null;



    }
})();
