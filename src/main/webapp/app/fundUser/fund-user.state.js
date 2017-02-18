(function() {
    'use strict';

    angular
        .module('poshtgarmiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('funduser', {
            parent: 'app',
            url: '/funduser',
            data: {
                authorities: [],
                pageTitle: 'register.dashboard'
            },
            views: {
                'content@': {
                    templateUrl: 'app/fundUser/fund-user.html',
                    controller: 'FundUserController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('register');
                    $translatePartialLoader.addPart('member');
                    $translatePartialLoader.addPart('fund');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
