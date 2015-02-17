angular.module( 'droidlinguist.navigation.home', [
  'droidlinguist.translation.services',
  'ui.router'
])

/**
 * Each section or module of the site can also have its own routes. AngularJS
 * will handle ensuring they are all available at run-time, but splitting it
 * this way makes each module more "self-contained".
 */
.config(function config( $stateProvider ) {
  $stateProvider.state( 'navigation.home', {
    url: '/home',
    onEnter: function(){
       window.scrollTo(0, 0);
    },
    views: {
      "content": {
        controller: 'HomeCtrl',
        templateUrl: 'scripts/app/navigation/home/home.tpl.html'
      }
    },
    data:{ pageTitle: 'Home' }
  });
})

/**
 * And of course we define a controller for our route.
 */
.controller( 'HomeCtrl', function HomeController( $scope, $state, translationService ) {

  $scope.startTranslating = function() {
    translationService.resetData();
    $state.go('translation.step1');
  };

});

