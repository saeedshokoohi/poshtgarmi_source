(function() {
    'use strict';
    angular
        .module('poshtgarmiApp')
        .factory('LoanDurationIteration', LoanDurationIteration);

    LoanDurationIteration.$inject = ['$resource', 'DateUtils'];

    function LoanDurationIteration ($resource, DateUtils) {
        var resourceUrl =  'api/loan-duration-iterations/:id';

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
