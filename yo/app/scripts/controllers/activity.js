'use strict';

angular.module('bankApp')
  .controller('ActivityCtrl', function ($scope, bankApi, cashaccounts, $stateParams, HAL) {
    $scope.cashaccounts = cashaccounts;

    if ($stateParams.account){
      angular.forEach(cashaccounts, function(acc){
        if (acc.number === $stateParams.account){
          $scope.selectedCashAccount = acc;
        }
      });
    }else{
      $scope.selectedCashAccount = cashaccounts[0];
    }

    $scope.cashAccount = $scope.selectedCashAccount;

    bankApi($scope.selectedCashAccount._links.self.href)
      .getResource().result
      .then(function(response){
        if (response._embedded){
          $scope.transactions = HAL.embedded(response, 'transactions');
        }
      });

  });
