SPRING HATEOAS FORMS SAMPLE APPLICATION
===============================================
Sample bank application showing the integration between Spring Mvc and [spring-hateoas-forms](https://github.com/hateoas-forms/spring-hateoas-forms).

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
