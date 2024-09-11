# CSED332 Software Design Method

## Setup

install java
```
sudo apt-get install openjdk-8-jdk
```
set environment variable(skipped here)

install coursier
```
curl -fL "https://github.com/coursier/launchers/raw/master/cs-x86_64-pc-linux.gz" | gzip -d > cs
chmod +x cs
./cs setup
cs install scala:2.11.5
```

## Troubleshooting(?)
> [error] java.io.FileNotFoundException: /PATH_TO_DIR/cs332-assign/recfun/project/project/target/config-classes/$497f6f333f0c57ec9d9a.cache (No such file or directory)

do

```
rm -rf ~/.sbt/boot/
rm -rf ~/.ivy2/cache/org.scala-lang/
rm -rf ~/.ivy2/cache/org.scala-sbt/
cd cs332-assign/recfun
sbt clean compile
sbt run
```
