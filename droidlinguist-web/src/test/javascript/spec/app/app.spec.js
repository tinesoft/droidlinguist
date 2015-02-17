describe( "'droidlinguist' module", function() {
  var AppCtrl, $location, $scope;

  beforeEach( module( 'droidlinguistApp' ) );

  beforeEach( inject( function( $controller, _$location_, $rootScope ) {
    $location = _$location_;
    $scope = $rootScope.$new();
    AppCtrl = $controller( 'AppCtrl', { $location: $location, $scope: $scope });
  }));

  it( 'AppCtrl should be defined', inject( function() {
    expect( AppCtrl ).toBeTruthy();
  }));
});

