angular.module( 'droidlinguist.translation', [
  'droidlinguist.translation.step1',
  'droidlinguist.translation.step2',
  'droidlinguist.translation.step3',
  'droidlinguist.translation.translate',
  'ui.bootstrap'
])
.config( function config ( $stateProvider) {

  $stateProvider
    //main state when in "translation mode"
    .state('translation', {
      abstract: true,
      url: '/translation',
      templateUrl: 'scripts/app/translation/translation.tpl.html',
      resolve: {
        languages: function(translationService){
          return translationService.getLanguages().then(function(data){
            return data.data;
          });
        }
      },
      controller: 'TranslationCtrl'
    });
})

.run(function($rootScope, $state, translationService){
  $rootScope.$on('$stateChangeStart', function(event, toState){

    if(toState.name === 'translation.step2' && !translationService.isStep1Complete()){
      event.preventDefault();
      $state.go('translation.step1');
    }
    else if(toState.name === 'translation.step3' && !translationService.isStep2Complete()){
      event.preventDefault();
      $state.go('translation.step2');
    }
    else if(toState.name === 'translation.translate' && !translationService.isStep3Complete()){
      event.preventDefault();
      $state.go('translation.step3');
    }
  });
})

.controller('TranslationCtrl', function($scope, $state, translationService){

  $scope.options = translationService.getOptions();
  $scope.isTranslateStep = false;
  $scope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams){
    $scope.isTranslateStep = $state.is('translation.translate');//to show the toolbar in the navbar
  });

});
