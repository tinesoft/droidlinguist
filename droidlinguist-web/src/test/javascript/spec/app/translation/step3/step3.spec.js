describe( "'droidlinguist.translation.step3' module", function() {
  var Step3Ctrl, $scope, $state, $timeout, $window, $q,  translationService, deferred;

  //mocked file to be uploaded
  var srcFile = {
    name: 'strings.xml',
    type: 'text/xml',
    size : 3864 
  };

  //mocked reponses
  var responseOk = { 
    data:{
      strings: [
        {
          name: 'menu_settings',
          type: 'string',
          value : 'Settings'
        },
        {
          name: 'menu_about',
          type: 'string',
          value : 'About'
        }
      ]      
    },
    status: 200,
    config: {},
    headers: {}
  };

  var responseKo = { 
    status: 422,
    data:{
      errors: ["error1", "error2"]
    },
    config: {},
    headers: {}
  };

 var fakeUploadSourceFile = function(file){
    deferred = $q.defer();
    return deferred.promise;
  };

  beforeEach( module( 'droidlinguist.translation.step3' ) );

  beforeEach( inject( function( $controller, _$timeout_, _$window_, $rootScope, _$state_, _$q_, _translationService_ ) {
    $q = _$q_;
    $state = _$state_;
    $window = _$window_;
    $timeout = _$timeout_;
    $scope = $rootScope.$new();
    translationService = _translationService_;

    spyOn(translationService, 'uploadSourceFile').and.callFake(fakeUploadSourceFile);
    
    Step3Ctrl = $controller( 'Step3Ctrl', { 
      translationService: translationService, 
      $timeout: $timeout, 
      $window: $window, 
      $state: $state,  
      $scope: $scope
    });
  }));

  it( 'Step3Ctrl should be defined and default values should be set', inject( function() {
    expect( Step3Ctrl ).toBeTruthy();
    expect($scope.isUploading).toBe(false);
    expect($scope.sourceFile).toEqual({});
  }));


  describe("'uploadSrcFile(file, errorFiles)'", function()
  {
    it("should call 'upload()' on the Upload service", function() {

      $scope.uploadSrcFile(srcFile);

      deferred.resolve(responseOk); // upload() will call success callback
      $scope.$apply();//to force the resolution of the promise
      $timeout.flush(); // cause $timeout to return immediately

      expect(translationService.uploadSourceFile).toHaveBeenCalled();
      expect(translationService.uploadSourceFile.calls.count()).toBe(1);
    });

    it("should call 'success' callback and set $scope properties", function() {
      
      $scope.uploadSrcFile(srcFile);

      expect($scope.sourceFile).toEqual(srcFile);
      expect($scope.isUploading).toBe(true);

      deferred.resolve(responseOk); // upload() will call success callback
      $scope.$apply();//to force the resolution of the promise
      $timeout.flush();// cause $timeout to return immediately

      expect($scope.isUploading).toBe(false);
      expect($scope.sourceFile.data).toEqual(responseOk.data);
    });

    it("should call 'error' callback and set $scope properties", function() {

      $scope.uploadSrcFile(srcFile);

      expect($scope.sourceFile).toEqual(srcFile);
      expect($scope.isUploading).toBe(true);

      deferred.reject(responseKo); // upload() will call error callback
      $scope.$apply();//to force the resolution of the promise
      $timeout.flush(); // cause $timeout to return immediately

      expect($scope.isUploading).toBe(false);
      expect($scope.sourceFile.data).toBe(null);
    });

  });

});

