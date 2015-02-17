describe( "'droidlinguist.translation.step2' module", function() {
  var Step2Ctrl, $scope, $state;

  beforeEach( module( 'droidlinguist.translation.step2' ) );


  beforeEach( inject( function( $controller, $rootScope, _$state_) {
    $state = _$state_;
    $scope = $rootScope.$new();
    Step2Ctrl = $controller( 'Step2Ctrl', { 
      $state: $state,  
      $scope: $scope
    });
  }));

  it( 'Step2Ctrl should be defined and default values should be set', inject( function() {
    expect( Step2Ctrl ).toBeTruthy();
    expect($scope.translator).toBeUndefined();
  }));

});

