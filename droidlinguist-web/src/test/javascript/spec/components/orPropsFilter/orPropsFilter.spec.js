describe('Filter: components.orPropsFilter', function () {
  var orPropsFilter;
  var languages =  [
    {
      name: "English",
      code: "EN"
    },
    {
      name: "French",
      code: "FR"
    },
    {
      name: "Italian",
      code: "IT"
    },
    {
      name: "Portuguese",
      code: "PT"
    },
    {
      name: "Spanish",
      code: "ES"
    },
    {
      name: "German",
      code: "DE"
    }
  ];

 beforeEach( module('components.orPropsFilter'));

  beforeEach(inject(function ($filter) {
      orPropsFilter = $filter('orPropsFilter');
    }));

  it('should exist', function () {
    expect(!!orPropsFilter).toBe(true);
  });

  describe('when filtering with an non-empty expression', function () {

    it('should only match English language', function () {
      var props = {name: 'En', code: 'EN'};
      //maches on 'name'
      expect(orPropsFilter(languages, props)).toEqual([{name: 'English', code:'EN'}, {name: 'French', code:'FR'}]);
    });


    it('should match Italian, Spanish and German languages in that order', function () {
      var props = {name: 'an'};
      expect(orPropsFilter(languages, props)).toEqual([ {name: 'Italian', code:'IT'}, {name: 'Spanish', code:'ES'},{name: 'German', code:'DE'}]);
    });
  });

  describe('when filtering with an empty expression', function () {

    it('should match no language', function () {
      expect(orPropsFilter(languages,{})).toEqual([]);
    });
  });

  describe('when filtering an empty array of items', function () {

    it('should  match no language', function () {
      var props = {name: 'En', code: 'EN'};
      expect(orPropsFilter([], props)).toEqual([]);
    });

  });

    describe('when filtering a single item', function () {

    it('should  match exactly the same item object', function () {
      var props = {name: 'En', code: 'EN'};
      var language = {name: 'Italian', code:'IT'};
      expect(orPropsFilter(language, props)).toBe(language);
    });

  });

});