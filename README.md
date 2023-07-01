# imseProject
terminal commands to containerize mongo :
1) docker pull mongo
2) docker run --name mongodb-container -p 27017:27017 -d mongo

- i did the same for sql i think

terminal command to containerize whole app :
1) docker build -t imse-docker .
2) docker run -p 9999:9999 imse-docker

- check application.properties when switching from running locally to docker run
