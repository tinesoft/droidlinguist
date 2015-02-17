angular.module( 'components.social', ['ngSocial'] )

.directive( 'socialButtons', function() {
	return {
		restrict: 'E',
		templateUrl: 'scripts/components/social/social-buttons.tpl.html',
		scope: {
			website: '=',
			github: '='
		}
	};
});

