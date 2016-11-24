SPRING HATEOAS FORMS SAMPLE APPLICATION
===============================================
Sample bank application showing the integration between [spring-hateoas](https://github.com/spring-projects/spring-hateoas) and [spring-hateoas-forms](https://github.com/hateoas-forms/spring-hateoas-forms).

## Getting Started

The sample application publishes a REST API with services for a Bank application and an Angular client for the REST API.
This services are implemented using [spring-hateoas](https://github.com/spring-projects/spring-hateoas) and HAL document format.

## Entry point

Once the user authenticates in the application the entry point service is invoked in the root path of the API, [http://localhost:8080/spring-hateoas-forms-sample-bank/api/](http://localhost:8080/spring-hateoas-forms-sample-bank/api/).

The resulting JSON shows the available service links the client can invoke.

	{
	  "_links": {
	    "halforms:account": {
	      "href": "http://localhost:8080/spring-hateoas-forms-sample-bank/api/account"
	    },
	    "halforms:cashaccounts": {
	      "href": "http://localhost:8080/spring-hateoas-forms-sample-bank/api/cashaccounts"
	    },
	    "halforms:creditaccounts": {
	      "href": "http://localhost:8080/spring-hateoas-forms-sample-bank/api/creditaccounts"
	    },
	    "halforms:make-transfer": {
	      "href": "http://localhost:8080/spring-hateoas-forms-sample-bank/api/transfer"
	    },
	    "halforms:list-transfers": {
	      "href": "http://localhost:8080/spring-hateoas-forms-sample-bank/api/transfer"
	    },
	    "halforms:list-after-date-transfers": {
	      "href": "http://localhost:8080/spring-hateoas-forms-sample-bank/api/transfer/filter{?dateFrom,dateTo,status}",
	      "templated": true
	    },
	    "halforms:logout": {
	      "href": "http://localhost:8080/spring-hateoas-forms-sample-bank/j_spring_security_logout"
	    },
	    "curies": [
	      {
	        "href": "http://localhost:8080/spring-hateoas-forms-sample-bank/doc/{rel}",
	        "name": "halforms",
	        "templated": true
	      }
	    ]
	  }
	} 

There are several services, each one identified with a *rel* value:

- *halforms:account*, *halforms:cashaccounts* and *halforms:creditaccounts*: Services to read existing accounts.
- *halforms:list-transfers* and *list-after-date-transfers*: List existing transfers.
- *halforms:make-transfer*: Create new transfer.
- *halforms:logout*: Application logout.

Executing these links the server will response with data and more links to more resources and actions.

## Transfer creation

Let's focus in a concrete operation, new credit transfer (rel *halforms:make-transfer*). The reason to focus in this service is to analyze the usage of [spring-hateoas-forms](https://github.com/hateoas-forms/spring-hateoas-forms) library to define the format of the data required to create a new transfer.

The transfer creation is done in two steps.

1. A request to retrieve the format of the transfer creation form, including it's fields and constraints.
2. Another request to create the transfer itself, usually a POST.

### Transfer form preparation

In order to know the required fields to create a transfer a request must be done to the server. The url of the request is generated with the *curies* url in the root of the service.
 
- curies url: "http://localhost:8080/spring-hateoas-forms-sample-bank/doc/{rel}"
- link rel: "halforms:make-transfer"

Combining the two values, a url is generated:
- http://localhost:8080/spring-hateoas-forms-sample-bank/doc/make-transfer

The [DocController.get()](https://github.com/hateoas-forms/spring-hateoas-forms-sample-bank/blob/master/src/main/java/com/github/hateoas/forms/samples/controllers/DocController.java#L36) method processes the request to this url and uses the AfforcenceBuilder to generate the response.

	AffordanceBuilder transferBuilder = linkTo(methodOn(TransferController.class).transfer(new Transfer(), null, principal));
	builder.and(transferBuilder);

In the previous code AffordanceBuilder class is used to create a link to [TransferController.transfer(..)](https://github.com/hateoas-forms/spring-hateoas-forms-sample-bank/blob/master/src/main/java/com/github/hateoas/forms/samples/controllers/TransferController.java#L46) method.

This is the signature of the method:

	@RequestMapping(value = "/api/transfer")
	public class TransferController {
	
		@RequestMapping(method = RequestMethod.POST)
		public ResponseEntity<Resource<Transfer>> transfer(@Valid @RequestBody final Transfer transfer, final BindingResult bindingResult, final Principal principal) {
			...
		}
	}		

The controller method is executed with the url */api/transfer* with the method *POST*, and it is used to create the transfer.
The *Transfer* object is used for binding and the content of the submit will be included into it.

	public class Transfer {
	
		private double amount;
	
		private String toAccount;
	
		...
	}

The AffordanceBuilder in the DocController.get(..) method will use this Transfer bean to infer the format of the service. 
For example, for the previous minimal Transfer object, the following document will be generated, including *amount* and *toAccount* fields:

	{
	  "_links": {
	    "self": {
	      "href": "http://localhost:8080/spring-hateoas-forms-sample-bank/doc/make-transfer"
	    },
	    "curies": [
	      {
	        "href": "http://localhost:8080/spring-hateoas-forms-sample-bank/doc/{rel}",
	        "name": "halforms",
	        "templated": true
	      }
	    ]
	  },
	  "_templates": {
	    "post": {
	      "method": "POST",
	      "properties": [
	        {
	          "name": "amount",
	          "readOnly": false,
	          "value": "0.0",
	          "required": true
	        },
	        {
	          "name": "toAccount",
	          "readOnly": false,
	          "required": true
	        }
	      ]
	    }
	  }
	}

### Transfer creation

Once the preparation request is executed, the client knows which data to send to create a new transfer. In the previous minimal example code only two fields are required, *amount* and *toAccount*, they are mandatory (required: true) and can be modified (readOnly: false).

With this information the client can show a form to the user, with two *<input type="text" >* fields, and validate that they are not empty before sending to the server.

As defined in the previous request, HTTP method of the creation request should be POST and the url is the original link with the rel *make-transfer*:
	http://localhost:8080/spring-hateoas-forms-sample-bank/api/transfer

And an example valid request payload:

	{
	  "toAccount": "10669803404133150948",
	  "amount": "100"
	}

This request will response with a 201 code, so the transfer is created correctly.

## Possible values for a field

In a form it is common to have a field where the user can select a value in a list of accepted ones.
For example, this could be generated with a `<select>` field in the client side. In a Hateoas service it is possible to include the possible values in the definition of the field returned by the server in the form preparation request.

For example, if a *type* property is added to the *Tranfer* class:

	public class Transfer {
	
		private double amount;
	
		private String toAccount;
	
		private TransferType type; 
		
		...
	}

Where *TransferType* is a enum with two values:

	public enum TransferType {
		NATIONAL, INTERNATIONAL
	}

The output of the form preparation request will include these field and it's possible values:

	{
	  "_links": {
	    "self": {
	      "href": "http://localhost:8080/spring-hateoas-forms-sample-bank/doc/make-transfer"
	    },
	    "curies": [
	      {
	        "href": "http://localhost:8080/spring-hateoas-forms-sample-bank/doc/{rel}",
	        "name": "halforms",
	        "templated": true
	      }
	    ]
	  },
	  "_templates": {
	    "post": {
	      "method": "POST",
	      "properties": [
	        {
	          "name": "amount",
	          "readOnly": false,
	          "value": "0.0",
	          "required": true
	        },
	        {
	          "name": "toAccount",
	          "readOnly": false,
	          "required": true
	        },
	        {
	          "name": "type",
	          "readOnly": false,
	          "suggest": [
	            {
	              "value": "NATIONAL",
	              "prompt": "NATIONAL"
	            },
	            {
	              "value": "INTERNATIONAL",
	              "prompt": "INTERNATIONAL"
	            }
	          ]
	        }
	      ]
	    }
	  }
	}

This way the client can create a `<select>` element with the suggested values, *NATIONAL* and *INTERNATIONAL*.
Note that in the example we are using HAL-FORMS hypermedia format where possible values are defined within "suggest" entity.

## Possible values for a field retrieved from the server
 
We will introduce a change on the *toAccount* field. It will be converted to a `<select>` like element where possible values are retrieved from the server in another request.
This way possible values are showed to the user as he/she keys the account number.

The field data for the transfer creation form preparation request will change and include the url for the filter request:

	{
	  "name": "toAccount",
	  "readOnly": false,
	  "suggest": {
	    "href": "http://localhost:8080/spring-hateoas-forms-sample-bank/api/cashaccounts{?filter}",
	    "prompt-field": "description"
	  }
	}

The url has a *filter* parameter that contains the text the user writes.
An example filter request:

	http://localhost:8080/spring-hateoas-forms-sample-bank/api/cashaccounts?filter=10
	
Response content, containing the suggested cash accounts, only one in this case:

	{
	  "_embedded": {
	    "halforms:cashAccounts": [
	      {
	        "id": "10669803404133150948",
	        "number": "10669803404133150948",
	        "username": "Tom Bogen",
	        "availableBalance": 3424.32,
	        "description": "Checking Account",
	        "_links": {
	          "self": {
	            "href": "http://localhost:8080/spring-hateoas-forms-sample-bank/api/cashaccounts/10669803404133150948"
	          }
	        }
	      }
	    ]
	  },
	  ...
	}

To be able to do it, some changes must be done to the *Transfer* class.

	public class Transfer {
	
		private double amount;
	
		private String toAccount;
	
		private TransferType type; 
		
		public String getToAccount() {
			return toAccount;
		}
	
		public void setToAccount(@Select(type = SuggestType.REMOTE, options = CashAccountFilteredOptions.class) final String toAccount) {
			this.toAccount = toAccount;
		}
		
		...
	}

A new annotation is added to the *toAccount* field setter, *@Select*.
It contains two properties:

- type: Suggested types, REMOTE in this case, because the possible values are retrieved from the server.
- options: Contains possible values or the way to obtain them. In this case a class that implements *Options* is included, [CashAccountFilteredOptions](https://github.com/hateoas-forms/spring-hateoas-forms-sample-bank/blob/799ea73c2a34aa2c09dc4373f44364c529d4ab1f/src/main/java/com/github/hateoas/forms/samples/facade/CashAccountFilteredOptions.java).

*CashAccountFilteredOptions* class:

	public class CashAccountFilteredOptions implements Options<String> {
		@Override
		public List<Suggest<String>> get(final String[] value, final Object... args) {
			Link link = AffordanceBuilder.linkTo(AffordanceBuilder.methodOn(CashAccountController.class).search(null, null)).withSelfRel();
			return SuggestImpl.wrap(Arrays.asList(link.getHref()), null, "description");
		}
	}
	
As in the transfer creation form generation, *AffordanceBuilder* class is used to create a *Link* object to the Controller method that will receive the filter request, *CashAccountController.search()*.
As previously showed and url to this Controller will be included in the field configuration.

	{
	  "name": "toAccount",
	  "readOnly": false,
	  "suggest": {
	    "href": "http://localhost:8080/spring-hateoas-forms-sample-bank/api/cashaccounts{?filter}",
	    "prompt-field": "description"
	  }
	}

