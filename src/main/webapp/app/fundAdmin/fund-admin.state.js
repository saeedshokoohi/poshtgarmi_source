(function() {
    'use strict';

    angular
        .module('poshtgarmiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('fundadmin', {
            parent: 'app',
            url: '/fundadmin',
            data: {
                authorities: [],
                pageTitle: 'register.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/fundAdmin/fund-admin.html',
                    controller: 'FundAdminController',
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
