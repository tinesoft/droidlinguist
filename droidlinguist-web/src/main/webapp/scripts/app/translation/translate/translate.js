angular.module( 'droidlinguist.translation.translate', [
  'droidlinguist.translation.directives',
  'ui.router'
])

.config(function config( $stateProvider ) {

  //States Config
  $stateProvider.state( 'translation.translate', {
    url: '/translate',
    views: {
      "content": {
        controller: 'TranslateCtrl',
        templateUrl: 'scripts/app/translation/translate/translate.tpl.html'
      }
    },
    data:{ pageTitle: 'Translate' }
  });
})

.directive('translateMgr', function(translationToolbarService) {

  var EVENTS = [
    //sidebar target languages events
    'toggleTargetLangVisibility',
    'removeTargetLang',
    //source grid events
    'sourceSelectionChanged'
  ];

  EVENTS = EVENTS.concat(translationToolbarService.events);//merge with events from translation toolbar

  function TranslateMgrCtrl($scope,translationService) {
    this.handlers = EVENTS.reduce(function(memo, event) {
      memo[event] = [];
      return memo;
    }, {});


    TranslateMgrCtrl.prototype.getStrings = function(){
      return translationService.getSourceStrings();
    };

    TranslateMgrCtrl.prototype.getTranslation = function(){
      return translationService.getTranslation();
    };

    TranslateMgrCtrl.prototype.getSourceLang = function(){
      return translationService.getSourceLang().selected;
    };

    TranslateMgrCtrl.prototype.getTargetLangs = function(){
      return translationService.getTargetLangs().selected;
    };

    var ctrl = this;
    //register this directive for receiving and relaying toolbar events
    translationToolbarService.events.forEach(function(tEvent) {
        $scope.$on(translationToolbarService.eventPrefix + tEvent, function (event, data) {
          TranslateMgrCtrl.prototype[tEvent].call(ctrl, data);
        });
      });
    }
 
  //add event handlers
  EVENTS.forEach(function(event) {
    var capitalized = event.charAt(0).toUpperCase() + event.slice(1);
    TranslateMgrCtrl.prototype['on' + capitalized] = function(handler) {
      this.handlers[event].push(handler);
    };
  });

  //add event executors
  EVENTS.forEach(function(event) {
    TranslateMgrCtrl.prototype[event] = function() {
      var params = arguments;//'arguments' contains all params passed when calling the handler
      this.handlers[event].forEach(function(handler){
        handler.apply(this,params);
      });
    };
  });

  return {
    restrict: 'EA',
    //templateUrl: 'scripts/app/translation/translate/directives/translation-mgr.tpl.html',
    scope: {},
    controller: ['$scope', 'translationService', TranslateMgrCtrl]
  };
})
.controller('TranslateCtrl', function($scope, $window, $state, $uibModal){

  var skipExitConfirmation = false;

  //prevent unexpected exit from the 'translate' state
  function onStateChange(event, toState){

    if(skipExitConfirmation){
      return ; //means user was already asked to give its consent
    }

    event.preventDefault();//by default, prevent the event until we have user's confirmation

    var modalInstance = $uibModal.open({
      animation: true,
      templateUrl: 'scripts/app/translation/translate/translate-exit-dialog.tpl.html',
      controller: 'TranslateExitDialogCtrl',
      scope: $scope
    });
    modalInstance.result.then(function () {
      //user decided to proceed with the exit operation
      skipExitConfirmation=true;
      $state.go(toState.name);
    }, function () {
      //user canceled the refresh/exit operation->  nothing to do as the transition event has already been prevented earlier
    });
  }


  //also prevent unexpected refresh of the 'translate' page
  function onPageReload(event){

    var e = event || $window.event;

    //the method above won't work the 'onbeforeunload' event is not cancelable programatically, because of security reasons.

    var exitMessage = 'You are about to refresh the translation page. This will reset your current work and redirect you to Step 1.';
    if (e) {// For IE and Firefox
      e.returnValue = exitMessage;
    }
    else {// For Safari
      return exitMessage;
    }
  }

  $scope.$on('$stateChangeStart', onStateChange);
  $window.onbeforeunload =  onPageReload;

  //clean up
  $scope.$on('$destroy', function(){
    $window.onbeforeunload = undefined;
  });

})

.controller('TranslateExitDialogCtrl', function($scope, $uibModalInstance){

    $scope.proceed = function(){
      $uibModalInstance.close();
    };

    $scope.cancel = function(){
      $uibModalInstance.dismiss('cancel');
    };
})

;
