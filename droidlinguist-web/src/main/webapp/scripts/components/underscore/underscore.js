//to inject underscore.js as any angular service
angular.module('components.underscore', [])
	.factory('_', function($window){
		return $window._;
	});