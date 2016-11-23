'use strict';

/**
 * @ngdoc function
 * @name bankApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the bankApp
 */
angular.module('bankApp')
  .controller('MainCtrl', function ($scope, bankApi, HAL, $state) {
    bankApi()
      .follow(HAL.rel('cashaccounts'))
      .getResource()
      .result
      .then(function(accounts){
        $scope.cashaccounts = HAL.embedded(accounts, 'cashAccounts');
      });
    bankApi()
      .follow(HAL.rel('creditaccounts'))
      .getResource()
      .result
      .then(function(accounts){
        $scope.creditaccounts = HAL.embedded(accounts, 'creditAccounts');
      });
    
    
    $scope.gotoActivity = function(account){
    	$state.go('activity', {account: account.number});
    };
    
  })
  
  .directive('mainNav', function($rootScope){
	 var toggleOffCanvas = function(){
    	$('.wrapper').toggleClass('off-canvas-active')
    };
    return {
      restrict: 'A',
      scope: {},
      link: function link(scope, element, attrs, controller, transcludeFn) {
    	  $('.btn-off-canvas').click(toggleOffCanvas);
    	  $('.main-menu li a').not('.submenu-toggle').click(function(){
    		  if ($('.btn-off-canvas').is(':visible')){
    			  toggleOffCanvas();
    	  		}
    	  });
      }
    }
  });
