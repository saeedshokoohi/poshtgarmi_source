(function() {
    'use strict';
    angular
        .module('poshtgarmiApp')
        .factory('Fundstat', Fundstat);

    Fundstat.$inject = ['$resource', 'DateUtils'];

    function Fundstat ($resource, DateUtils) {

        return{
            getCurrentFundStat:getCurrentFundStat,
            getPaymentInfo:getPaymentInfo
        }

        function getPaymentInfo(params)
        {

            var resourceUrl =  'api/paymentInfo/'+params.iterationid+'/'+params.fromid+'/'+params.toid;

            return $resource(resourceUrl, {}, {
                'get': {
                    method: 'GET',
                    transformResponse: function (data) {
                        if (data) {
                            data = angular.fromJson(data);
                        }
                        return data;
                    }
                }
            });
        }
        function getCurrentFundStat()
        {
            var resourceUrl =  'api/fundstat';

            return $resource(resourceUrl, {}, {
                'get': {
                    method: 'GET',
                    transformResponse: function (data) {
                        if (data) {
                            data = angular.fromJson(data);
                            data.createDate = DateUtils.convertDateTimeFromServer(data.createDate);
                        }
                        return data;
                    }
                }
            });
        }

    }
})();
