<script>

    import {usernameStore} from "./store.js";
    import {navigate} from "svelte-routing";

    let question = '';
    let options = [''];
    let responseMessage =""

    function addOption() {
        options = [...options, ''];
    }
    function updateOption(index, value) {
        options[index] = value;
    }

    async function createPoll() {

        const pollData = {
            question: question,
            publishedAt: new Date().toISOString(),
            validUntil: new Date().toISOString(),
            creator: {
                username: $usernameStore
            },
            options: options.map((option, index) => ({
                caption: option,
                presentationOrder: (index + 1).toString()
            }))
        };

        const params = new URLSearchParams({
            username: $usernameStore
        });
        const url = `/v1/poll/create?${params.toString()}`;

        const response = await fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(pollData)
        });

        if (response.ok){
            console.log("success")
            responseMessage = "Poll created!"
        }
        else{
            const data = await response.json();
            console.log(data.message);
        }


    }
</script>


<div class="poll">
    <h2>Create New Poll</h2>
    <form on:submit|preventDefault={createPoll}>
        <label for="question">Question:</label>
        <input type="text" id="question" bind:value={question} required>

        {#each options as option, index}
            <label for={"option" + index}>Option {index + 1}:</label>
            <input type="text" id={"option" + index} bind:value={options[index]} on:input={e => updateOption(index, e.target.value)} required>
        {/each}

        <button type="button" on:click={addOption}>Add Another Option</button>

        <button type="submit" on:click={() => createPoll()}>Submit</button>

        <button class="button" on:click={() => navigate("/polls")}>Back</button>
        <p>{responseMessage}</p>
    </form>
</div>

<style>
    .poll {

    }

    form {
        padding: 20px;
        background-color: lightblue;
        border-radius: 8px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        width: 200%;
        margin-left: -50%;

    }

    label {
        display: block;
        margin-bottom: 8px;
        font-weight: bold;
        font-size: 20px;
        text-align: left;
    }

    input[type="text"], input[type="email"] {
        width: 98%;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 4px;
        margin-bottom: 15px;
    }

    button {
        background-color: white;
        color: black;
        border: none;
        padding: 10px 20px;
        border-radius: 4px;
        cursor: pointer;
        font-size: 16px;
    }

    button:hover {
        background-color: #0056b3;
    }
</style>