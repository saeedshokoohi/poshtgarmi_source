'use strict';

describe('Controller Tests', function() {

    describe('Member Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockMember, MockFund, MockLoanDuration;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockMember = jasmine.createSpy('MockMember');
            MockFund = jasmine.createSpy('MockFund');
            MockLoanDuration = jasmine.createSpy('MockLoanDuration');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Member': MockMember,
                'Fund': MockFund,
                'LoanDuration': MockLoanDuration
            };
            createController = function() {
                $injector.get('$controller')("MemberDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'poshtgarmiApp:memberUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
