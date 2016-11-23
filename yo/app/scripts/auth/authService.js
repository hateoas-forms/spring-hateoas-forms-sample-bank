'use strict';

angular.module('bankApp')
  .factory('AuthService', function ($rootScope, $http, $state, Config, bankApi, $localStorage, $q, HAL) {
    var _loginResource;

    return {
      principal: function(){
        return $localStorage.principal;
      },
      account: function(){
        var defer = $q.defer();
        bankApi()
          .follow(HAL.rel('account'))
          .getResource()
          .result
          .then(function(principal){
              $localStorage.principal = principal;
              defer.resolve(principal);
          }).catch(function(err){
        	  console.log(err)
            $localStorage.principal = null;
            defer.reject(err);
          });
          return defer.promise;
      },
      login: function(credentials){
        /* No consigo enviar los parametros de forma que le guste a Spring
        return bankApi(null, {headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'accept': 'application/hal+json'
          }})
          .follow('login')
          .post({
            j_username: credentials.username,
            j_password: credentials.password
          })
          .result;
          */

        return $http.post(_loginResource._links['halforms:login'].href, $.param( {
            j_username: credentials.username,
            j_password: credentials.password
          }), {
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded',
              'accept': 'application/hal+json'
            }
          });
        /*
        return bankApi().getResource().result
          .then(function(response){
            return response._links.login.href;
          })
          .then(function(loginHref){
            return $http.post(loginHref, $.param( {
              j_username: credentials.username,
              j_password: credentials.password
            }), {
              headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                'accept': 'application/hal+json'
              }
            });
          });
          */
      },
      logout: function(){
        return bankApi().follow(HAL.rel('logout')).getResource().result
          .then(function(response){
            $localStorage.principal = null;
            return JSON.parse(response.data);
          });
      },
      onAuthenticationError: function(loginResource){
        if (loginResource){
          _loginResource = loginResource;
        }
        $rootScope.principal = null;
        $localStorage.principal = null;
        $state.go('login');
      }
    };
  });
