/**
 * Tests sit right alongside the file they are testing, which is more intuitive
 * and portable than separating `src` and `test` directories. Additionally, the
 * build process will exclude all `.spec.js` files from the build
 * automatically.
 */
describe( 'Directive: components.social', function() {
  
 var $compile, $rootScope,$httpBackend, element;

  // Load the module, which contains the directive
  beforeEach( module( 'components.social' ) );

  // Load the directive template
  beforeEach( module( 'scripts/components/social/social-buttons.tpl.html' ) );

  beforeEach(inject(function(_$compile_, _$rootScope_, _$httpBackend_){
    // The injector unwraps the underscores (_) from around the parameter names when matching
    $compile = _$compile_;
    $rootScope = _$rootScope_;
    $httpBackend = _$httpBackend_;
  }));


  afterEach(function() {
      $httpBackend.verifyNoOutstandingExpectation();
      $httpBackend.verifyNoOutstandingRequest();
   });


  it("should bind to values provided in scope ", function () {
    
    //prepare scope data
    $rootScope.website = {
      url: 'http://tinesoft.github.io/droidlinguist',
      title : 'DroidLinguist',
      description : 'A tool that helps you easily i18nalize your killer android apps!'
    };

    $rootScope.github = {
      user: 'tinesoft',
      repo: 'droidlinguist'
    };

    //request made by google + button
    $httpBackend.expectJSONP('//share.yandex.ru/gpp.xml?url=http%3A%2F%2Ftinesoft.github.io%2Fdroidlinguist').respond('200',{});
    //request made by facebook button
    $httpBackend.expectJSONP('//graph.facebook.com/fql?q=SELECT+total_count+FROM+link_stat+WHERE+url%3D%22http%3A%2F%2Ftinesoft.github.io%2Fdroidlinguist%22&callback=JSON_CALLBACK').respond({ data: [{total_count: 0}]});
    //request made by twitter button
    $httpBackend.expectJSONP('//urls.api.twitter.com/1/urls/count.json?url=http%3A%2F%2Ftinesoft.github.io%2Fdroidlinguist&callback=JSON_CALLBACK').respond('200',{});
    //request made by pinterest button
    $httpBackend.expectJSONP('//api.pinterest.com/v1/urls/count.json?url=http%3A%2F%2Ftinesoft.github.io%2Fdroidlinguist&callback=JSON_CALLBACK').respond('200',{});
    //request made by github button
    $httpBackend.expectJSONP('//api.github.com/repos/tinesoft/droidlinguist?callback=JSON_CALLBACK').respond({ data: {watchers_count: 0}});
    

    // Create an instance of the directive
    element = angular.element('<social-buttons website="website" github="github" ></social-buttons>');
    $compile(element)($rootScope); // Compile the directive
    $rootScope.$digest(); // Update the HTML

    // Get the isolate scope for the directive
    var isoScope = element.isolateScope();

    // Make our assertions
    expect(isoScope.website.url).toBe('http://tinesoft.github.io/droidlinguist');
    expect(isoScope.website.title).toBe('DroidLinguist');
    expect(isoScope.website.description).toBe('A tool that helps you easily i18nalize your killer android apps!');

    expect(isoScope.github.user).toBe('tinesoft');
    expect(isoScope.github.repo).toBe('droidlinguist');

    $httpBackend.flush();
  });


});

