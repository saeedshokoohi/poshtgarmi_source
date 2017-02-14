(function() {
    'use strict';

    angular
        .module('poshtgarmiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('loan-request', {
            parent: 'entity',
            url: '/loan-request?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'poshtgarmiApp.loanRequest.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/loan-request/loan-requests.html',
                    controller: 'LoanRequestController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('loanRequest');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('loan-request-detail', {
            parent: 'entity',
            url: '/loan-request/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'poshtgarmiApp.loanRequest.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/loan-request/loan-request-detail.html',
                    controller: 'LoanRequestDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('loanRequest');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LoanRequest', function($stateParams, LoanRequest) {
                    return LoanRequest.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'loan-request',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('loan-request-detail.edit', {
            parent: 'loan-request-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/loan-request/loan-request-dialog.html',
                    controller: 'LoanRequestDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LoanRequest', function(LoanRequest) {
                            return LoanRequest.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('loan-request.new', {
            parent: 'loan-request',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/loan-request/loan-request-dialog.html',
                    controller: 'LoanRequestDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                createDate: null,
                                description: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('loan-request', null, { reload: 'loan-request' });
                }, function() {
                    $state.go('loan-request');
                });
            }]
        })
        .state('loan-request.edit', {
            parent: 'loan-request',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/loan-request/loan-request-dialog.html',
                    controller: 'LoanRequestDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LoanRequest', function(LoanRequest) {
                            return LoanRequest.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('loan-request', null, { reload: 'loan-request' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('loan-request.delete', {
            parent: 'loan-request',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/loan-request/loan-request-delete-dialog.html',
                    controller: 'LoanRequestDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LoanRequest', function(LoanRequest) {
                            return LoanRequest.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('loan-request', null, { reload: 'loan-request' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
