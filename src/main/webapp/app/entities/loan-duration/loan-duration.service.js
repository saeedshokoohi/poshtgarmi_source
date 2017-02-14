(function() {
    'use strict';
    angular
        .module('poshtgarmiApp')
        .factory('LoanDuration', LoanDuration);

    LoanDuration.$inject = ['$resource', 'DateUtils'];

    function LoanDuration ($resource, DateUtils) {
        var resourceUrl =  'api/loan-durations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createDate = DateUtils.convertDateTimeFromServer(data.createDate);
                        data.startDate = DateUtils.convertDateTimeFromServer(data.startDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
