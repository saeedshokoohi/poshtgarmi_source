(function() {
    'use strict';

    angular
        .module('poshtgarmiApp')
        .controller('FundDeleteController',FundDeleteController);

    FundDeleteController.$inject = ['$uibModalInstance', 'entity', 'Fund'];

    function FundDeleteController($uibModalInstance, entity, Fund) {
        var vm = this;

        vm.fund = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Fund.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
