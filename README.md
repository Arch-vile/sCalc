# sCalc
Scala - Play Calculator

== Description ==

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

# Deviations from spec
* Returned JSON is correctly formatted by having variable names in double quotes

# Running application
Should be running on Heroku. Here are some urls to test with:
* 1+2x3 https://scalc2000.herokuapp.com/calculus?query=MSsyKjM=
* (23x3)/5x(2+3x(21/7))-(4x(4x5)) https://scalc2000.herokuapp.com/calculus?query=KDIzKjMpLzUqKDIrMyooMjEvNykpLSg0LSg0LTUpKQ==
* 4 / (2-2) https://scalc2000.herokuapp.com/calculus?query=NCAvICgyLTIp
* 1+(2*4/4+2(3-1*3)/2/3+3)*(3/-2-4+1*1*-4((2*2*(1-5))*2/4)) = 133,5 https://scalc2000.herokuapp.com/calculus?query=MSsoMio0LzQrMigzLTEqMykvMi8zKzMpKigzLy0yLTQrMSoxKi00KCgyKjIqKDEtNSkpKjIvNCkp
* (2.1+2)/3.2x.3x0.2 https://scalc2000.herokuapp.com/calculus?query=KDIuMSsyKS8zLjIqLjMqMC4y
* 1+a https://scalc2000.herokuapp.com/calculus?query=MSth
* encodeError https://scalc2000.herokuapp.com/calculus?query=n

# Known issues
* imaginary numbers not supported :)






