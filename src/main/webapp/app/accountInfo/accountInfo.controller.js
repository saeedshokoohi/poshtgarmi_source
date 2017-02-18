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
        vm.confirm=confirmRequest;
        vm.encrypt=EncryptionMethod;

        function confirmRequest()
        {
            vm.payInfo.username=vm.encrypt( vm.username);
            vm.payInfo.password=vm.encrypt(vm.password);
            Fundstat.sendPayRequest(vm.payInfo);

        }
        function EncryptionMethod(val)
        {
            return val;
        }



    }
})();
