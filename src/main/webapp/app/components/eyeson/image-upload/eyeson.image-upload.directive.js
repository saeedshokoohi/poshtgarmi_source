(function() {
    'use strict';
    function ImageUploadController($scope,$attrs,$element) {
//debugger;
        var $ctrl = this;
        $ctrl.myImage='';
        $ctrl.myCroppedImage='';
        var handleFileSelect=function(evt) {
            var file=evt.currentTarget.files[0];
            var reader = new FileReader();
            reader.onload = function (evt) {
                $scope.$apply(function($scope){
                    $ctrl.myImage=evt.target.result;
                });
            };
            reader.readAsDataURL(file);
        };
        angular.element(document.querySelector('#fileInput')).on('change',handleFileSelect);

    }
    var ImageUpload = {
       // template:'tets:{{$ctrl.testStr}}',
        templateUrl: 'app/components/eyeson/image-upload/eyeson.image-upload.html',
        controller:ImageUploadController,
        bindings:
        {
          /*  items :'<',
            displaytag:'@?',
            displayitem:'@?',
            list:'='*/

        }

    };

    angular
        .module('poshtgarmiApp')
        .component('eoImageUpload', ImageUpload)

    ImageUploadController.$inject = ['$scope','$attrs','$element'];


})();

