angular.module('droidlinguist.translation.directives')
.directive( 'translationSources', function(uiGridConstants, uiGridCellNavService) {
	return {
		restrict: 'EA',
		require: '^translateMgr',
		templateUrl: 'scripts/app/translation/translate/directives/translation-sources.tpl.html',
		scope: {},
		link: function (scope, element, attrs, translateMgrCtrl){

			scope.strings = translateMgrCtrl.getStrings();

			scope.gridOptions = {
				data : 'strings',
				multiSelect: false,
				enableRowSelection: true,
				enableRowHeaderSelection: false,
				enableSelectionBatchEvent: false,
				enableHorizontalScrollbar: uiGridConstants.scrollbars.NEVER
			};


			var typeCellTemplate = '<div class="ui-grid-cell-contents"><span class="label label-{{COL_FIELD}}">{{COL_FIELD CUSTOM_FILTERS}}</span></div>';
			var textCellTemplate = '<div class="ui-grid-cell-contents">{{COL_FIELD CUSTOM_FILTERS}}</div>';
			scope.gridOptions.columnDefs = [
				{ name: 'name', enableColumnResizing: true},
				{ name: 'type', cellTemplate: typeCellTemplate, width: '10%', enableColumnResizing: true},
				{ name: 'displayValue', displayName: 'Text', cellTemplate: textCellTemplate, enableColumnResizing: true}
			];

			scope.gridOptions.onRegisterApi = function(gridApi){
				//set gridApi on scope
				scope.gridApi = gridApi;
				gridApi.selection.on.rowSelectionChanged(scope,function(row){
				if(row.isSelected){
					scope.selectedSource = row.entity;

					//prevent other listeners that selected source has changed
					translateMgrCtrl.sourceSelectionChanged(scope.selectedSource);
				}
				});
			};

			//register this directive as handler for the selection changed
			//this is need when the selection is triggered from outside the 
			//grid (from toolbar events by example)
			translateMgrCtrl.onSourceSelectionChanged(function(selected){
				//scroll to selected item if necessary
				scope.selectedSource	= selected;
				scope.gridApi.selection.selectRow(selected);
				uiGridCellNavService.scrollToFocus(scope.gridApi.grid, selected);
			});

			//register this directive as handler for the toolbar events
			translateMgrCtrl.onMoveToPrevSource(function(){
				if(scope.selectedSource){
					//workaround to simulate real negative modulo in js : ((n % m) + m) % m;
					var prevIndex = (scope.selectedSource.index - 1) % scope.strings.length;
					prevIndex = (prevIndex + scope.strings.length) % scope.strings.length;
					scope.gridApi.selection.selectRow(scope.strings[prevIndex]);
					//scroll to selected item if necessary
					uiGridCellNavService.scrollToFocus(scope.gridApi.grid, scope.strings[prevIndex]);
				}
				else if(scope.strings.length){
					scope.gridApi.selection.selectRow(scope.strings[0]);
				}
			});

			translateMgrCtrl.onMoveToNextSource(function(){
				if(scope.selectedSource){
					var nextIndex = (scope.selectedSource.index + 1) % scope.strings.length;
					scope.gridApi.selection.selectRow(scope.strings[nextIndex]);
					//scroll to selected item if necessary
					uiGridCellNavService.scrollToFocus(scope.gridApi.grid, scope.strings[nextIndex]);
				}
				else if(scope.strings.length){
					scope.gridApi.selection.selectRow(scope.strings[0]);
				}
			});

		}
	};
});