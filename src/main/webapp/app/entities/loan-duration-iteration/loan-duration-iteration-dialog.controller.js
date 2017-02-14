(function() {
    'use strict';

    angular
        .module('poshtgarmiApp')
        .controller('LoanDurationIterationDialogController', LoanDurationIterationDialogController);

    LoanDurationIterationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LoanDurationIteration', 'LoanDuration', 'Payment'];

    function LoanDurationIterationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LoanDurationIteration, LoanDuration, Payment) {
        var vm = this;

        vm.loanDurationIteration = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.loandurations = LoanDuration.query();
        vm.payments = Payment.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.loanDurationIteration.id !== null) {
                LoanDurationIteration.update(vm.loanDurationIteration, onSaveSuccess, onSaveError);
            } else {
                LoanDurationIteration.save(vm.loanDurationIteration, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('poshtgarmiApp:loanDurationIterationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
