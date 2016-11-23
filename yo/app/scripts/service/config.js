'use strict';

angular.module('bankApp')
  .constant('Config', {
    basePath: '/spring-hateoas-forms-sample-bank/api',
    curiePrefix: 'halforms',
    url: function(url){
      return this.basePath + url;
    }
  });
