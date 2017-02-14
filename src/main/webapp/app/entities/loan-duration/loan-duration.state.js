(function() {
    'use strict';

    angular
        .module('poshtgarmiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('loan-duration', {
            parent: 'entity',
            url: '/loan-duration?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'poshtgarmiApp.loanDuration.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/loan-duration/loan-durations.html',
                    controller: 'LoanDurationController',
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
                    $translatePartialLoader.addPart('loanDuration');
                    $translatePartialLoader.addPart('loanDurationStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('loan-duration-detail', {
            parent: 'entity',
            url: '/loan-duration/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'poshtgarmiApp.loanDuration.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/loan-duration/loan-duration-detail.html',
                    controller: 'LoanDurationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('loanDuration');
                    $translatePartialLoader.addPart('loanDurationStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LoanDuration', function($stateParams, LoanDuration) {
                    return LoanDuration.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'loan-duration',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('loan-duration-detail.edit', {
            parent: 'loan-duration-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/loan-duration/loan-duration-dialog.html',
                    controller: 'LoanDurationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LoanDuration', function(LoanDuration) {
                            return LoanDuration.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('loan-duration.new', {
            parent: 'loan-duration',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/loan-duration/loan-duration-dialog.html',
                    controller: 'LoanDurationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                title: null,
                                description: null,
                                status: null,
                                agreement: null,
                                createDate: null,
                                startDate: null,
                                minMember: null,
                                maxMember: null,
                                fundSeedAmount: null,
                                sarresidDay: null,
                                loanPayDay: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('loan-duration', null, { reload: 'loan-duration' });
                }, function() {
                    $state.go('loan-duration');
                });
            }]
        })
        .state('loan-duration.edit', {
            parent: 'loan-duration',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/loan-duration/loan-duration-dialog.html',
                    controller: 'LoanDurationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LoanDuration', function(LoanDuration) {
                            return LoanDuration.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('loan-duration', null, { reload: 'loan-duration' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('loan-duration.delete', {
            parent: 'loan-duration',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/loan-duration/loan-duration-delete-dialog.html',
                    controller: 'LoanDurationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LoanDuration', function(LoanDuration) {
                            return LoanDuration.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('loan-duration', null, { reload: 'loan-duration' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
