(function() {
    'use strict';

    angular
        .module('poshtgarmiApp')
        .controller('LoanDurationDialogController', LoanDurationDialogController);

    LoanDurationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LoanDuration', 'Fund', 'Member'];

    function LoanDurationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LoanDuration, Fund, Member) {
        var vm = this;

        vm.loanDuration = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.funds = Fund.query();
        vm.members = Member.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.loanDuration.id !== null) {
                LoanDuration.update(vm.loanDuration, onSaveSuccess, onSaveError);
            } else {
                LoanDuration.save(vm.loanDuration, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('poshtgarmiApp:loanDurationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createDate = false;
        vm.datePickerOpenStatus.startDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
