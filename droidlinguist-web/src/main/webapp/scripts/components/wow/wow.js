//to inject wowjs.js as any angular service
angular.module('components.wow', [])
	.provider('wow', [function () {

		this.init = function (options) {
			var defaultOptions ={
				boxClass:     'wow',      // animated element css class (default is wow)
				animateClass: 'animated', // animation css class (default is animated)
				offset:       0,          // distance to the element when triggering the animation (default is 0)
				mobile:       true,       // trigger animations on mobile devices (default is true)
				live:         true,       // act on asynchronously loaded content (default is true)
				callback:     function(box) {
					  // the callback is fired every time an animation is started
					  // the argument that is passed in is the DOM node being animated
					},
				scrollContainer: null // optional scroll container selector, otherwise use window
			};

			var options = angular.extend({}, defaultOptions, options);
			new WOW(options).init();
		};

		this.$get = [function() {
			return {
			};
		}];
	}])