angular.module( 'droidlinguist.translation.step1', [
  'droidlinguist.translation.services',
  'components.orPropsFilter',
  'ui.router',
  'ui.select',
  'ngSanitize'
])

.config(function config( $stateProvider, uiSelectConfig ) {
  uiSelectConfig.theme = 'bootstrap';

  $stateProvider.state( 'translation.step1', {
    url: '/step1',
    views: {
      "content": {
        controller: 'Step1Ctrl',
        templateUrl: 'scripts/app/translation/step1/step1.tpl.html'
      }
    },
    data:{ pageTitle: 'Step 1/3 - Choose source and target languages' }
  });
})

.controller( 'Step1Ctrl', function Step1Controller( $scope, $state, languages, translationService) {

  var lastSelectedSrcLangPos = -1; //position of last selected source lang in the allSrcLangs list


  $scope.sourceLang = translationService.getSourceLang();
  $scope.targetLangs = translationService.getTargetLangs();

  var oldSourceLang = angular.copy($scope.sourceLang.selected || {});
  var oldTargetLangs = angular.copy($scope.targetLangs.selected || []);

  $scope.allSourceLangs = languages;
  $scope.allTargetLangs = languages;

  $scope.$watch('sourceLang.selected', function(newSrcLang, oldSrcLang){
    if ( newSrcLang !== oldSrcLang ) {
         onSourceLangChanged(newSrcLang, oldSrcLang);
       }
  } );

 $scope.$watchCollection('targetLangs.selected', function(newTargetLangs, oldTargetLangs){
    if ( newTargetLangs !== oldTargetLangs ) {
         onTargetLangsChanged(newTargetLangs, oldTargetLangs);
       }
  });

  $scope.goToNextStep = function() {


    var languagesHaveChanged = !angular.equals(oldSourceLang, $scope.sourceLang.selected || {}) || 
                               !angular.equals(oldTargetLangs, $scope.targetLangs.selected || []);

    translationService.setSourceLang($scope.sourceLang);
    translationService.setTargetLangs($scope.targetLangs);
    if(translationService.isStep1Complete()){
      if(languagesHaveChanged) {
        //reset previously uploaded strings.xml @ Step 3 (if any), to force new upload / translation
        translationService.setSourceFile({});
      }
      $state.go('translation.step2');
    }
  };

  function onSourceLangChanged(newSrclang, oldSrcLang) {
     //we remove new source lang from list of all target languages.
    var i =removeLang(newSrclang,$scope.allTargetLangs);

    //we insert back the old source language in the list of all target languages (at same position)
    if(oldSrcLang){
      $scope.allTargetLangs.splice(lastSelectedSrcLangPos,0,oldSrcLang);
    }
    
    //we save the position of the new src lang, to be able to reinsert it later on at same position
    lastSelectedSrcLangPos = i;
    
  }

  function onTargetLangsChanged(newTargetLangs, oldTargetLangs) {

    if(newTargetLangs){
      //remove selected target langs from list of all source langs and clear selection.
      for(var i  in newTargetLangs){
        removeLang(newTargetLangs[i], $scope.allSourceLangs);
      }
    }

    if(oldTargetLangs){
      //we fetch all target langs that have been unselected
      var unselectedTargetLangs = [];
      for(var j  in oldTargetLangs){
        var k = indexOfLang(oldTargetLangs[j],newTargetLangs);

        if(k === -1){
            unselectedTargetLangs.push(oldTargetLangs[j]);
            break;
        }
      }
      
      //and re-insert them back in the list of all source langs
      for(var l in unselectedTargetLangs){
        $scope.allSourceLangs.splice(0,0,unselectedTargetLangs[l]);
      }
    }

  }

  /**
   * Finds the index of the given language in the given list of languages using 'areLangsEqual()'.
   * As the 'languages objects are enhanced by ui-select (with an unique '$$hashKey' property), 
   * a normal indexof wouldn't work...
   */
  function indexOfLang(language, languages) {
    for(var i in languages){
      if(areLangsEqual(language, languages[i])){
          return i;
      }
    }
    return -1;
  }

  /**
   * Compares two languages objects, based on their name and code.
   * A direct object comparison wouldn't work, because the objects are enhanced by ui-select
   * (with an unique '$$hashKey' property).
   */
  function areLangsEqual(lang1, lang2) {
    return (lang1.name === lang2.name && 
            lang1.code === lang2.code);
  }

  function removeLang(lang, languages) {
    var i = indexOfLang(lang,languages);
    if(i !== -1){
      languages.splice(i, 1);
    }
    return i;
  }

});

