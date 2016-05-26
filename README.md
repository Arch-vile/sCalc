# sCalc
Scala - Play Calculator

== Description ==

Your task is to setup a simple web service to implement a calculator. The service offers an endpoint that reads a string input and parses it. It should return either an HTTP error code, or a solution to the calculation in JSON form.

An example calculus query:
- Original query: 2 * (23/(3*3))- 23 * (2*3)
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

# Derivations from spec
* Returned JSON is correctly formatted by having variable names in double quotes

