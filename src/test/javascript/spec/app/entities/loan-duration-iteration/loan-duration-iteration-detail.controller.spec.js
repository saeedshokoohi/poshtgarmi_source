'use strict';

describe('Controller Tests', function() {

    describe('LoanDurationIteration Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockLoanDurationIteration, MockLoanDuration, MockPayment;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockLoanDurationIteration = jasmine.createSpy('MockLoanDurationIteration');
            MockLoanDuration = jasmine.createSpy('MockLoanDuration');
            MockPayment = jasmine.createSpy('MockPayment');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'LoanDurationIteration': MockLoanDurationIteration,
                'LoanDuration': MockLoanDuration,
                'Payment': MockPayment
            };
            createController = function() {
                $injector.get('$controller')("LoanDurationIterationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'poshtgarmiApp:loanDurationIterationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
