angular.module( 'droidlinguist.translation.step3', [
  'droidlinguist.translation.services',
  'ui.router'
])


.config(function config( $stateProvider ) {
  $stateProvider.state( 'translation.step3', {
    url: '/step3',
    views: {
      "content": {
        controller: 'Step3Ctrl',
        templateUrl: 'scripts/app/translation/step3/step3.tpl.html'
      }
    },
    data:{ pageTitle: 'Step 3/3 - Upload source file' }
  });
})

.controller( 'Step3Ctrl', function Step3Controller( $scope, $timeout, $state, $window, translationService) {
  $scope.errors = null;
  $scope.isUploading = false;
  $scope.sourceFile = translationService.getSourceFile();

  $scope.uploadSrcFile = function (file, errFiles) {
      $scope.sourceFile = file;
      $scope.isUploading = !!file;

      if(file) {
        $scope.errors = null;

        file.upload = translationService.uploadSourceFile(file);

        file.upload.then(
          function success(response) {
            $timeout(function() {
              file.data = response.data;
            });
          }, 
          function error(response) {
            $scope.isUploading = false;
            if (response.status > 0){
              $scope.errors = response.data.errors;
            }
            file.data = null;
          },
          function progress(evt) {
            // Math.min is to fix IE which reports 200% sometimes
            file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
            //console.log('progress: ' + file.progress + '% , ' + evt.config.file.name);
          }
        ).finally(function(){
          $timeout(function() {
              $scope.isUploading = false;
            },700 /*minimum delay to avoid flickering when rendering the loading spinner*/);
            
        });
      }
  };

  $scope.goToPrevStep = function() {
     $state.go('translation.step2');
  };

  $scope.goToNextStep = function() {
    translationService.setSourceFile($scope.sourceFile);

    if(translationService.isStep3Complete()){
      $state.go('translation.translate');
    }
  };

  //Drag & Drop
  function onDragOver(e) {
    e.preventDefault();
  }

  function onDrop(e) {
    e.preventDefault();
  }

  var windowElement =  angular.element($window);
  windowElement.on("dragover", onDragOver );
  windowElement.on("drop", onDrop);

  //clean up
  $scope.$on('$destroy', function(){
    windowElement.off("dragover", onDragOver );
    windowElement.off("drop", onDrop);
  });

});

