(function() {
    'use strict';

    angular
        .module('poshtgarmiApp')
        .controller('LoanDurationDeleteController',LoanDurationDeleteController);

    LoanDurationDeleteController.$inject = ['$uibModalInstance', 'entity', 'LoanDuration'];

    function LoanDurationDeleteController($uibModalInstance, entity, LoanDuration) {
        var vm = this;

        vm.loanDuration = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LoanDuration.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
