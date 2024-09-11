## Report experiment 3 DAT250

In this report i will go through the different steps to explain what i have done. 
This was a fun experiment and I learnt a lot about connecting backend and frontend.

FYI - The instructions mentions in the end: a link to your code for experiments 1-2 above.
I don't really think it makes sense to add links to step 1? So I added links where I found it useful.

Link to repo startpage: [home](https://github.com/vetlemknutsen/DAT250-Experiments/tree/main)

### Step 1 
Node.js and NPM were already installed.
I created the frontend project with
```
npm init vite@latest
```
I encountered a problem her because in the task description there was a spelling error. 
And since I just copied the command I didn't notice it, and I got an error. 

Next, I chose to use Svelte since I hadn't used this before and wanted to try it. 
This set up part went well. 

### Step 2
I did some research about how to create components in Javascript and Svelte, and made three classes: 
[CreatePollComponent](https://github.com/vetlemknutsen/DAT250-Experiments/blob/main/DAT250-Experiments/src/CreatePollComponent.svelte), 
[CreateUserComponent](https://github.com/vetlemknutsen/DAT250-Experiments/blob/main/DAT250-Experiments/src/CreateUserComponent.svelte) 
and [VoteComponent](https://github.com/vetlemknutsen/DAT250-Experiments/blob/main/DAT250-Experiments/src/VoteComponent.svelte). 
They were going to handle poll creation, user creating and voting respectively.
I started by just creating simple user interfaces which I could continue working on in later steps.
The process was quite basic and resulted in simple div's. 
Then to connect the components, I used Router in [App.svelte](https://github.com/vetlemknutsen/DAT250-Experiments/blob/main/DAT250-Experiments/src/App.svelte), 
which makes it possible to connect the components to paths, 
like localhost:8080/create-poll.

I could have used more time on the CSS, but didn't find it necessary for this project. 

### Step 3
Enabling CORS went well 

### Step 4
This was the tricky part. Now I had to connect the frontend to the backend. I thought this was going to be a easy process, but 
i stumbled upon some challenges. I started with the [CreateUserComponent](https://github.com/vetlemknutsen/DAT250-Experiments/blob/main/DAT250-Experiments/src/CreateUserComponent.svelte), 
which was the simplest one. 
<br>First I had to build the request with fetch to the create user backend endpoint, then connect the input field and button to
that request. So when the user entered their username and clicked the button, the request was sent. 
If the request was successfull, I used svelte-routing and navigate to continue to the polls. 
<br> 
Next up was [VoteComponent](https://github.com/vetlemknutsen/DAT250-Experiments/blob/main/DAT250-Experiments/src/VoteComponent.svelte), 
this was the most tricky one. First I had to implement the method to fetch all polls, which is called
in onMount(). Then, when all polls successfully showed, I had to find a way to choose a poll and vote on it. 
I made two request, one to get all the poll's options, and one to vote on an option. Also, I needed to show the amount of votes per option. 
When implementing the voting request I encountered a problem as it required a username. So after some research I figured to make 
a [store.js](https://github.com/vetlemknutsen/DAT250-Experiments/blob/main/DAT250-Experiments/src/store.js) 
file to store the username across the components. Then it could be set in the CreateUserComponent and 
retrieved in the VoteComponent. 
The end product for this component
ended up having many if's and #each's in the div section, which I don't think is optimal. 
<br>At last was [CreatePollComponent](https://github.com/vetlemknutsen/DAT250-Experiments/blob/main/DAT250-Experiments/src/CreatePollComponent.svelte), 
which went alright. It was a bit tricky to build the body for the request to create a poll. 
It was also difficult to have an option to add as many options as you want, but it worked in the end. 

### Step 5
I also encountered some problems here, as the instructions never mentioned that I had to 
remove /assets from the index.html file to get it to work. After a while, I figured out this, and it worked fine!
