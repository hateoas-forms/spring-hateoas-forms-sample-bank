'use strict';

angular.module('bankApp')
  .controller('TransferCtrl', function ($scope, $state, $stateParams, bankApi, transferResource, HAL) {
	var makeTransferUrl;
	var halResource;
    
    // FIXME: build URL with Curies
    var cashaccountsUrl; 
   if($stateParams.action) {

		halResource = HAL.res(transferResource);
		console.log(halResource);
		
	    makeTransferUrl = halResource.href($stateParams.action);
	    cashaccountsUrl = halResource.buildDocUrl($stateParams.action);
	    if($stateParams.action=='delete') {
	    	$scope.template='delete';
	    	$scope.myButtonLabel = "Delete";
	    } else {
	    	$scope.template='put';
	    	$scope.myButtonLabel = "Update";
	    }
	    $scope.newTransfer = {};
	    $scope.newTransfer.id = halResource._resource.id;
	    $scope.newTransfer.fromAccount = halResource._resource.fromAccount;
	    $scope.newTransfer.toAccount = halResource._resource.toAccount;
	    $scope.newTransfer.description = halResource._resource.description;
	    $scope.newTransfer.amount = halResource._resource.amount;
	    $scope.newTransfer.type = halResource._resource.type;
	    $scope.newTransfer.status = halResource._resource.status;
	    $scope.newTransfer.options = halResource._resource.options;
	    $scope.newTransfer.telephone = halResource._resource.telephone;
	    $scope.newTransfer.email = halResource._resource.email;
	} else {
		$scope.template = 'post';
		$scope.myButtonLabel = "Send";
		halResource = HAL.res(transferResource);
	    makeTransferUrl = halResource.href('make-transfer');
	    cashaccountsUrl = halResource.buildDocUrl('make-transfer');
	    $scope.newTransfer = {};
	}
	console.log(cashaccountsUrl);
	
    
    // FIXME: build URL with Curies
    var toAccountUrl;
    bankApi(cashaccountsUrl, {
    	headers: {
    		'accept':'application/prs.hal-forms+json'
    	}})
      .getResource()
      .result
      .then(HAL.form)
      .then(function(halform){
    	  
    	if($scope.template=='delete') {
    		  halform.property('method', $scope.template);
    		  $scope.isNonEditableForm = true;
    		  $scope.cashaccounts = [{number:$scope.newTransfer.fromAccount, description:$scope.newTransfer.fromAccount}];
    		  $scope.type = [{value:$scope.newTransfer.type, prompt:$scope.newTransfer.type}];
    		  $scope.status = [{value:$scope.newTransfer.status, prompt:$scope.newTransfer.status}];
    		  $scope.options = [{value:$scope.newTransfer.options, prompt:$scope.newTransfer.options}];
	    } else {
	    	  var pr = halform.property('toAccount', $scope.template);
	    	  toAccountUrl = pr.suggest.href;
	    	  $scope.cashaccounts = halform.values('fromAccount', $scope.template);
	    	  $scope.type = halform.values('type', $scope.template);
	    	  $scope.status = halform.values('status', $scope.template);
	    	  $scope.options = halform.values('options', $scope.template);
    	}
      });

    var req;
    $scope.onEditingToAccount = function(accNumber){
    	if (!toAccountUrl || accNumber.length >= 20){
    		return;
    	}
    	
    	if (req){
    		req.abort();
    	}
    	
    	req = bankApi(toAccountUrl)
        .withTemplateParameters({filter: accNumber})
        .getResource();
    	
    	return req.result
        .then(function(res){
        	var accounts = HAL.embedded(res, 'cashAccounts');
        	return _.map(accounts, function(account){
        		return {
        			number: account.number, 
        			username: account.username, 
        			description: account.description
        		};
        	}); 
        });
    }
    
    $scope.onClickSend = function(){ 	
      if($scope.template=='delete') {
    	  bankApi(makeTransferUrl, {form: $scope.transferForm})
          .delete($scope.newTransfer.id)
          .result
          .then(function(response){
        	console.log(response.status);
            if (response.status === 204){
            	console.log($scope.newTransfer);
            	$scope.savedTransfer = $scope.newTransfer;
                $scope.newTransfer = {};
                
                //$state.go('activity', {account: transfer.fromAccount});
            }
          }); 
      }	else{
	      var transfer = angular.copy($scope.newTransfer);
	      $scope.transferForm.$reset();
	      console.log("tranfer data -- " + JSON.stringify(transfer));
	      //transfer.fromAccount = '0192301293012930213';
	      if($scope.template=='post') {
	      bankApi(makeTransferUrl, {form: $scope.transferForm})
	        .post(transfer)
	        .result
	        .then(function(response){
	          if (response.status === 201){
	              var savedTransfer = response.data;
	              $scope.newTransfer = {};
	              $scope.savedTransfer = savedTransfer;
	
	              //$state.go('activity', {account: transfer.fromAccount});
          }
        });
	      } else {
	    	  bankApi(makeTransferUrl, {form: $scope.transferForm})
		        .put(transfer)
		        .result
		        .then(function(response){
		          if (response.status === 201){
		              var savedTransfer = response.data;
		              $scope.newTransfer = {};
		              $scope.savedTransfer = savedTransfer;
		
		              //$state.go('activity', {account: transfer.fromAccount});
	          }
	        });
	      }
      }
    };
  });
