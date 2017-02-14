(function() {
    'use strict';

    angular
        .module('poshtgarmiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('loan-duration-iteration', {
            parent: 'entity',
            url: '/loan-duration-iteration?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'poshtgarmiApp.loanDurationIteration.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/loan-duration-iteration/loan-duration-iterations.html',
                    controller: 'LoanDurationIterationController',
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
                    $translatePartialLoader.addPart('loanDurationIteration');
                    $translatePartialLoader.addPart('loanDurationIterationStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('loan-duration-iteration-detail', {
            parent: 'entity',
            url: '/loan-duration-iteration/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'poshtgarmiApp.loanDurationIteration.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/loan-duration-iteration/loan-duration-iteration-detail.html',
                    controller: 'LoanDurationIterationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('loanDurationIteration');
                    $translatePartialLoader.addPart('loanDurationIterationStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LoanDurationIteration', function($stateParams, LoanDurationIteration) {
                    return LoanDurationIteration.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'loan-duration-iteration',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('loan-duration-iteration-detail.edit', {
            parent: 'loan-duration-iteration-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/loan-duration-iteration/loan-duration-iteration-dialog.html',
                    controller: 'LoanDurationIterationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LoanDurationIteration', function(LoanDurationIteration) {
                            return LoanDurationIteration.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('loan-duration-iteration.new', {
            parent: 'loan-duration-iteration',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/loan-duration-iteration/loan-duration-iteration-dialog.html',
                    controller: 'LoanDurationIterationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                title: null,
                                createDate: null,
                                iterationIndex: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('loan-duration-iteration', null, { reload: 'loan-duration-iteration' });
                }, function() {
                    $state.go('loan-duration-iteration');
                });
            }]
        })
        .state('loan-duration-iteration.edit', {
            parent: 'loan-duration-iteration',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/loan-duration-iteration/loan-duration-iteration-dialog.html',
                    controller: 'LoanDurationIterationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LoanDurationIteration', function(LoanDurationIteration) {
                            return LoanDurationIteration.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('loan-duration-iteration', null, { reload: 'loan-duration-iteration' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('loan-duration-iteration.delete', {
            parent: 'loan-duration-iteration',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/loan-duration-iteration/loan-duration-iteration-delete-dialog.html',
                    controller: 'LoanDurationIterationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LoanDurationIteration', function(LoanDurationIteration) {
                            return LoanDurationIteration.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('loan-duration-iteration', null, { reload: 'loan-duration-iteration' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
