## Report experiment 2 DAT250

In this report i will go through the different steps to explain what i have done. 

First of all, an interesting experiment. It was challenging and a bit tricky. 

### Step 0
I chose to use Bruno, as I was not familiar with that program and it was used in the lectures. It was easy to download 
and set up.

### Step 1
Used the same repository as first assignment

### Step 2
I started by creating simple Java beans for User, Vote, Poll and VoteOption. Here I had to make some choices, like what 
properties the beans should have. 
<br>
For Poll, which you can find [here](https://github.com/vetlemknutsen/DAT250-Experiments/blob/main/src/main/java/com/example/demo/domains/Poll.java), I chose to include:
* ID for identification
* String for the question 
* Instant for the publish date 
* Instant for valid date 
* User-object for user 
* Arraylist of options as a poll can have 2 or more voteoptions

For User, which you can find [here](https://github.com/vetlemknutsen/DAT250-Experiments/blob/main/src/main/java/com/example/demo/domains/User.java), I chose to include: 
* String for username
* String for email
* List of the users Poll's 
* List of the users votes

For Vote, which you can find [here](https://github.com/vetlemknutsen/DAT250-Experiments/blob/main/src/main/java/com/example/demo/domains/Vote.java), I chose to include:
* Instant for publish date
* User for the user who voted, as a vote has one user
* VoteOption for which option the vote includes, as a vote can have 1 voteoption

For VoteOption, which you can find [here](https://github.com/vetlemknutsen/DAT250-Experiments/blob/main/src/main/java/com/example/demo/domains/VoteOption.java), I chose to include: 
* String for the caption
* int for the presentationOrder
* Poll for the poll it belongs to, as a VoteOption have 1 poll connected
* List of Vote's for all the options votes, as a VoteOpting can have multiple votes

For the [PollManager](https://github.com/vetlemknutsen/DAT250-Experiments/blob/main/src/main/java/com/example/demo/manager/PollManager.java), I chose to have a hashmap consisting of User-objects and a list of their Poll's. 
I think it is best to keep the users as keys, as a user can have multiple polls and if the polls were the keys, 
a user would have to register with a poll. I think users should be able to register without having a poll.
Also, if the user were the key, when creating a new poll - the last poll connected to that user would be replaced.

As mentioned, the model contains association cycles. Therefore, I have used @JsonBackReference, @JsonManagedReference 
and @JsonIgnore in the Poll, User and VoteOption domains. An example is VoteOption which references a Poll, and the Poll 
contains a list of VoteOptions, so I used @JsonBackReference for the Poll in VoteOption and @JsonManagedReference for the
list of VoteOption's in Poll.

I tried to use @Getters and @Setters in the beans but for some reason it didn't work, so i auto-generated the getters 
and setters with intelliJ's built in functionality.

### Step 3
For this step, I designed test scenarios in Bruno. I did this by identifying which functionality I want and then
making requests for testing later. 

### Step 4
This was the most time-consuming step. I made controllers for one domain at a time. I implemented the functionality, 
tested it in Bruno, and continued. It was challenging as I may have complicated things a bit too much. 
I found it hard, because I made the choice to have most of the handlers have username as a parameter, which meant I had
to find the User object in a loop. I was not able to test it with a user as the parameter, but this would make the 
implementation much easier. The implementation itself was not too hard, but as I said, I might have complicated things a bit too much. 
Also, I am not happy with how much functionality is in the handlers itself. I would refactor the code if I had more time 
working on this project. I could also have included paths, but didn't find it necessary. But, in the end, the functionality works. 
<br>
PollController -> [PollController](https://github.com/vetlemknutsen/DAT250-Experiments/blob/main/src/main/java/com/example/demo/controller/pollController.java)
<br>
UserController -> [UserController](https://github.com/vetlemknutsen/DAT250-Experiments/blob/main/src/main/java/com/example/demo/controller/userController.java)
<br>
VoteController -> [VoteController](https://github.com/vetlemknutsen/DAT250-Experiments/blob/main/src/main/java/com/example/demo/controller/voteController.java)
<br>
VoteOptionController -> [VoteOptionController](https://github.com/vetlemknutsen/DAT250-Experiments/blob/main/src/main/java/com/example/demo/controller/voteOptionController.java)

### Step 5
I always find making tests difficult. I chose to try to make tests using RestClient, which was fine for the Poll and User 
controllers, but I found it hard to make for Vote and VoteOption, and I was not able to do it. 
But the tests works and pass for [Poll](https://github.com/vetlemknutsen/DAT250-Experiments/blob/main/src/test/java/com/example/demo/controller/PollControllerTest.java) and [User](https://github.com/vetlemknutsen/DAT250-Experiments/blob/main/src/test/java/com/example/demo/controller/UserControllerTest.java). 


### Step 6
Did this, it worked, all handlers showed. Could spend more time documenting the handlers. 



