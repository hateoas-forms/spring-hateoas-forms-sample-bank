'use strict';

angular.module('bankApp')
  .controller('AlertsActivityCtrl', function ($scope, bankApi, $stateParams, HAL, $state) {
	  var listByDateUrl;
	  bankApi().follow(HAL.rel('list-alerts'))
      .getResource()
      .result
      .then(function(alerts){
    	  $scope.alerts =  HAL.embedded(alerts, 'alerts');
      });
    
    $scope.gotoActivity = function(alert, action){
    	$state.go('alert', {alert: alert._links.self.href, action: action});
    };      
  });
