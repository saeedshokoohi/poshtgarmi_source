(function() {
    'use strict';

    angular
        .module('poshtgarmiApp')
        .controller('LoanDurationIterationDetailController', LoanDurationIterationDetailController);

    LoanDurationIterationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LoanDurationIteration', 'LoanDuration', 'Payment'];

    function LoanDurationIterationDetailController($scope, $rootScope, $stateParams, previousState, entity, LoanDurationIteration, LoanDuration, Payment) {
        var vm = this;

        vm.loanDurationIteration = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('poshtgarmiApp:loanDurationIterationUpdate', function(event, result) {
            vm.loanDurationIteration = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
