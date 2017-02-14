(function() {
    'use strict';

    angular
        .module('poshtgarmiApp')
        .controller('LoanRequestDeleteController',LoanRequestDeleteController);

    LoanRequestDeleteController.$inject = ['$uibModalInstance', 'entity', 'LoanRequest'];

    function LoanRequestDeleteController($uibModalInstance, entity, LoanRequest) {
        var vm = this;

        vm.loanRequest = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LoanRequest.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
