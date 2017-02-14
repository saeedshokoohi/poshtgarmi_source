(function() {
    'use strict';

    angular
        .module('poshtgarmiApp')
        .controller('PaymentDetailController', PaymentDetailController);

    PaymentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Payment', 'LoanDurationIteration', 'Member'];

    function PaymentDetailController($scope, $rootScope, $stateParams, previousState, entity, Payment, LoanDurationIteration, Member) {
        var vm = this;

        vm.payment = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('poshtgarmiApp:paymentUpdate', function(event, result) {
            vm.payment = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
