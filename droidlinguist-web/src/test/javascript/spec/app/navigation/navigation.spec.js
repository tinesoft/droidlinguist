describe( 'droidlinguist.navigation module', function() {
  var NavigationCtrl, HomeCtrl, FeaturesCtrl,  $location, $scope;

  beforeEach( module( 'droidlinguist.navigation' ) );

  beforeEach( inject( function( $controller, _$location_, $rootScope ) {
    $location = _$location_;
    $scope = $rootScope.$new();
    NavigationCtrl = $controller( 'NavigationCtrl', { $location: $location, $scope: $scope });
    HomeCtrl = $controller( 'HomeCtrl', { $location: $location, $scope: $scope });
    FeaturesCtrl = $controller( 'FeaturesCtrl', { $location: $location, $scope: $scope });
  }));

  //make sure that everyting is set correctly in the 'droidlinguist.navigation' module
  it( 'NavigationCtrl should be defined', inject( function() {
    expect( NavigationCtrl ).toBeTruthy();
  }));

  it( 'HomeCtrl should be defined', inject( function() {
    expect( HomeCtrl ).toBeTruthy();
  }));

  it( 'FeaturesCtrl should be defined', inject( function() {
    expect( FeaturesCtrl ).toBeTruthy();
  }));

});

