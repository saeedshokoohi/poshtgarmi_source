(function() {
    'use strict';

    angular
        .module('poshtgarmiApp')
        .controller('MessageDetailController', MessageDetailController);

    MessageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Message', 'Fund', 'Member'];

    function MessageDetailController($scope, $rootScope, $stateParams, previousState, entity, Message, Fund, Member) {
        var vm = this;

        vm.message = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('poshtgarmiApp:messageUpdate', function(event, result) {
            vm.message = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
