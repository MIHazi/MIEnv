// Agent test_ag1 in project MiEnv

/* Initial beliefs and rules */

/* Initial goals */

!start.

/* Plans */

+!start : true <- .print("hello world.").

+!random_turn(R) : .max(nodes, I) <- 
	turn(math.round(R * I));
	.print("turn");
	.print(R).

-!random_turn(R) : true <- 
	.print("turn failed"); 
	.print(R).

+has_action : .random(R) <- !randomTurn(R).
