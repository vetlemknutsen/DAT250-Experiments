<script>
    import { Router, Route, Link } from 'svelte-routing';
    import CreateUser from './CreateUserComponent.svelte';
    import CreatePoll from './CreatePollComponent.svelte';
    import Vote from './VoteComponent.svelte';


</script>

<Router>
        <Route path="/" component={CreateUser} exact></Route>
        <Route path="/polls" component={Vote} />
        <Route path="/create-poll" component={CreatePoll} />

</Router>