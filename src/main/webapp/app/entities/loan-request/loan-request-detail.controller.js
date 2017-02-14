(function() {
    'use strict';

    angular
        .module('poshtgarmiApp')
        .controller('LoanRequestDetailController', LoanRequestDetailController);

    LoanRequestDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LoanRequest', 'LoanDurationIteration', 'Member'];

    function LoanRequestDetailController($scope, $rootScope, $stateParams, previousState, entity, LoanRequest, LoanDurationIteration, Member) {
        var vm = this;

        vm.loanRequest = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('poshtgarmiApp:loanRequestUpdate', function(event, result) {
            vm.loanRequest = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
