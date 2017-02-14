(function() {
    'use strict';
    angular
        .module('poshtgarmiApp')
        .factory('Fund', Fund);

    Fund.$inject = ['$resource', 'DateUtils'];

    function Fund ($resource, DateUtils) {
        var resourceUrl =  'api/funds/:id';

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
