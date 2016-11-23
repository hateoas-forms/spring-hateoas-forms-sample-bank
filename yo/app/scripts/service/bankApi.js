'use strict';

angular.module('bankApp')
  .factory('bankApi', function(traverson, Config) {
    traverson.registerMediaType(TraversonJsonHalAdapter.mediaType, TraversonJsonHalAdapter);
    var defaultHeaders = {
      'accept': 'application/hal+json',
      'Content-Type': 'application/hal+json'
    };

    return function(url, options){
      url = url || (Config.basePath + '/');

      var reqId = null;
      if (options && options.form){
        reqId = {'reqId':  options.form.$newId()};
      }

      var preHeaders = angular.extend(reqId || {}, defaultHeaders);
      var headers = options && options.headers ? angular.extend(preHeaders, options.headers) : preHeaders;

      return traverson
        .from(url)
        .useAngularHttp()
        .jsonHal()
        .withRequestOptions({
          headers: headers
        })
        .newRequest();
    };
  });
