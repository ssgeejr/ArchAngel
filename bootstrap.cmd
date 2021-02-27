@ECHO OFF


Set "_CWD=%CD%"
Set "_start=0"
Set "_clean=0"


echo "bringing the system offline ... docker-compose down"
docker-compose down

:Loop
IF "%1"=="" GOTO Continue
	echo %1
	If "%1"=="start" (
		Set "_start=1"
		echo "...Application will be started upon successful build..."
	)
	
	If "%1"=="clean" (
		Set "_clean=1"
		echo "... Old images will be removed"
	)
	
	If "%1"=="up" (
		echo "docker-compose up -d"
		goto exit
	)
SHIFT
GOTO Loop

@REM echo "Do start: %_start%"
@REM echo "Do clean: %_clean%"

:Continue

echo "BUILDING ArchangeDB"
If %_clean%==1 (
	echo "cleaning archangeldb images"
	docker stop archangeldb
	docker rm archangeldb
	docker rmi -f archangeldb
)
cd archangeldb/docker
docker build --tag archangeldb .

cd %_CWD%


echo "BUILDING ArchangeMS"
If %_clean%==1 (
	echo "cleaning archangelms images"
	docker stop archangelms
	docker rm archangelms
	docker rmi -f archangelms
)
cd archangelms
del /F docker\archAngelService.jar
mvn clean package
cd docker
copy ..\target\archAngelService.jar .
docker build --tag archangelms .
cd %_CWD%



echo "BUILDING ArchangeUI"
If %_clean%==1 (
	echo "cleaning archangelui  images"
	docker stop archangelui
	docker rm archangelui
	docker rmi -f archangelui
 
)
cd archangelui
del /F docker\archAngelFrontEnd.jar
mvn clean package
cd docker
copy ..\target\archAngelFrontEnd.jar .
docker build --tag archangelui .
cd %_CWD%


echo "PULLING Dozzle"
docker pull amir20/dozzle

 
If %_start%==1 (
	echo "Application Built, starting application: docker-compose up -d"
    docker-compose up -d
	goto exit
)

echo "Application Built, you can start the system by executing: 'docker-compose up -d'"


:exit