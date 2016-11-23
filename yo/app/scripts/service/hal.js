'use strict';
(function () {
	

function HalDoc(resource, curiePrefix){
	this._embeddedProperty = '_embedded';
	this._linksProperty = '_links';
	this._curiePrefix = curiePrefix || '';
	this._resource = resource;
}

HalDoc.prototype = {
	 rel: function(rel){
		  if (!rel || rel === 'self'){
			  return 'self';
		  }
		  else if (rel.indexOf(':') != -1){
			  return rel;
		  }
		  else{
			  return this._curiePrefix + rel;
		  } 
	  },
	  embedded: function(rel){
		  return this._resource[this._embeddedProperty] ? this._resource[this._embeddedProperty][this.rel(rel)] : null;
	  },
	  link: function(rel){
		  return this._resource[this._linksProperty][this.rel(rel)];
	  },
	  href: function(rel){
		  return this.link(rel).href;
	  },
	  buildDocUrl: function(rel){
		  var orRel = rel;
		  rel = this.rel(rel);
		  
		  var curie = _.findWhere(this._resource[this._linksProperty]['curies'], function(curie){
			  return curie.name === this._curiePrefix;
		  });
		  var o = {rel: orRel, href: this.href(rel)}
		  
		  // ejem ejem
		  console.log("PREVIOUS:"+curie.href);
		  return curie.href.replace('{rel}', o.rel);
	  }
};


function HalFormDoc(resource, curiePrefix){
	HalDoc.call(this, resource, curiePrefix);	
	this._templatesProperty = '_templates';
};

HalFormDoc.prototype = Object.create(HalDoc.prototype, {
	template: {
		value: function(templateName){
		  templateName = templateName || 'default';
		  return this._resource[this._templatesProperty][templateName];
		}
	},
	property: {
		value: function(propertyName, templateName){
		  var template = this.template(templateName);
		  return _.find(template.properties, function(property){
			  return property.name === propertyName;
		  });
	  }
	},
	values: {
		value: function(propertyName, templateName){
			var property = this.property(propertyName, templateName);
			if (!property.suggest){
				return null;
			}
			
			if (property.suggest.embedded){
				return this.embedded(property.suggest.embedded);
			}else{
				return property.suggest;
			}
		}
	}
});
HalFormDoc.prototype.constructor = HalFormDoc;

angular.module('bankApp')
  .factory('HAL', function(traverson, Config) {
	  
	  var _curiePrefix = Config.curiePrefix ? Config.curiePrefix + ':' : '';
	  
	  return {
		  rel: function(rel){
			  return new HalDoc(null, _curiePrefix).rel(rel);
		  },
		  embedded: function(resource, rel){
			  return new HalDoc(resource, _curiePrefix).embedded(rel);
		  },
		  res: function(resource){
			  return new HalDoc(resource, _curiePrefix);
		  },
		  form: function(resource){
			  return new HalFormDoc(resource, _curiePrefix);
		  },
		  possibleValues: function(rel){
			  return function(resource){
				  var template = this.form(resource);
				var property = template.property(rel);
				if (property.suggest){
					if (property.suggest.embedded){
						return template.embedded(rel);
					}else{
						return property.suggest;
					}
				}
				return null;
			  }
		  }
	  };
  });

}());