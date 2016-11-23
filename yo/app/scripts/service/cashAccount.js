'use strict';

angular.module('bankApp')
  .factory('CashAccount', function(bankApi, HAL) {
    return {
      list: function(){
        return bankApi()
          .follow(HAL.rel('cashaccounts'))
          .getResource()
          .result
          .then(function(accounts){
            return HAL.embedded(accounts, 'cashAccounts');
          });
      }
    };
  });
