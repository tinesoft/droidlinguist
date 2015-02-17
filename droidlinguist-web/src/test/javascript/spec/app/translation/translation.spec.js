describe( "'droidlinguist.translation' module", function() {
  var Step1Ctrl, Step2Ctrl, Step3Ctrl, TranslateCtrl, $scope, languages;

  beforeEach( module( 'droidlinguist.translation' ) );

  beforeEach( inject( function( $controller, $rootScope ) {
    $scope = $rootScope.$new();
    languages = [];
    Step1Ctrl = $controller( 'Step1Ctrl', { $scope: $scope, languages: languages});
    Step2Ctrl = $controller( 'Step2Ctrl', { $scope: $scope });
    Step3Ctrl = $controller( 'Step3Ctrl', { $scope: $scope });
    TranslateCtrl = $controller( 'TranslateCtrl', { $scope: $scope});
  }));

  //make sure that everyting is set correctly in the 'droidlinguist.translation' module

  it( 'Step1Ctrl should be defined', inject( function() {
    expect( Step1Ctrl ).toBeTruthy();
  }));

  it( 'Step2Ctrl should be defined', inject( function() {
    expect( Step2Ctrl ).toBeTruthy();
  }));

  it( 'Step3Ctrl should be defined', inject( function() {
    expect( Step3Ctrl ).toBeTruthy();
  }));

  it( 'TranslateCtrl should be defined', inject( function() {
    expect( TranslateCtrl ).toBeTruthy();
  }));
  
});

