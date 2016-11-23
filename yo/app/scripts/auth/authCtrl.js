angular.module('bankApp')
  .controller('AuthCtrl', function () {

  })
  .controller('LoginCtrl', function(AuthService, $rootScope, $state){
    if (AuthService.principal()) {
      $state.go('dashboard');
    } else {
      $rootScope.showLoginDialog = true;
    }
  });
