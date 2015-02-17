angular.module('droidlinguist.translation.directives')
.directive( 'translationToolbar', function(translationService, translationToolbarService) {
	return {
		restrict: 'EA',
		templateUrl: 'scripts/app/translation/translate/directives/translation-toolbar.tpl.html',
		scope: {},
		link: function (scope, element, attrs){

			scope.options = {};
			scope.options.punctuationValidation = translationService.isPunctuationValidation();
			scope.options.placemarkerValidation = translationService.isPlacemarkerValidation();

			scope.download = function(){
			translationService.downloadTranslation()
				.success(function(data, status, headers, config){
					//extract file name from headers
					var contentDisposition = headers()['content-disposition'];
					var match = /filename="([^"]+)"/i.exec(contentDisposition);
					var filename = (match && match[1])? match[1] : 'strings.zip';
					
					//save the file via browser 'Save as' dialog
					var zip = new Blob([data], {type: 'application/zip;charset=utf-8'});
					saveAs(zip, filename);
				})
				.error(function(data, status, headers, config){
				});
			};

			scope.moveToPrevSource = function(){
				translationToolbarService.moveToPrevSource();
			};

			scope.moveToNextSource = function(){
				translationToolbarService.moveToNextSource();
			};

			scope.moveToPrevUnfinishedSource = function(){
				translationToolbarService.moveToPrevUnfinishedSource();
			};

			scope.moveToNextUnfinishedSource = function(){
				translationToolbarService.moveToNextUnfinishedSource();
			};

			scope.markAsTranslated = function(){
				translationToolbarService.markAsTranslated();
			};

			scope.markAllAsValidated = function(){
				translationToolbarService.markAllAsValidated();
			};

			scope.markAllAsRejected = function(){
				translationToolbarService.markAllAsRejected();
			};

			scope.toggleEndingPunctuationValidation = function(){
				translationService.setPunctuationValidation(scope.options.punctuationValidation);
				translationToolbarService.toggleEndingPunctuationValidation(scope.options.punctuationValidation);
			};

			scope.togglePlacemarkerValidation = function(){
				translationService.setPlacemarkerValidation(scope.options.placemarkerValidation);
				translationToolbarService.togglePlacemarkerValidation(scope.options.placemarkerValidation);
			};
		}
	};
});