'use strict';

angular.module('bankApp')
  .factory('authInterceptor', function ($q, $injector) {
    return {
      'request': function(config) {

        return config;
      },
      'responseError': function(rejection) {
        if (rejection.status === 403 || rejection.status === 1403){
          console.log('AUTH ERROR');
        }else if (rejection.status === 401){
          var resource = typeof rejection.data === 'object' ? rejection.data : JSON.parse(rejection.data);
          $injector.get('AuthService').onAuthenticationError(resource);
        }
        return $q.reject(rejection);
      }
    };
  });
