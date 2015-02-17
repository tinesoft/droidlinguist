describe( "'droidlinguist.translation.step1' module", function() {
  var Step1Ctrl, $scope, $state, languages;

  beforeEach( module( 'droidlinguist.translation.step1' ) );

  beforeEach( inject( function( $controller,  $rootScope, _$state_) {
    $state = _$state_;
    $scope = $rootScope.$new();
    languages = [];
    Step1Ctrl = $controller( 'Step1Ctrl', { $state: $state, $scope: $scope , languages: languages});
  }));

  it( 'Step1Ctrl should be defined and default values should be set', inject( function() {
    expect( Step1Ctrl ).toBeTruthy();
    expect($scope.sourceLang).toEqual({});
    expect($scope.targetLangs).toEqual([]);
  }));


  describe("'onSourceLangChanged()'", function()
  {
    it("should removed selected source lang from all target langs list", function(){

      $scope.sourceLang.selected = undefined;
      $scope.allTargetLangs = [{ name: "English", code: "EN" }, { name: "French", code: "FR" }];

      $scope.$digest();

      $scope.sourceLang.selected =  { name: "English", code: "EN" };

      $scope.$digest();

      expect($scope.allTargetLangs).toEqual([{ name: "French", code: "FR" }]);

    });
  });

  describe("'onTargetLangsChanged()'", function()
  {
    it("should remove selected target lang from all source langs list", function(){

      $scope.targetLangs.selected = undefined;
      $scope.allSourceLangs = [{ name: "English", code: "EN" }, { name: "French", code: "FR" }];

      $scope.$digest();

      $scope.targetLangs.selected =  [{ name: "French", code: "FR" }];

      $scope.$digest();

      expect($scope.allSourceLangs).toEqual([{ name: "English", code: "EN" }]);

    });

    it("should re-add unselected target lang from all source langs list", function(){

      $scope.targetLangs.selected = undefined;
      $scope.allSourceLangs = [{ name: "English", code: "EN" }, { name: "French", code: "FR" }];

      $scope.$digest();

      $scope.targetLangs.selected =  [{ name: "French", code: "FR" }];

      $scope.$digest();

      expect($scope.allSourceLangs).toEqual([{ name: "English", code: "EN" }]);

      $scope.targetLangs.selected =  [];

      $scope.$digest();

      expect($scope.allSourceLangs).toEqual([{ name: "French", code: "FR" }, { name: "English", code: "EN" }]);

    });
  });

});

