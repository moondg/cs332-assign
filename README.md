# CSED332 Software Design Method

## Setup

install java
```
sudo apt-get install openjdk-8-jdk
```
set environment variable
```
java --version
which java
readlink -f PATH_FROM_COMMAND_ABOVE
```
open ~/.bashrc and paste
```
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
export PATH=${PATH}:${JAVA_HOME}/bin
```

apply ~/.bashrc in current terminal(optional)
```
source ~/.bashrc
```

check
```
echo $JAVA_HOME
```

install coursier
```
curl -fL "https://github.com/coursier/launchers/raw/master/cs-x86_64-pc-linux.gz" | gzip -d > cs
chmod +x cs
./cs setup
cs install scala:2.11.5
```

you may see output below when excuting ```./cs setup```

> Checking if a JVM is installed<br>
> Found a JVM installed under /usr/lib/jvm/java-8-openjdk-amd64.

## Troubleshooting
+ > [error] java.io.FileNotFoundException: /PATH_TO_DIR/cs332-assign/recfun/project/project/target/config-classes/$497f6f333f0c57ec9d9a.cache (No such file or directory)

    do

    ```
    rm -rf ~/.sbt/boot/
    rm -rf ~/.ivy2/cache/org.scala-lang/
    rm -rf ~/.ivy2/cache/org.scala-sbt/
    cd cs332-assign/recfun
    sbt clean compile
    sbt run
    ```

+ > [error] [launcher] error during sbt launcher: java.lang.UnsupportedOperationException: The Security Manager is deprecated and will be removed in a future release

    see

    https://stackoverflow.com/questions/76151072/error-during-sbt-launcher-java-lang-unsupportedoperationexception-the-security
