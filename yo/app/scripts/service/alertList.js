'use strict';

angular.module('bankApp')
  .factory('Alerts', function(bankApi, HAL) {
    return {
      list: function(){
        return bankApi()
          .follow(HAL.rel('list-alerts'))
          .getResource()
          .result
          .then(function(alerts){
            return HAL.embedded(alerts, 'alerts');
          });
      }
    };
  });
