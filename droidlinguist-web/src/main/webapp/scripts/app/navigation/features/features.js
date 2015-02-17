angular.module( 'droidlinguist.navigation.features', [
  'droidlinguist.translation.services',
  'ui.router',
  'ui.bootstrap'
])

.config(function config( $stateProvider ) {
  $stateProvider.state( 'navigation.features', {
    url: '/features',
    onEnter: function(){
       window.scrollTo(0, 0);
    },
    views: {
      "content": {
        controller: 'FeaturesCtrl',
        templateUrl: 'scripts/app/navigation/features/features.tpl.html'
      }
    },
    data:{ pageTitle: 'Features' }
  });
})

.controller( 'FeaturesCtrl', function FeaturesController( $scope, $state, translationService ) {
  $scope.startTranslating = function() {
    translationService.resetData();
    $state.go('translation.step1');
  };
})

;
