A simple and secure checker of whether your password has been compromised in a data breach
------------------------------------------------------------------------------------------
See: https://haveibeenpwned.com/

The check is done using [k-Anonymity](https://www.troyhunt.com/ive-just-launched-pwned-passwords-version-2/#cloudflareprivacyandkanonymity) method

#### Usage (command-line):
```
$ ./hibp-checker.sh
```
or
```
$ mvn install
$ java -jar target/hibp-checker-1.0.jar
```