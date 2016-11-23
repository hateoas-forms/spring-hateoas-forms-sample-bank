'use strict';

angular.module('bankApp')
  .controller('TransfersActivityCtrl', function ($scope, bankApi, $stateParams, HAL, $state) {
	   var prepareData = function(transfers){
		  	  $scope.transfers =  HAL.embedded(transfers, 'transfers');
	    	  var halResource = HAL.res(transfers);
		      var listByDateHalFrormUrl = halResource.buildDocUrl('list-after-date-transfers');
		      listByDateUrl = halResource.href('list-after-date-transfers');
		      
		      bankApi(listByDateHalFrormUrl, {
		      	headers: {
		      		'accept':'application/prs.hal-forms+json'
		      	}})
		        .getResource()
		        .result
		        .then(HAL.form)
		        .then(function(halform){
		        	$scope.status = halform.values('status');
		        });
		      
	      };
	  
	  
	  var listByDateUrl;
	  bankApi().follow(HAL.rel('list-transfers'))
      .getResource()
      .result
      .then(prepareData);
    
    $scope.gotoActivity = function(transfer, action){
    	$state.go('transfer', {transfer: transfer._links.self.href, action: action});
    };
   
    $scope.fd = {
    valuationDate : new Date(),
    valuationDatePickerIsOpen : false
    };
    $scope.fd.valuationDatePickerOpen = function () {

    	$scope.fd.valuationDatePickerIsOpen = true;
    };
    
    $scope.td = {
    valuationDate : new Date(),
    valuationDatePickerIsOpen : false
    };
    $scope.td.valuationDatePickerOpen = function () {

    	$scope.td.valuationDatePickerIsOpen = true;
    }; 
    
    $scope.onClickFilter = function(){
    	var listByDateSubmitUrl = listByDateUrl;
    	//status is a suggest value, so we have to keep out from url if it is empty (filtered by All)
    	if(!$scope.transferStatus){
    		listByDateSubmitUrl = listByDateUrl.replace("status","");
    	}
    	bankApi(listByDateSubmitUrl)
          	.withTemplateParameters({dateFrom: $scope.fromDate ? $scope.fromDate.toISOString().slice(0, 10) : "",
          							dateTo: $scope.toDate ? $scope.toDate.toISOString().slice(0, 10) : "",
          							status: $scope.transferStatus ? $scope.transferStatus.toString() : ""})
            .get()
            .result 
            .then(function(response){
          	  if (response.status === 200){ 
            	  prepareData(JSON.parse(response.body));
              }
            }); 
      };
      
  });
