angular.module( 'droidlinguist.translation.services', [ 
 'ngFileUpload'
])

.factory('translationToolbarService', ['$rootScope', function ($rootScope) {

  var TOOLBAR_EVENTS = [
  //toolbar events
  'moveToPrevSource',
  'moveToNextSource',
  'moveToPrevUnfinishedSource',
  'moveToNextUnfinishedSource',
  'markAsTranslated',
  'markAllAsValidated',
  'markAllAsRejected',
  'toggleEndingPunctuationValidation',
  'togglePlacemarkerValidation'
  ];

  var EVENT_PREFIX = 'translationToolbar:';

  var toolbar = {
    events: TOOLBAR_EVENTS,
    eventPrefix: EVENT_PREFIX
  };

  TOOLBAR_EVENTS.forEach(function(event) {
    toolbar[event] = function (data) {
      $rootScope.$broadcast(EVENT_PREFIX + event, data);
    };
  });
  
  return toolbar;
}])

.factory('translationService', function($http, Upload){
  var data = {};
  data.sourceFile = {};
  data.sourceStrings = {};
  data.translatedFiles = {};
  data.sourceLang = {};
  data.targetLangs = [];
  data.translator = {};
  data.translation = {};

  var options = {};
  options.punctuationValidation = true;
  options.placemarkerValidation = true;

  var translators = [
    {//https://www.microsoft.com/en-us/translator/attribution.aspx
      name: 'Microsoft Translator',
      code: 'microsoft',
      enabled: true,
      credits: 'Translated by ',
      url: 'http://aka.ms/MicrosoftTranslatorAttribution'
    },
    {//https://tech.yandex.com/translate/doc/dg/concepts/design-requirements-docpage/
      name: 'Yandex Translate',
      code: 'yandex',
      enabled: true,
      credits: 'Powered by ',
      url: 'http://translate.yandex.com/'
    },
    {//https://cloud.google.com/translate/v2/attribution
      name: 'Google Translate (coming soon, requires paid API key)',
      code: 'google',
      enabled: false,
      credits: 'Powered by ',
      url: 'http://translate.google.com'
    },
    {
      name: 'No Translator',
      code: 'no',
      enabled: true,
      credits: '',
      url: ''
    }
  ];

  function getSelectedTargetLangs(){
    var targetLangs = [];
    if(data.targetLangs.selected){
      for (var i = 0; i < data.targetLangs.selected.length; i++) {
        targetLangs.push(data.targetLangs.selected[i].code);
      }
    }
    return targetLangs;
  }

  return {
      getData: function(){
        return data;
      },

      resetData: function(){
        data.sourceFile = {};
        data.sourceStrings = {};
        data.translatedFiles = {};
        data.sourceLang = {};
        data.targetLangs = [];
        data.translation = {};
        data.translator = {};
      },
      getOptions: function(){
        return options;
      },

      setSourceFile: function(file){
        if(file && !angular.equals(file,{})){
          data.sourceFile = file;
          data.sourceStrings = file.data.strings;
          data.translatedFiles = file.data.files;
          data.translation = {}; //reset the translation objet
        }
        else {
          data.sourceFile = {};
          data.sourceStrings = {};
          data.translatedFiles = {};
          data.translation = {};
        }


      },
      getSourceFile: function(){
        return data.sourceFile;
      },

      setSourceStrings: function(strings){
        data.sourceStrings = strings;
      },
      getSourceStrings: function(){
        return data.sourceStrings;
      },

      setTranslator: function(translator){
        data.translator = translator;
      },
      getTranslator: function(){
        return data.translator;
      },
      getTranslators: function(){
        return translators;
      },

      setTranslatedFiles: function(files){
        data.translatedFiles = files;
      },
      getTranslatedFiles: function(){
        return data.translatedFiles;
      },

      setSourceLang: function(sourceLang){
        data.sourceLang = sourceLang;
      },
      getSourceLang: function(){
        return data.sourceLang;
      },

      getSelectedSourceLang: function(){
        return data.sourceLang.selected ? data.sourceLang.selected.code : null;
      },

      setTargetLangs: function(targetLangs){
        data.targetLangs = targetLangs;
      },

      getTargetLangs: function(){
        return data.targetLangs;
      },

      getSelectedTargetLangs: getSelectedTargetLangs,

      setTranslation: function(translation){
        data.translation = translation;
      },
      getTranslation: function(){
        return data.translation;
      },

      setPunctuationValidation: function(on){
        options.punctuationValidation = on;
      },
      isPunctuationValidation: function(){
        return options.punctuationValidation;
      },

      setPlacemarkerValidation: function(on){
        options.placemarkerValidation = on;
      },
      isPlacemarkerValidation: function(){
        return options.placemarkerValidation;
      },

      isStep1Complete: function(){
        return data.sourceLang.selected && data.targetLangs.selected && data.targetLangs.selected.length;
      },
      isStep2Complete: function()
      {
        return !angular.equals(data.translator,{});
      },
      isStep3Complete: function()
      {
        return data.sourceFile && data.sourceFile.data;
      },

      uploadSourceFile: function(file){
        return Upload.upload({
          url: '/api/upload',
          method: 'POST',
          headers: {},
          data: {
            sourceFile: file,
            sourceLang: data.sourceLang.selected.code,
            targetLangs: getSelectedTargetLangs().join(", "),
            translator: data.translator.code
          }
        });
      },

      downloadTranslation: function(){
        return $http({
          url: '/api/translation/download',
          method: 'POST',
          responseType: 'arraybuffer',
          headers: {
            'Content-Type': 'application/json; charset=utf-8'
          },
          data : data.translation

        });
      },

      getLanguageCountryCode: function(langCode){
        return $http({
          url: '/api/language/country',
          method: 'GET',
          headers: {
            'Content-Type': 'application/text; charset=utf-8'
          },
          params : {langCode : langCode}
        });
      },

      getLanguages: function() {
        return $http({
          url: '/api/language/all',
          method: 'GET',
          headers: {
            'Content-Type': 'application/json; charset=utf-8'
          }
        });
      }
  };
});
