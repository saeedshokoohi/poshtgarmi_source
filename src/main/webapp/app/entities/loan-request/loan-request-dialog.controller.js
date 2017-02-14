(function() {
    'use strict';

    angular
        .module('poshtgarmiApp')
        .controller('LoanRequestDialogController', LoanRequestDialogController);

    LoanRequestDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LoanRequest', 'LoanDurationIteration', 'Member'];

    function LoanRequestDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LoanRequest, LoanDurationIteration, Member) {
        var vm = this;

        vm.loanRequest = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.loandurationiterations = LoanDurationIteration.query();
        vm.members = Member.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.loanRequest.id !== null) {
                LoanRequest.update(vm.loanRequest, onSaveSuccess, onSaveError);
            } else {
                LoanRequest.save(vm.loanRequest, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('poshtgarmiApp:loanRequestUpdate', result);
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
