'use strict';

angular.module('bankApp')
  .directive('userMenu', function(){

    //pfff
    $(".dropdown-toggle").dropdown();
    return {
      scope: true,
      restrict: 'A',
      controller: function($scope, $rootScope, AuthService, $state){
        AuthService
          .account()
          .then(function(response){
            $rootScope.principal = response;
            $rootScope.showLoginDialog = false;
            if ($state.current.name === 'home' || $state.current.name === 'login')
              $state.go('dashboard')
          })
          .catch(function(error){
            $state.go('login');
          });

        $rootScope.$on('success-login', function(ev, data){
          AuthService
            .account()
            .then(function(response){
              $rootScope.principal = response;
            })
            .catch(function(error){
              $state.go('login');
            });
        });

        $scope.logout = function(){
          AuthService
            .logout()
            .then(function(response){
              delete $rootScope.principal;
              $state.go('login');
            });
        };
      }
    };
  })

  .directive('login', function($rootScope){
    return {
      restrict: 'A',
      scope: {},
      templateUrl: 'views/login.html',
      replace: true,
      link: function link(scope, element, attrs, controller, transcludeFn) {
        $rootScope.$watch('showLoginDialog', function(showLoginDialog){
          if (showLoginDialog){
            $(element).removeClass('ng-hide');
          }else{
            $(element).addClass('ng-hide');
          }
        });
      },
      controller: function($rootScope, $scope, $state, AuthService){
    	$scope.username = "lmartensen";
        $scope.password = "test";
          
        $scope.onClickLogin = function(username, password){
          $scope.authenticationFailure = false;
          
          AuthService
            .login({username: username, password: password})
            .then(function(response){
              $rootScope.showLoginDialog = false;
              $rootScope.$broadcast('success-login', response.data);
              $rootScope.principal = response.data;
              $state.go('dashboard');
            }).catch(function(error){
              $scope.authenticationFailure = true;
            });
        };
      }
    };
  });
