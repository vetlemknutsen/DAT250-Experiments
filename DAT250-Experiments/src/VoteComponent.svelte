<script>
    import {onMount} from 'svelte';
    import { usernameStore } from './store';
    import {navigate} from "svelte-routing";


    let polls = [];
    let selectedPoll = null;
    let votesPerOption = {};
    let selectedVote = null;

    async function fetchPolls() {
        const url = `/v1/poll/getAll`;

        const response = await fetch(url, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
        });

        if (response.ok){
            const data = await response.json();
            polls = data;
            console.log(data)
        }
        else{
            const data = await response.json();
            console.log(data.message)
        }

    }

    async function getVotes(presentationOrder){

        const params = new URLSearchParams({
            pollId: selectedPoll.id,
            presentationOrder: presentationOrder
        });

        const url = `/v1/voteOption/get?${params.toString()}`;

        const response = await fetch(url, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
        });

        if (response.ok){
            const votesData = await response.json();
            votesPerOption[presentationOrder] = votesData.votes || [];
        }
        else{
            const data = await response.json();
            console.log(data.message)
        }


    }


    async function vote(pollId, voteOption) {

        const params = new URLSearchParams({
            username: $usernameStore,
            pollId: pollId,
            voteOption: voteOption
        });
        console.log("vote" + $usernameStore)

        const url = `/v1/vote/vote?${params.toString()}`;

        const response = await fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
        });

        if (response.ok){
            console.log("Success")
            for (const option of selectedPoll.options) {
                await getVotes(option.presentationOrder);
            }
        }
        else {
            const data = response.json();
            console.log(data.message)
        }

    }

    function selectPoll(id) {
        for (const pollList of polls) {
            for (const poll of pollList){
                if (poll.id === id) {
                    selectedPoll = poll;
                    return;
                }
        }   }
        selectedPoll = null;
    }

    function nullSelectedOption(){
        selectedPoll = null;
    }

    function selectVote(presentationOrder){
        selectedVote = presentationOrder
        console.log(selectedVote)
    }

    onMount(() => {
        fetchPolls();
    });

</script>



<div class="poll">
    {#if polls && polls.length > 0}
        {#if !selectedPoll}
            <div class="header-container">
                <h2>Polls</h2>
                <button class="poll-button" on:click={() => navigate("/create-poll")}>New poll</button>
            </div>
            {#each polls as pollList}
                {#each pollList as poll}
                    <div class="poll-box">
                        {poll.question}
                        <button class="button" on:click={() => selectPoll(poll.id)}>Choose Poll</button>
                    </div>
                {/each}
            {/each}
        {:else}
            <div class="poll-box">
                <h3>{selectedPoll.question}</h3>
                <p style="text-align: left">Choose between:</p>
                {#each selectedPoll.options as options}
                    <div class="vote-box" style="text-align: left" class:selected={options.presentationOrder === selectedVote} on:click={() => selectVote(options.presentationOrder)}>{options.caption}
                    {#await getVotes(options.presentationOrder)}
                        <p>Loading votes...</p>
                    {:then votes}
                        <p style="text-align: right">Votes: {votesPerOption[options.presentationOrder]?.length ?? 0}</p>
                    {/await}
                    </div>
                {/each}

                <button class="button" on:click={() => vote(selectedPoll.id, selectedVote)}>Vote</button>
                <button class="button" on:click={nullSelectedOption}>Back</button>
            </div>
        {/if}
    {:else}

        <h3>No polls, create a new one to get started</h3>
        <button on:click={() => navigate("/create-poll")}>New poll</button>

    {/if}
</div>


<style>
    .poll {
        background-color: lightblue;
        padding: 30px;
        border-radius: 8px;
        margin-bottom: 60%;
        width: 300%;
        margin-left: -100%;
    }
    .poll-box {
        background-color: white;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        margin-bottom: 20px;
        display: flex;
        flex-direction: column;
        gap: 10px;
    }

    .vote-box {
        display: flex;
        justify-content: space-between;
        padding: 20px ;
        border-radius: 8px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }

    .vote-box:hover {
        background-color: lightgray;
    }

    .button:hover{
        background-color: lightgray;
    }

    .selected {
        background-color: lightgrey;
    }

    .header-container {
        display: flex;
        align-items: center;
        gap: 10px;
        margin-left: 47%;
    }

    .poll-button {
        margin-left: 67%;
        padding: 10px 20px;
        background-color: white;
        color: black;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }

    .poll-button:hover {
        background-color: lightgray;
    }

</style>