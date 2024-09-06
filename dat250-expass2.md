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
For Poll, I chose to include:
* ID for identification
* String for the question 
* Instant for the publish date 
* Instant for valid date 
* User-object for user 
* Arraylist of options as a poll can have 2 or more voteoptions

For User, I chose to include: 
* String for username
* String for email
* List of the users Poll's 
* List of the users votes

For Vote, I chose to include:
* Instant for publish date
* User for the user who voted, as a vote has one user
* VoteOption for which option the vote includes, as a vote can have 1 voteoption

For VoteOption, I chose to include: 
* String for the caption
* int for the presentationOrder
* Poll for the poll it belongs to, as a VoteOption have 1 poll connected
* List of Vote's for all the options votes, as a VoteOpting can have multiple votes

For the PollManager, I chose to have a hashmap consisting of User-objects and a list of their Poll's. 
I think it is best to keep the users as keys, as a user can have multiple polls and if the polls were the keys, 
a user would have to register with a poll. I think users should be able to register without having a poll.
Also, if the user were the key, when creating a new poll - the last poll connected to that user would be replaced.

As mentioned, the model contains association cycles. Therefore, I have used @JsonBackReference, @JsonManagedReference 
and @JsonIgnore in the Poll, User and VoteOption domains. An example is VoteOption which references a Poll, and the Poll 
contains a list of VoteOptions, so I used @JsonBackReference for the Poll in VoteOption and @JsonManagedReference for the
list of VoteOption's in Poll. 

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

### Step 5
I always find making tests difficult. I chose to try to make tests using RestClient, which was fine for the Poll and User 
controllers, but I found it hard to make for Vote and VoteOption, and I was not able to do it. 
But the tests work and pass for Poll and User. 

### Step 6
Did this, it worked, all handlers showed. Could spend more time documenting the handlers. 



