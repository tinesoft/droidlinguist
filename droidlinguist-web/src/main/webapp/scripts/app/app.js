angular.module( 'droidlinguistApp', [
  'droidlinguist.navigation',
  'droidlinguist.translation',
  'droidlinguist.constants',
  'components.social',
  'components.wow',
  'ui.bootstrap',
  'ui.router',
  'ngAnimate',
  'duScroll'
])

.run(function ($rootScope, BUILD_OPTIONS) {
  $rootScope.BUILD_OPTIONS = BUILD_OPTIONS;
})

.config( function ($uibTooltipProvider, wowProvider) {
  $uibTooltipProvider.options({ placement: 'bottom'});

  wowProvider.init();
})
.value('duScrollOffset', 75)//navbar height + ~
.value('duScrollDuration', 700)
.value('duScrollEasing', function(x){
  //'easeInQuad'
  return x*x;
})

.controller( 'AppCtrl', function AppController ( $scope, $state ) {
  
  //social media settings
  $scope.website = {
    url: 'http://droidlinguist-tinesoft.rhcloud.com/',
    title : 'DroidLinguist',
    description : 'A tool that helps you easily i18nalize your killer android apps!'
  };

  $scope.github = {
    user: 'tinesoft',
    repo: 'droidlinguist'
  };

  //collapse settings
  $scope.isCollapsed = true;

  $scope.isTranslationMode = false;

  $scope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams){
    if ( angular.isDefined( toState.data.pageTitle ) ) {
      $scope.pageTitle = toState.data.pageTitle + ' | droidlinguist' ;
    }

    $scope.isTranslationMode = $state.includes("translation");
  });
})

;

