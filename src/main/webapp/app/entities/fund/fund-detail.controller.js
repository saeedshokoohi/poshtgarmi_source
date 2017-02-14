(function() {
    'use strict';

    angular
        .module('poshtgarmiApp')
        .controller('FundDetailController', FundDetailController);

    FundDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Fund', 'Member'];

    function FundDetailController($scope, $rootScope, $stateParams, previousState, entity, Fund, Member) {
        var vm = this;

        vm.fund = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('poshtgarmiApp:fundUpdate', function(event, result) {
            vm.fund = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
