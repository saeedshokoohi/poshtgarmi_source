(function() {
    'use strict';

    angular
        .module('poshtgarmiApp')
        .controller('LoanDurationIterationDeleteController',LoanDurationIterationDeleteController);

    LoanDurationIterationDeleteController.$inject = ['$uibModalInstance', 'entity', 'LoanDurationIteration'];

    function LoanDurationIterationDeleteController($uibModalInstance, entity, LoanDurationIteration) {
        var vm = this;

        vm.loanDurationIteration = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LoanDurationIteration.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
