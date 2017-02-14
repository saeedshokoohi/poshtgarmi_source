(function() {
    'use strict';

    angular
        .module('poshtgarmiApp')
        .controller('FundDialogController', FundDialogController);

    FundDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Fund', 'Member'];

    function FundDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Fund, Member) {
        var vm = this;

        vm.fund = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.members = Member.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.fund.id !== null) {
                Fund.update(vm.fund, onSaveSuccess, onSaveError);
            } else {
                Fund.save(vm.fund, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('poshtgarmiApp:fundUpdate', result);
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
