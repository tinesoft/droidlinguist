angular.module( 'droidlinguist.translation.directives', [
	'droidlinguist.translation.services',
	'components.padFilter',
	'components.underscore',
	'duScroll',
	'ui.layout',
	'ui.grid',
	'ui.grid.cellNav',
	'ui.grid.selection',
	'ui.grid.resizeColumns'] )

.directive( 'languageFlag', function(translationService, $timeout) {
	return {
		restrict: 'EA',
		replace: true,
		template: '<span class="flag-icon flag-icon-{{countryCode}}" title="{{lang.name}} - {{lang.code}}"></span>',
		scope: {
			lang: '='
		},
		link: function (scope, element, attrs){ 
			$timeout(function(){//$timeout to avoid early binding error
				if(scope.lang){
					translationService.getLanguageCountryCode(scope.lang.code)
						.success(function(data, status, headers, config){
							scope.countryCode = data;
					});
				}
			});
		}
	};
})

.directive( 'languageFlagBg', function(translationService,$timeout) {
	return {
		restrict: 'EA',
		replace: true,
		template: '<div class="img-thumbnail flag-icon-background flag-icon-{{countryCode}}" title="{{lang.name}} - {{lang.code}}"></div>',
		scope: {
			lang: '='
		},
		link: function (scope, element, attrs){ 
			$timeout(function(){//$timeout to avoid early binding error 
				if(scope.lang){
					translationService.getLanguageCountryCode(scope.lang.code)
						.success(function(data, status, headers, config){
							scope.countryCode = data;
					});
				}
			});

		}
	};
});



