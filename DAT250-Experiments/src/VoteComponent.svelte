<script>
    import { onMount } from 'svelte';


    let polls = [];
    let selectedPoll = null;

    async function fetchPolls() {
        const url = `http://localhost:8080/v1/poll/getAll`;

        const response = await fetch(url, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
        });

        if (response.ok){
            const data = await response.json();
            polls = data[0];
            console.log(data)
        }
        else{
            const data = await response.json();
            console.log(data.message)
        }

    }

    async function vote() {
    }

    function selectPoll(id){
        selectedPoll = polls.find(poll => poll.id === id);
    }

    onMount(() => {
        fetchPolls();
    });
</script>

<style>
    .poll {
        background-color: lightblue;
        padding: 30px;
        border-radius: 8px;
        margin-bottom: 60%;
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

    .button:hover{
        background-color: lightgray;
    }

</style>

<div class="poll">
    {#if polls.length > 0}
        {#if !selectedPoll}
            <h2>Polls</h2>
            {#each polls as poll}
                {#if poll != null}
                    <div class="poll-box">
                        {poll.question}
                        <button class="button" on:click={() => selectPoll(poll.id)}>Choose Poll</button>
                    </div>
                {/if}
            {/each}
        {:else}
            <div class="poll-box">
                <h3>{selectedPoll.question}</h3>
                <p style="text-align: left">Choose between:</p>
                {#each selectedPoll.options as options}
                    <p class="poll-box">{options.caption}</p>
                {/each}

                <button class="button">Vote</button>
                <button class="button">Back</button>
            </div>
        {/if}
    {/if}
</div>