# 302462825 204727408
# gaashor topazsh1
compile: bin
	find src -name "*.java" > sources.txt
	javac -d bin -cp biuoop-1.4.jar @sources.txt
run:	
	java -cp biuoop-1.4.jar:bin:resources gameplay/Ass6Game
jar:
	jar -cfm ass6game.jar Manifest.txt -C bin . -C resources .
bin:
	mkdir bin