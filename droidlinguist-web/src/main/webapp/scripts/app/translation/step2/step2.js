angular.module( 'droidlinguist.translation.step2', [
  'droidlinguist.translation.services',
  'ui.router'
])


.config(function config( $stateProvider ) {
  $stateProvider.state( 'translation.step2', {
    url: '/step2',
    views: {
      "content": {
        controller: 'Step2Ctrl',
        templateUrl: 'scripts/app/translation/step2/step2.tpl.html'
      }
    },
    data:{ pageTitle: 'Step 2/3 - Select Translator Engine' }
  });
})

.controller( 'Step2Ctrl', function Step2Controller( $scope, $state,translationService) {
  $scope.selection = {
    translator : translationService.getTranslator()
  }
  
  $scope.translators = translationService.getTranslators();

  $scope.goToPrevStep = function()  {
     $state.go('translation.step1');
  };

  $scope.goToNextStep = function() {
    translationService.setTranslator($scope.selection.translator);

    if(translationService.isStep2Complete()){
      $state.go('translation.step3');
    }
    
  };

});

