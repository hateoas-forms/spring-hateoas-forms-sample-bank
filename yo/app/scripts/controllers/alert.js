'use strict';

angular.module('bankApp')
  .controller('AlertCtrl', function ($scope, $state, $stateParams, bankApi, alertResource, HAL) {
	var makeAlertUrl;
	var halResource;
    
    // FIXME: build URL with Curies
    var cashaccountsUrl;
   if($stateParams.action) {

		halResource = HAL.res(alertResource);
		console.log(halResource);
		
	    makeAlertUrl = halResource.href($stateParams.action);
	    cashaccountsUrl = halResource.buildDocUrl($stateParams.action);
	    if($stateParams.action=='delete') {
	    	$scope.template='delete';
	    	$scope.myButtonLabel = "Delete";
	    } else {
	    	$scope.template='put';
	    	$scope.myButtonLabel = "Update";
	    }
	    $scope.newAlert = {};
	    $scope.newAlert.id = halResource._resource.id;
	    $scope.newAlert.name = halResource._resource.name;
	    $scope.newAlert.type = halResource._resource.type;
	    $scope.newAlert.email = halResource._resource.email;
	    $scope.newAlert.telephone = halResource._resource.telephone;
	    $scope.newAlert.username = halResource._resource.username;
	    $scope.newAlert.conditions = halResource._resource.conditions;
	} else {
		$scope.template = 'post';
		$scope.myButtonLabel = "Send";
		halResource = HAL.res(alertResource);
	    makeAlertUrl = halResource.href('make-alert');
	    cashaccountsUrl = halResource.buildDocUrl('make-alert');
	    $scope.newAlert = {};
	}
	console.log(cashaccountsUrl);
	
	var found = true;
	var validIds = [];
	var i=0;
	for(i=0; i<99 && found; i++) {
		if(!$scope.newAlert.conditions[i]) {
			found = false;
		} else {
			validIds.push(i);
		}
	}
	$scope.validIds = validIds;
    
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
    		  $scope.type = [{value:$scope.newAlert.type, prompt:$scope.newAlert.type}];
	    } else {
	    	  $scope.type = halform.values('type', $scope.template);
	    	  if($scope.newAlert.conditions[0]) {
	    	  $scope.cashaccounts = halform.values('conditions[0].account', $scope.template);
	    	  }
    	}
      });

    var req;
    
    $scope.onClickSend = function(){ 	
      if($scope.template=='delete') {
    	  bankApi(makeAlertUrl, {form: $scope.alertForm})
          .delete($scope.newAlert.id)
          .result
          .then(function(response){
        	console.log(response.status);
            if (response.status === 204){
                $scope.newAlert = {};
                
                //$state.go('activity', {account: transfer.fromAccount});
            }
          }); 
      }	else{
	      var transfer = angular.copy($scope.newAlert);
	      $scope.alertForm.$reset();
	      console.log("tranfer data -- " + JSON.stringify(alert));
	      //transfer.fromAccount = '0192301293012930213';
	      if($scope.template=='post') {
	      bankApi(makeAlertUrl, {form: $scope.alertForm})
	        .post(alert)
	        .result
	        .then(function(response){
	          if (response.status === 201){
	              var savedTransfer = response.data;
	              $scope.newAlert = {};
	              $scope.savedAlert = savedAlert;
	
	              //$state.go('activity', {account: transfer.fromAccount});
          }
        });
	      } else {
	    	  bankApi(makeAlertUrl, {form: $scope.alertForm})
		        .put(alert)
		        .result
		        .then(function(response){
		          if (response.status === 201){
		              var savedAlert = response.data;
		              $scope.newAlert = {};
		              $scope.savedAlert = savedAlert;
		
		              //$state.go('activity', {account: transfer.fromAccount});
	          }
	        });
	      }
      }
    };
  });
