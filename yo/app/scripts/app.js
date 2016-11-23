'use strict';

/**
 * @ngdoc overview
 * @name bankApp
 * @description
 * # bankApp
 *
 * Main module of the application.
 */
angular
  .module('bankApp', [
    'ngAnimate',
    'ngCookies',
    'ngMessages',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'ui.router',
    'traverson',
    'ngStorage',
    'angularMoment',
    'ui.bootstrap'
  ])
  .config(function ($stateProvider, $urlRouterProvider, $httpProvider) {

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    $httpProvider.interceptors.push('authInterceptor');
    $httpProvider.interceptors.push('hdivInterceptor');

    $urlRouterProvider.otherwise('/');

    $stateProvider
      .state('home', {
        url: '/',
        controller: 'AuthCtrl',
        resolve: {
          entryPoint: function(bankApi){
            return bankApi()
              .getResource()
              .result;
          }
        }
      })
      .state('dashboard', {
        url: '/dashboard',
        controller: 'MainCtrl',
        templateUrl: 'views/main.html'
      })

      .state('activity', {
        url: '/activity/:account',
        templateUrl: 'views/activity.html',
        controller: 'ActivityCtrl',
        resolve: {
          cashaccounts: function(CashAccount){
            return CashAccount.list();
          }
        }
      })
      .state('transfer', {
        url: '/transfer/:action/:transfer',
        templateUrl: 'views/transfer.html',
        controller: 'TransferCtrl',
        resolve: {
          transferResource: function(bankApi, $stateParams){
        	if($stateParams.transfer) {
        		return bankApi($stateParams.transfer)
                .getResource()
                .result;
        	}  
            return bankApi()
              .getResource()
              .result;
          }
        }
      })
      .state('listTransfers', {
        url: '/listTransfers',
        templateUrl: 'views/listTransfers.html',
        controller: 'TransfersActivityCtrl'
      })
      .state('alert', {
        url: '/alert/:action/:alert',
        templateUrl: 'views/alert.html',
        controller: 'AlertCtrl',
        resolve: {
          alertResource: function(bankApi, $stateParams) {
        	console.log("Alert"+$stateParams.alert);  
        	if($stateParams.alert) {
        		return bankApi($stateParams.alert)
                .getResource()
                .result;
        	}  
            return bankApi()
              .getResource()
              .result;
          }
        }
      })
      .state('listAlerts', {
        url: '/listAlerts',
        templateUrl: 'views/listAlerts.html',
        controller: 'AlertsActivityCtrl',
        resolve: {
        }
      })
      .state('login', {
        url: '/login',
        controller: 'LoginCtrl'
      });

  });
