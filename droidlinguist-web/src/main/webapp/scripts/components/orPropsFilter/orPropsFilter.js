/**
 * AngularJS default filter with the following expression:
 * "person in people | filter: {name: search, age: search}"
 * performs a AND between 'name: search' and 'age: search'.
 * We want to perform a OR.
 */
 angular.module('components.orPropsFilter', [] )

.filter('orPropsFilter', function() {
  return function(items, props) {
    var out = [];

    if (angular.isArray(items)) {
      items.forEach(function(item) {
        var itemMatches = false;

        var keys = Object.keys(props);
        for (var i = 0; i < keys.length; i++) {
          var prop = keys[i];
          var text = props[prop].toLowerCase();
          if (item[prop].toString().toLowerCase().indexOf(text) !== -1) {
            itemMatches = true;
            break;
          }
        }

        if (itemMatches) {
          out.push(item);
        }
      });
    } else {
      // Let the output be the input untouched
      out = items;
    }

    return out;
  };
});
