(function() {
    'use strict';

    angular
        .module('poshtgarmiApp', [
            'ngStorage',
            'tmh.dynamicLocale',
            'pascalprecht.translate',
            'ngResource',
            'ngCookies',
            'ngAria',
            'ngCacheBuster',
            'ngFileUpload',

            'ui.bootstrap',
            'ui.bootstrap.datetimepicker',
            'ui.router',
            //'infinite-scroll',
            // jhipster-needle-angularjs-add-module JHipster will add new module here
            //'angular-loading-bar',
            'lr.upload',
            'persian_datepicker',
            'thatisuday.ng-image-gallery'
            ,'ngMaterial',
            'summernote'
        ])
        .run(run);

    run.$inject = ['stateHandler', 'translationHandler'];

    function run(stateHandler, translationHandler) {
        stateHandler.initialize();
        translationHandler.initialize();
    }
})();
