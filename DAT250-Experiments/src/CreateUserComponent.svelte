<script>
    import { navigate } from 'svelte-routing';


    let username = "";
    let success = false;
    let error = "";

    async function createUser() {
        const params = new URLSearchParams({
            username: username
        });

        const url = `http://localhost:8080/v1/user/create?username=${params.toString()}`;

        const response = await fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({username}),
        });

        if (response.ok){
            success = true;
            navigate("/polls");
        }
        else{
            const data = await response.json();
            error = data.message;
            console.log(error)
        }

    }
</script>

<style>
    .user {
        background-color: lightblue;
        padding: 30px;
        border-radius: 8px;
        margin-bottom: 60%;
    }
</style>

<div class="user">
    <h2>Create User</h2>
    <input type="text" bind:value={username} placeholder="Username" required/>
    <button on:click={createUser}>Create User</button>
</div>


