(function() {
    'use strict';

    angular
        .module('poshtgarmiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('accountInfo', {
            parent: 'app',
            url: '/accountInfo/{iteration}/{from}/{to}',
            data: {
                authorities: [],
                pageTitle: 'register.paymentForm'
            },
            views: {
                'content@': {
                    templateUrl: 'app/accountInfo/accountInfo.html',
                    controller: 'AccountInfoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('register');
                    $translatePartialLoader.addPart('member');
                    $translatePartialLoader.addPart('fund');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Fundstat', function($stateParams, Fundstat) {
                    console.log($stateParams);

                    return Fundstat.getPaymentInfo({iterationid:$stateParams.iteration,fromid : $stateParams.from,toid : $stateParams.to}).get().$promise;
                }],
            }
        });
    }
})();
