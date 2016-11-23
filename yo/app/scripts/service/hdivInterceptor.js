'use strict';

angular.module('bankApp')
.directive('form', function($uibModal){
  var _errorDialog;
  var onValidationError = function(form, ev, error){
    if (form.$reqId !== error.reqId)
      return;

    var hasUnauthorizedError = false;
    _.each(error.errorData.errors, function(err){
      if (err.type !== 'INVALID_EDITABLE_VALUE' && err.type !== 'INVALID_PARAMETER_VALUE' && err.type !== 'NOT_RECEIVED_ALL_REQUIRED_PARAMETERS'){
        hasUnauthorizedError = true;
        return;
      }
      var validationErrorKey = err.type.toLowerCase().replace(/_(.+?)/g, function(v){
        return v.toUpperCase().replace("_","");
      });

      if (!form[err.parameterName]){
        console.warn('Doesnt exists input with name ' + err.parameterName + ' in form ', form);
        return;
      }
      form[err.parameterName].$setValidity(validationErrorKey, false);
    });

    if (hasUnauthorizedError && !_errorDialog){

      _errorDialog = $uibModal.open({
        animation: true,
        templateUrl: 'views/hdivError.html',

        //https://github.com/angular-ui/bootstrap/issues/4603#issuecomment-151890075
        controller: /*@ngInject*/ function($scope, $uibModalInstance){
          $scope.cancel = function(){
            _errorDialog.dismiss('cancel');
          }
        },
        size: '600'
      });

      _errorDialog
        .result
        .then(function(){
  				_errorDialog = null;
  			}, function(){
          _errorDialog = null;
        });
    }

  };

  var newId = function(){
    this.$reqId = this.$name + '-' + new Date().getTime();
    return this.$reqId;
  };
  var reset = function(){
    _.each(Object.keys(this), function(key){
      if (key.indexOf('$') == 0)
        return;

        var control = this[key];
        if (control.$error){
          _.each(control.$error, function(err, name){
              control.$setValidity(name, null);
          });
        }
    }.bind(this));
  }
  return {
    restrict: 'E',
    require: '^form',

    link: function (scope, element, attr, formController) {
      formController.$newId = newId.bind(formController);
      formController.$reset = reset.bind(formController);

      scope.$on('hdiv-validation-error', onValidationError.bind(this, formController));
    }
  };
})

.factory('hdivInterceptor', function($q, $rootScope) {

  var interceptRequest = function(config){
    if (config.headers['reqId'] || config.reqId){
     return true;
    }
    return false;
  }

  return {

    'request': function(config) {
      if (!interceptRequest(config))
        return config;

      config.reqId = config.headers['reqId'];
      delete config.headers['reqId'];

      return config;
    },
    'response': function(response){
      if (interceptRequest(response.config)){ 
        response.data = (typeof response.data === 'object' || response.data.length==0) ? response.data : JSON.parse(response.data);
      }
      return response;
    },
    'responseError': function(rejection) {
      if (rejection.status == 403){
        var errorData = (typeof response.data === 'object' || response.data.length==0) ? rejection.data : JSON.parse(rejection.data);
        $rootScope.$broadcast('hdiv-validation-error', {
          reqId: rejection.config.reqId,
          errorData: errorData
        });

        // :S
        rejection.status = 1000 + rejection.status;
      }
      return $q.reject(rejection);
    }
  };
});
