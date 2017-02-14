(function() {
    'use strict';

    angular
        .module('poshtgarmiApp')
        .controller('MemberDetailController', MemberDetailController);

    MemberDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Member', 'Fund', 'LoanDuration'];

    function MemberDetailController($scope, $rootScope, $stateParams, previousState, entity, Member, Fund, LoanDuration) {
        var vm = this;

        vm.member = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('poshtgarmiApp:memberUpdate', function(event, result) {
            vm.member = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
