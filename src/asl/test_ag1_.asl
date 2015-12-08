// Agent test_ag1 in project MiEnv

/* Initial beliefs and rules */

/* Initial goals */

!start.

/* Plans */

+!start : true <- .print("hello world.").

+!random_turn(R) : nodes(N)  & .max(N, I) <- 
	turn(math.round(R * I));
	.print("turn").

+!random_turn(_) : .random(R) <-
	turn(math.round(R*2));
	.print("turn2").

-!random_turn(_) : true <- 
	.print("turn failed").

+has_action : .random(R) <- !random_turn(R).
