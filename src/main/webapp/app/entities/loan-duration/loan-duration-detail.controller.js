(function() {
    'use strict';

    angular
        .module('poshtgarmiApp')
        .controller('LoanDurationDetailController', LoanDurationDetailController);

    LoanDurationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LoanDuration', 'Fund', 'Member'];

    function LoanDurationDetailController($scope, $rootScope, $stateParams, previousState, entity, LoanDuration, Fund, Member) {
        var vm = this;

        vm.loanDuration = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('poshtgarmiApp:loanDurationUpdate', function(event, result) {
            vm.loanDuration = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
