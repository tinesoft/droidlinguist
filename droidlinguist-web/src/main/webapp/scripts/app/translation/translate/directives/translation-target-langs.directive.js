angular.module('droidlinguist.translation.directives')
.directive( 'translationTargetLangs', function(filterFilter, padFilter) {
	return {
		restrict: 'EA',
		require: '^translateMgr',
		templateUrl: 'scripts/app/translation/translate/directives/translation-target-langs.tpl.html',
		scope: {},
		link: function (scope, element, attrs, translateMgrCtrl){

			scope.targetLangs = translateMgrCtrl.getTargetLangs();
			scope.translation = translateMgrCtrl.getTranslation();

			scope.toggleTargetLangVisibility = function(targetLang){
				translateMgrCtrl.toggleTargetLangVisibility(targetLang);
			};

			scope.removeTargetLang = function(targetLang){
				translateMgrCtrl.removeTargetLang(targetLang);
			};

			scope.totalCounter = function(targetLang){

				if(!targetLang || !scope.translation.files || !scope.translation.files[targetLang.code]){
					return 0;//translation files not yet initialized, so no need to go further
				}

				var counter = 0;
				for(i = 0; i < scope.translation.files[targetLang.code].strings.length; i++){
					var item = scope.translation.files[targetLang.code].strings[i];
					counter += item.values.length;
				}
				return counter;
			};

			scope.untranslatedCounter = function(targetLang){

				if(!targetLang || !scope.translation.files || !scope.translation.files[targetLang.code]){
					return 0;//translation files not yet initialized, so no need to go further
				}

				var counter = 0;
				for(i = 0; i < scope.translation.files[targetLang.code].strings.length; i++){
					var item = scope.translation.files[targetLang.code].strings[i];
					counter += filterFilter(item.values, {state : "rqt"}).length;
				}
				return counter;
			};

			scope.unvalidatedCounter = function(targetLang){

				if(!targetLang || !scope.translation.files || !scope.translation.files[targetLang.code]){
					return 0;//translation files not yet initialized, so no need to go further
				}
				
				var counter = 0;
				for(i = 0; i < scope.translation.files[targetLang.code].strings.length; i++){
					var item = scope.translation.files[targetLang.code].strings[i];
					counter += filterFilter(item.values, {state : "awv"}).length;
				}
				return counter;
			};

			scope.validatedCounter = function(targetLang){

				if(!targetLang || !scope.translation.files || !scope.translation.files[targetLang.code]){
					return 0;//translation files not yet initialized, so no need to go further
				}
				
				var counter = 0;
				for(i = 0; i < scope.translation.files[targetLang.code].strings.length; i++){
					var item = scope.translation.files[targetLang.code].strings[i];
					counter += filterFilter(item.values, {state : "vld"}).length;
				}
				return counter;
			};

			scope.failedCounter = function(targetLang){

				if(!targetLang || !scope.translation.files || !scope.translation.files[targetLang.code]){
					return 0;//translation files not yet initialized, so no need to go further
				}
				
				var counter = 0;
				for(i = 0; i < scope.translation.files[targetLang.code].strings.length; i++){
					var item = scope.translation.files[targetLang.code].strings[i];
					for(j = 0; j < item.values.length; j++){
						var value = item.values[j];
						counter += ( value.errors.length ? 1 : 0);
					}
				}
				return counter;
			};

			//register this directive as handler for the 'toggleTargetLangVisibility', 'removeTargetLang'
			translateMgrCtrl.onToggleTargetLangVisibility(function(targetLang){
				targetLang.visible = !targetLang.visible;
			});

			translateMgrCtrl.onRemoveTargetLang(function(targetLang){
				var i = scope.targetLangs.indexOf(targetLang);
				if(i>-1){
					scope.targetLangs.splice(i,1);
				}
			});
		}
	};
});