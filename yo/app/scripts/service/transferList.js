'use strict';

angular.module('bankApp')
  .factory('Transfers', function(bankApi, HAL) {
    return {
      list: function(){
        return bankApi()
          .follow(HAL.rel('list-transfers'))
          .getResource()
          .result
          .then(function(transfers){
            return HAL.embedded(transfers, 'transfers');
          });
      }
    };
  });
