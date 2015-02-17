angular.module('droidlinguist.translation.directives')
.directive( 'translationTargetCurrent', function(translationService) {

	function formatPluralsValue() {
		"use strict";
		return function (v, k) {
			this.push( '['+v.quantity+'] :'+v.text);
		};
	}

	return {
		restrict: 'EA',
		require: '^translateMgr',
		templateUrl: 'scripts/app/translation/translate/directives/translation-target-current.tpl.html',
		scope: {},
		controller: function(){},//to be requirable by child directives
		link: function (scope, element, attrs, translateMgrCtrl){


			var strings =	translateMgrCtrl.getStrings();

			scope.sourceLang = translateMgrCtrl.getSourceLang();
			scope.targetLangs = translateMgrCtrl.getTargetLangs();

			scope.translation = translateMgrCtrl.getTranslation();

			scope.translation.name = '';
			scope.translation.sourceLang = scope.sourceLang.code;


			//register this directive as handler for the 'sourceSelectionChanged'
			translateMgrCtrl.onSourceSelectionChanged(function(selected){
				scope.source = selected;
			});

			translateMgrCtrl.onRemoveTargetLang(function(targetLang){
				delete scope.translation.files[targetLang.code];
			});

			translateMgrCtrl.onMoveToPrevUnfinishedSource(function(){
				var startIndex = (scope.source)? scope.source.index : strings.length;
				//select the first source string whose all translations are not "completed"
				//starting from item at 'startIndex' position and ascending
				for(i = 0; i < scope.targetLangs.length; i++){
					var langCode = scope.targetLangs[i].code;
					var langStrings = scope.translation.files[langCode].strings;
					for(j = startIndex; j >=0; j--){
						var item = langStrings[j];
						for(k=0; k < item.values.length; k++){
							if(item.values[k].state != "vld"){
								translateMgrCtrl.sourceSelectionChanged(strings[j]);
								return;
							}
						}
					}
				}
			});

			translateMgrCtrl.onMoveToNextUnfinishedSource(function(){
				var startIndex = (scope.source)? scope.source.index : 0;
				//select the first source string whose all translations are not "completed"
				//starting from item at 'startIndex' position and descending
				for(i = 0; i < scope.targetLangs.length; i++){
					var langCode = scope.targetLangs[i].code;
					var langStrings = scope.translation.files[langCode].strings;
					for(j = startIndex; j < langStrings.length; j++){
						var item = langStrings[j];
						for(k=0; k < item.values.length; k++){
							if(item.values[k].state != "vld"){
								translateMgrCtrl.sourceSelectionChanged(strings[j]);
								return;
							}
						}
					}
				}
			});

			translateMgrCtrl.onMarkAsTranslated(function(){
				
			});

			translateMgrCtrl.onMarkAllAsValidated(function(){
				for(i = 0; i < scope.targetLangs.length; i++){
					var langCode = scope.targetLangs[i].code;
					var langStrings = scope.translation.files[langCode].strings;
					for(j = 0; j < langStrings.length; j++){
						var item = langStrings[j];
						for(k=0; k < item.values.length; k++){
							var currentState = item.values[k].state;
							if(['awv', 'rjd'].indexOf(currentState)>-1){
								item.values[k].state = "vld";
							}
						}
					}
				}
			});

			translateMgrCtrl.onMarkAllAsRejected(function(){
				for(i = 0; i < scope.targetLangs.length; i++){
					var langCode = scope.targetLangs[i].code;
					var langStrings = scope.translation.files[langCode].strings;
					for(j = 0; j < langStrings.length; j++){
						var item = langStrings[j];
						for(k=0; k < item.values.length; k++){
							var currentState = item.values[k].state;
							if(['awv', 'vld'].indexOf(currentState)>-1){
								item.values[k].state = "rjd";
							}
						}
					}
				}
			});

			//prepare and format displayed source string values
			for(i = 0; i < strings.length; i++){
				var item = strings[i];

				item.index = i;
				switch(item.type){
				case "string":
					item.displayValue = item.value;
					break;
				case "string-array":
					item.displayValue = item.values.join (' | ');
					break;
				case "plurals":
					var values = [];
					angular.forEach(item.values, formatPluralsValue(),values);
					item.displayValue = values.join (' | ');
					break;
				}
			}

			var files = translationService.getTranslatedFiles();
			// prepare the translation files for each target language
			scope.translation.files = {};
			for(i = 0; i < scope.targetLangs.length; i++){

				var file = {};

				file.targetLang = scope.targetLangs[i].code;
				file.state = !!files? files[file.targetLang].state : "rqt"; //request translation
				file.errors = !!files? files[file.targetLang].errors : []; //translation errors(ie language not supported...)
				file.strings = [];

				var translatedStrings = !!files? files[file.targetLang].strings : undefined;
				//prepare the strings for the current language
				for(j = 0; j < strings.length; j++)
				{
					var string = {};
					string.name = strings[j].name;
					string.type = strings[j].type;

					//prepare the values for each string
					string.values = [];
					switch(string.type){
						case "string":
						string.values[0] = {//single value
							state: !!translatedStrings? translatedStrings[j].values[0].state : "rqt", 
							text: !!translatedStrings? translatedStrings[j].values[0].text : undefined,
							errors: []//validation errors
						}; 
						break;
						case "string-array":
						for(k = 0; k < strings[j].values.length; k++){
							string.values[k] = {
								state: !!translatedStrings? translatedStrings[j].values[k].state : "rqt", 
								text: !!translatedStrings? translatedStrings[j].values[k].text : undefined,
								errors: []//validation errors
							};
						}
						break;
						case "plurals":
						for(k = 0; k < strings[j].values.length; k++){
							string.values[k] = {
								state: !!translatedStrings? translatedStrings[j].values[k].state : "rqt", 
								quantity: strings[j].values[k].quantity,
								text: !!translatedStrings? translatedStrings[j].values[k].text : undefined,
								errors: []//validation errors
							};
						}
						break;
					}

					file.strings[j] = string;
				}
				scope.translation.files[scope.targetLangs[i].code] = file;

			}

			//make the first target language visible
			if(scope.targetLangs && scope.targetLangs.length){
				scope.targetLangs[0].visible = true;
			}

		}
	};
})

.directive( 'translationSourceItem', function(padFilter, translationService) {

	return {
		restrict: 'EA',
		require: '^translationTargetCurrent',
		templateUrl: 'scripts/app/translation/translate/directives/translation-source-item.tpl.html',
		scope: {
			source : '='
		},
		link: function (scope, element, attrs, translationTargetCurrentCtrl){
			scope.sourceLang = translationService.getSourceLang().selected;
		}
	};
})

.directive( 'translationTargetItem', function(padFilter) {

	var PUNCTUATION_REGEX = /(\.|\.{3}|[?!,;:])$/gm;

	//%[argument_index$][flags][width][.precision]conversion
	var PLACEMARKER_REGEX = /(%([1-9]+\$)?[-#+ 0,(]?[1-9]*(\.[1-9]+)?[a-zA-Z])/g;

	function TranslationTargetItemCtrl($scope, translationService, _) {

		TranslationTargetItemCtrl.prototype.getStrings = function(){
			return translationService.getSourceStrings();
		};

		TranslationTargetItemCtrl.prototype.getTranslation = function(){
			return translationService.getTranslation();
		};

		TranslationTargetItemCtrl.prototype.getTranslator = function(){
			return translationService.getTranslator();
		};

		TranslationTargetItemCtrl.prototype.getSourceLang = function(){
			return translationService.getSourceLang().selected;
		};

		TranslationTargetItemCtrl.prototype.getTargetLangs = function(){
			return translationService.getTargetLangs().selected;
		};

		TranslationTargetItemCtrl.prototype.markValueAs = function(targetLang, sourceIndex, valueIndex, state){

			var file = translationService.getTranslation().files[targetLang.code];
			var translatedString = file.strings[sourceIndex];

			//mark the given item's value of the given target language as 'state'
			var currentState = translatedString.values[valueIndex].state;
			if(currentState != 'rqt'){
				translatedString.values[valueIndex].state = state;
			}
		};

		TranslationTargetItemCtrl.prototype.markItemAs = function(targetLang, sourceIndex, state){

			var file = translationService.getTranslation().files[targetLang.code];
			var translatedString = file.strings[sourceIndex];

			//mark the given item of the given target language as 'state'
			for(k = 0; k < translatedString.values.length; k++){
				var currentState = translatedString.values[k].state;
				if(currentState != 'rqt'){
					translatedString.values[k].state = state;
				}
			}
		};

		TranslationTargetItemCtrl.prototype.onTranslationItemValueChanged = function(targetLang, sourceIndex, valueIndex){
			var strings = translationService.getSourceStrings();
			var translation = translationService.getTranslation();

			var value = translation.files[targetLang].strings[sourceIndex].values[valueIndex];
			value.errors = [];

			var source = "";
			switch(strings[sourceIndex].type){
				case "string":
					source = strings[sourceIndex].value;
					break;
				case "string-array":
					source = strings[sourceIndex].values[valueIndex];
					break;
				case "plurals":
					source = strings[sourceIndex].values[valueIndex].text;
					break;
			}
			
			if(!value.text || !value.text.trim()){
				value.state = "rqt";
				if(source.trim()){
					value.errors.push("Missing translation");
				}
			}
			else{
				value.state = "awv";

				//checks punctuation ending
				if(translationService.isPunctuationValidation()){

					var sourcePunctuations = source.match(PUNCTUATION_REGEX);
					var targetPunctuations = value.text.match(PUNCTUATION_REGEX);

					var missingPunctuations = _.difference(sourcePunctuations, targetPunctuations);
					var unexpectedPunctuations = _.difference(targetPunctuations, sourcePunctuations);

					for(i = 0; i < missingPunctuations.length; i++){
						value.errors.push("Missing ending punctuation : <code class=\"punctuation\" title=\"is missing from your translation, but was present in the Source Text\">"+missingPunctuations[i]+"</code>");
					}

					for(i = 0; i < unexpectedPunctuations.length; i++){
						value.errors.push("Unexpected ending punctuation : <code class=\"punctuation\" title=\"is present in your translation, but was missing from the Source Text\">"+unexpectedPunctuations[i]+"</code>");
					}
				}

				//checks placemarkers 
				if(translationService.isPlacemarkerValidation()){
					var sourcePlacemarkers = source.match(PLACEMARKER_REGEX);
					var targetPlacemarkers = value.text.match(PLACEMARKER_REGEX);

					var missingPlacemarkers = _.difference(sourcePlacemarkers, targetPlacemarkers);
					var unexpectedPlacemarkers = _.difference(targetPlacemarkers, sourcePlacemarkers);

					for(i = 0; i < missingPlacemarkers.length; i++){
						value.errors.push("Missing placemarker : <code class=\"placemarker\" title=\"is missing from your translation, but was present in the Source Text\">"+missingPlacemarkers[i]+"</code>");
					}

					for(i = 0; i < unexpectedPlacemarkers.length; i++){
						value.errors.push("Unexpected placemarker : <code class=\"placemarker\" title=\"is present in your translation, but was missing from the Source Text\">"+unexpectedPlacemarkers[i]+"</code>");
					}

				}
			}
		};
	}

	return {
		restrict: 'EA',
		require: ['translationTargetItem', '^translationTargetCurrent'],
		templateUrl: 'scripts/app/translation/translate/directives/translation-target-item.tpl.html',
		scope: {
			source : '='
		},
		controller: ['$scope', 'translationService', '_', TranslationTargetItemCtrl],
		link: function (scope, element, attrs, ctrls){
			var translationTargetItemCtrl	= ctrls[0];

			scope.targetLangs = translationTargetItemCtrl.getTargetLangs();
			scope.translation = translationTargetItemCtrl.getTranslation();
		}
	};
})

.directive( 'headerItem', function() {

	return {
		restrict: 'EA',
		require: '^translationTargetItem',
		templateUrl: 'scripts/app/translation/translate/directives/header-item.tpl.html',
		scope: {
			source: '=',
			targetLang: '='
		},
		link: function (scope, element, attrs, translationTargetItemCtrl){
			scope.translation = translationTargetItemCtrl.getTranslation();
			scope.translator  = translationTargetItemCtrl.getTranslator();
			scope.sourceLang = translationTargetItemCtrl.getSourceLang();

			scope.markItemAsValidated = function(targetLang, sourceIndex) {
				if(!!targetLang) {
					translationTargetItemCtrl.markItemAs(targetLang, sourceIndex, "vld");
				}
			};

			scope.markItemAsRejected = function(targetLang, sourceIndex) {
				if(!!targetLang) {
					translationTargetItemCtrl.markItemAs(targetLang, sourceIndex, "rjd");
				}
			};
		}
	};
})

.directive( 'stringItem', function() {

	return {
		restrict: 'EA',
		require: '^translationTargetItem',
		templateUrl: 'scripts/app/translation/translate/directives/string-item.tpl.html',
		scope: {
			source: '=',
			targetLang: '='
		},
		link: function (scope, element, attrs, translationTargetItemCtrl){
			scope.translation = translationTargetItemCtrl.getTranslation();
			scope.onTranslationItemValueChanged = translationTargetItemCtrl.onTranslationItemValueChanged;

			scope.markValueAsValidated = function(targetLang, sourceIndex) {
				if(!!targetLang) {
					translationTargetItemCtrl.markValueAs(targetLang, sourceIndex, 0, "vld");
				}
			};

			scope.markValueAsRejected = function(targetLang, sourceIndex) {
				if(!!targetLang) {
					translationTargetItemCtrl.markValueAs(targetLang, sourceIndex, 0, "rjd");
				}
			};
		}
	};
})

.directive( 'stringArrayItem', function(padFilter) {

	return {
		restrict: 'EA',
		require: '^translationTargetItem',
		templateUrl: 'scripts/app/translation/translate/directives/string-array-item.tpl.html',
		scope: {
			source: '=',
			targetLang: '='
		},
		link: function (scope, element, attrs, translationTargetItemCtrl){
			scope.translation = translationTargetItemCtrl.getTranslation();
			scope.onTranslationItemValueChanged = translationTargetItemCtrl.onTranslationItemValueChanged;

			scope.markValueAsValidated = function(targetLang, sourceIndex, valueIndex) {
				if(!!targetLang) {
					translationTargetItemCtrl.markValueAs(targetLang, sourceIndex, valueIndex, "vld");
				}
			};

			scope.markValueAsRejected = function(targetLang, sourceIndex, valueIndex) {
				if(!!targetLang) {
					translationTargetItemCtrl.markValueAs(targetLang, sourceIndex, valueIndex, "rjd");
				}
			};
		}
	};
})

.directive( 'pluralsItem', function(padFilter) {

	return {
		restrict: 'EA',
		require: '^translationTargetItem',
		templateUrl: 'scripts/app/translation/translate/directives/plurals-item.tpl.html',
		scope: {
			source: '=',
			targetLang: '='
		},
		link: function (scope, element, attrs, translationTargetItemCtrl){
			scope.translation = translationTargetItemCtrl.getTranslation();
			scope.onTranslationItemValueChanged = translationTargetItemCtrl.onTranslationItemValueChanged;

			scope.markValueAsValidated = function(targetLang, sourceIndex, valueIndex) {
				if(!!targetLang) {
					translationTargetItemCtrl.markValueAs(targetLang, sourceIndex, valueIndex, "vld");
				}
			};

			scope.markValueAsRejected = function(targetLang, sourceIndex, valueIndex) {
				if(!!targetLang) {
					translationTargetItemCtrl.markValueAs(targetLang, sourceIndex, valueIndex, "rjd");
				}
			};
		}
	};
})

.directive( 'itemStatus', function(padFilter) {

	return {
		restrict: 'EA',
		require: '^translationTargetItem',
		templateUrl: 'scripts/app/translation/translate/directives/item-status.tpl.html',
		scope: {
			source: '=',
			targetLang: '=',
			valueIndex: '=',
			onValidate: '&',
			onReject: '&'
		},
		link: function (scope, element, attrs, translationTargetItemCtrl){
			scope.translation = translationTargetItemCtrl.getTranslation();
		}
	};
})

.directive( 'commentItem', function() {

	return {
		restrict: 'EA',
		require: '^translationTargetItem',
		templateUrl: 'scripts/app/translation/translate/directives/comment-item.tpl.html',
		scope: {
			source: '=',
			targetLang: '='
		},
		link: function (scope, element, attrs, translationTargetItemCtrl){
			scope.translation = translationTargetItemCtrl.getTranslation();
		}
	};
});
