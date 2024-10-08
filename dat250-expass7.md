### Set up 
Docker was already installed from experiment 1 

### Using a Dockerized application: PostgreSQL
Pullin PostgreSQL went well, and I decided to use port 5432 as this is the standard port for PostgreSQL. 
Then I set an user, password and database name for the environment variables. Like this: 
<br>
![](images/docker_run.png)

Running *docker ps*:
![](images/docker_ps.png)

I connected the database to the IntelliJ database client and it went well, after I figured I needed to change the url of "hibernate.connection.url".
The tests passed after I figured there was an error in the task for the properties. The driver class in the task was wrong.

Then, the schemas was created and I copied the content of the schema.up.sql into the SQL console to create the tables. 


### Building your own dockerized application
I used the temurin image to containerize my application. 
The Dockerfile ended up like this: [Dockerfile](https://github.com/vetlemknutsen/DAT250-Experiments/blob/main/Dockerfile)

In the builder stage I use JDK22 and set the working directory, then copy the gradle build files and source code. 
For the runtime stage, I use Alpine JDK 22. Then I create a user and a group. 
In the end I run the application as the new user, while exposing port 8080, and starting the app.

I use this multi-stage build to make the image slim and I create a non-root user which the app is run as.
