'use strict';

describe('Controller Tests', function() {

    describe('LoanRequest Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockLoanRequest, MockLoanDurationIteration, MockMember;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockLoanRequest = jasmine.createSpy('MockLoanRequest');
            MockLoanDurationIteration = jasmine.createSpy('MockLoanDurationIteration');
            MockMember = jasmine.createSpy('MockMember');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'LoanRequest': MockLoanRequest,
                'LoanDurationIteration': MockLoanDurationIteration,
                'Member': MockMember
            };
            createController = function() {
                $injector.get('$controller')("LoanRequestDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'poshtgarmiApp:loanRequestUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
