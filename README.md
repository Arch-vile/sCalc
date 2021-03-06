# sCalc 2000
## Scala - Play Calculator
Learning project for Scala and Play framework. Provides web service to evaluate simple arithmetic expressions.

## Implementation
Project contains two independent implementations for arithmetic calculations. First is implemented using Scala's combinatory parsers (full of potential there!) and the second follows the traditional Shunting-yard algorithm (https://en.wikipedia.org/wiki/Shunting-yard_algorithm) with simple Reverse Polish notation evaluator.

# Running application
Application should be running on Heroku
* https://scalc2000.herokuapp.com/calculus?query=_expression_&type=_polish/grammar_
In which the _expression_ is the arithmetic expression in Base64 encoded string and _type_ is the type of implementation to use (grammar is default).

Here are some urls to test with:
* 1+2x3 https://scalc2000.herokuapp.com/calculus?query=MSsyKjM=
* (23x3)/5x(2+3+2+3x(21/7))-(4×(4×5)) = 140.8 https://scalc2000.herokuapp.com/calculus?query=KDIzKjMpLzUqKDIrMysyKzMqKDIxLzcpKS0oNCooNCo1KSk=
* 4 / (2-2) https://scalc2000.herokuapp.com/calculus?query=NCAvICgyLTIp
* 1+(2x4/4+2(3-1x3)/2/3+3)x(3/-2-4+1x1x-4((2x2x(1-5))x2/4)) = 133,5 https://scalc2000.herokuapp.com/calculus?query=MSsoMio0LzQrMigzLTEqMykvMi8zKzMpKigzLy0yLTQrMSoxKi00KCgyKjIqKDEtNSkpKjIvNCkp
* (2.1+2)/3.2x.3x0.2 https://scalc2000.herokuapp.com/calculus?query=KDIuMSsyKS8zLjIqLjMqMC4y
* 1+a https://scalc2000.herokuapp.com/calculus?query=MSth
* encodeError https://scalc2000.herokuapp.com/calculus?query=n

## Deviations from spec
* Returned JSON is correctly formatted by having variable names in double quotes

# Description

Your task is to setup a simple web service to implement a calculator. The service offers an endpoint that reads a string input and parses it. It should return either an HTTP error code, or a solution to the calculation in JSON form.

An example calculus query:
- Original query: 2 x (23/(3x3))- 23 x (2x3)
- With encoding: MiAqICgyMy8oMyozKSktIDIzICogKDIqMyk=


== API Description == 

- Endpoint:
  * GET /calculus?query=[input]
  * The input can be expected to be UTF-8 with BASE64 encoding
  * Return:
    - On success: JSON response of format:
      { error: false, result: number }
    - On error: Either a HTTP error code or:
      { error: true, message: string }

- Supported operations: + - * / ( ) 


== Technical constraints ==

There are some constraints that you need to follow. These are followed by some tips and ideas that you can choose to follow if you wish.

Required:
- As discussed, the solution should Scala based. 
- The API has to be testable online 
- The source code should be shared. For example, GitHub is a good option.

Tips and ideas:
- You can use any suitable libraries or frameworks to help you.
- Heroku might be a good place to publish your service.
- You might want to google for "Reverse Polish Notation".










