(function() {
    'use strict';
    angular
        .module('poshtgarmiApp')
        .factory('LoanRequest', LoanRequest);

    LoanRequest.$inject = ['$resource', 'DateUtils'];

    function LoanRequest ($resource, DateUtils) {
        var resourceUrl =  'api/loan-requests/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createDate = DateUtils.convertDateTimeFromServer(data.createDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
