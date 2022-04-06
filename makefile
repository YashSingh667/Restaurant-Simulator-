all:
	javac -d classes/ -cp classes/ IllegalNumberException.java
	javac -d classes/ -cp classes/ MMBurgersInterface.java
	javac -d classes/ -cp classes/ MMBurgers.java
	javac -d classes/ -cp classes/ TestCase3.java
	java -cp classes/ TestCase3

clean:
	rm classes/IllegalNumberException.class classes/MMBurgersInterface.class classes/MMBurgers.class classes/Tester1.class
 
