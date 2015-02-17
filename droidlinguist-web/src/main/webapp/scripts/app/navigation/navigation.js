angular.module( 'droidlinguist.navigation', [
  'droidlinguist.navigation.home',
  'droidlinguist.navigation.features'
])
.config( function config ( $stateProvider, $urlRouterProvider ) {
  $urlRouterProvider.otherwise( '/home' );

  $stateProvider
    
    //main state when in "navigation mode"
    .state('navigation', {
      abstract: true,
      templateUrl: 'scripts/app/navigation/navigation.tpl.html'
    });
})

.run( function run () {
})
.controller( 'NavigationCtrl', function NavigationController( $scope ) {
})

;
