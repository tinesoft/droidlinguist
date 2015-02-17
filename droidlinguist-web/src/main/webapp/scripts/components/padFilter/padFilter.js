angular.module( 'components.padFilter', [])

.filter('pad', function() {
  return function(input, options) {

    options = options || {};

    options.padMaxSize =  options.padMaxSize || 0;
    options.padDir = options.padDir || 'right'; 
    options.padStr = options.padStr || ' '; 

    var out = input || '';
    if (options.padMaxSize + 1 >= out.length) {
        switch (options.padDir){

            case "left":
                out = new Array(options.padMaxSize + 1 - out.length).join(options.padStr) + out;
            break;

            case "both":
                var right = Math.ceil((options.padMaxSize = options.padMaxSize - out.length) / 2);
                var left = options.padMaxSize - right;
                out = new Array(left+1).join(options.padStr) + out + new Array(right+1).join(options.padStr);
            break;

            default:
                out = out + new Array(options.padMaxSize + 1 - out.length).join(options.padStr);
            break;
        }
    }

    return out;
  };
});